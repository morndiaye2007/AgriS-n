package com.agri.sen.services.Impl;


import com.agri.sen.services.MeteoService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.BooleanBuilder;
import com.agri.sen.entity.Meteo;
import com.agri.sen.entity.QMeteo;
import com.agri.sen.mapper.MeteoMapper;
import com.agri.sen.model.MeteoDTO;
import com.agri.sen.repository.MeteoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MeteoServiceImpl implements MeteoService {

    private final MeteoRepository meteoRepository;
    private final MeteoMapper meteoMapper;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${openweather.api.key}")
    private String apiKey;

    @Value("${openweather.api.url}")
    private String apiUrl;

    @Override
    public MeteoDTO getCurrentMeteo(Double latitude, Double longitude) {
        String url = String.format("%s/weather?lat=%f&lon=%f&appid=%s&units=metric&lang=fr",
                apiUrl, latitude, longitude, apiKey);

        try {
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);

            Meteo meteo = Meteo.builder()
                    .latitude(latitude)
                    .longitude(longitude)
                    .ville(root.path("name").asText())
                    .temperature(root.path("main").path("temp").asDouble())
                    .temperatureMin(root.path("main").path("temp_min").asDouble())
                    .temperatureMax(root.path("main").path("temp_max").asDouble())
                    .humidite(root.path("main").path("humidity").asInt())
                    .vitesseVent(root.path("wind").path("speed").asDouble())
                    .description(root.path("weather").get(0).path("description").asText())
                    .icone(root.path("weather").get(0).path("icon").asText())
                    .datePrevisionnelle(LocalDateTime.now())
                    .build();

            var savedMeteo = meteoRepository.save(meteo);
            return meteoMapper.asDto(savedMeteo);

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des données météo: " + e.getMessage());
        }
    }

    @Override
    public List<MeteoDTO> getMeteoForecast(Double latitude, Double longitude, Integer days) {
        String url = String.format("%s/forecast?lat=%f&lon=%f&appid=%s&units=metric&lang=fr&cnt=%d",
                apiUrl, latitude, longitude, apiKey, days * 8);

        try {
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);
            JsonNode list = root.path("list");

            List<MeteoDTO> forecasts = new ArrayList<>();

            for (JsonNode item : list) {
                Meteo meteo = Meteo.builder()
                        .latitude(latitude)
                        .longitude(longitude)
                        .ville(root.path("city").path("name").asText())
                        .temperature(item.path("main").path("temp").asDouble())
                        .temperatureMin(item.path("main").path("temp_min").asDouble())
                        .temperatureMax(item.path("main").path("temp_max").asDouble())
                        .humidite(item.path("main").path("humidity").asInt())
                        .vitesseVent(item.path("wind").path("speed").asDouble())
                        .description(item.path("weather").get(0).path("description").asText())
                        .icone(item.path("weather").get(0).path("icon").asText())
                        .probabilitePluie(item.path("pop").asDouble() * 100)
                        .datePrevisionnelle(LocalDateTime.ofInstant(
                                Instant.ofEpochSecond(item.path("dt").asLong()),
                                ZoneId.systemDefault()))
                        .build();

                var savedMeteo = meteoRepository.save(meteo);
                forecasts.add(meteoMapper.asDto(savedMeteo));
            }

            return forecasts;

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des prévisions: " + e.getMessage());
        }
    }

    @Override
    public MeteoDTO getMeteo(Long id) {
        var entity = meteoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Données météo non trouvées"));
        return meteoMapper.asDto(entity);
    }

    @Override
    public List<MeteoDTO> getMeteoByVille(String ville) {
        return meteoRepository.findByVille(ville).stream()
                .map(meteoMapper::asDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<MeteoDTO> getAllMeteo(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return meteoRepository.findAll(booleanBuilder, pageable)
                .map(meteoMapper::asDto);
    }

    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
        if (Objects.nonNull(searchParams)) {
            var qEntity = QMeteo.meteo;

            if (searchParams.containsKey("ville"))
                booleanBuilder.and(qEntity.ville.containsIgnoreCase(searchParams.get("ville")));

            if (searchParams.containsKey("temperatureMin")) {
                Double tempMin = Double.parseDouble(searchParams.get("temperatureMin"));
                booleanBuilder.and(qEntity.temperature.goe(tempMin));
            }

            if (searchParams.containsKey("temperatureMax")) {
                Double tempMax = Double.parseDouble(searchParams.get("temperatureMax"));
                booleanBuilder.and(qEntity.temperature.loe(tempMax));
            }
        }
    }
}