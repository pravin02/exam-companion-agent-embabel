package org.pk.exam.companion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAgentMcpServer
public class ExamCompanionAgentEmbabelApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ExamCompanionAgentEmbabelApplication.class);
		app.setAdditionalProfiles(McpServers.DOCKER_DESKTOP);
		app.run(args);
	}

}
