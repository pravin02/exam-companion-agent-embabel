package org.pk.exam.companion;

import com.embabel.agent.config.annotation.LoggingThemes;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

@SpringBootApplication
public class ExamCompanionAgentEmbabelApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ExamCompanionAgentEmbabelApplication.class);
		app.setDefaultProperties(Map.of(
				"embabel.agent.logging.personality", LoggingThemes.SEVERANCE
		));
		app.run(args);
	}

}
