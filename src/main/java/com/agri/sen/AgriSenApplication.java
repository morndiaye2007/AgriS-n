package com.agri.sen;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.agri.sen"})
@EnableScheduling
public class AgriSenApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgriSenApplication.class, args);
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

					.build());

	};*/


}
