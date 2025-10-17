package com.webgram.dgpsn;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.webgram.dgpsn"})
@EnableScheduling
public class WebgramDgpsnApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebgramDgpsnApplication.class, args);
    }

	/*@Bean
	CommandLineRunner start(AgentContratRepository , AgentRepository agentRepository) {
		return args -> {
			AgentContratEntity c1 = agentContratRepository.save(AgentContratEntity.builder()
					.contraType(ContraType.CDD)
					.dateDebutContrat(new Date(2025 - 1900, 0, 1))
					.dateFinContrat(new Date(2026 - 1900, 0, 1))
					.salaire(350000.0)
					.poste("Développeur Java")
					.departement("Informatique")
					.contratstatut(ContratStatut.ACTIF)
<<<<<<< HEAD
					.build());

	};*/


}
