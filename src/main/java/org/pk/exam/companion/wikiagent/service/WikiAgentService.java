package org.pk.exam.companion.wikiagent.service;


import com.embabel.agent.api.invocation.AgentInvocation;
import com.embabel.agent.core.*;
import org.pk.exam.companion.wikiagent.records.ResearchReport;
import org.pk.exam.companion.wikiagent.records.ResearchSubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class WikiAgentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WikiAgentService.class);

    private final AgentPlatform agentPlatform;

    public WikiAgentService(AgentPlatform agentPlatform) {
        this.agentPlatform = agentPlatform;
        LOGGER.info("WikiAgentService initialized");
    }

    public ResearchReport search2(String topic) {
        LOGGER.info("Listing agents start");
        agentPlatform.agents().stream().forEach(System.out::println);
        LOGGER.info("Listing agents end");
        var invocation = AgentInvocation
                .builder(agentPlatform)
                .build(ResearchReport.class);
        return invocation.invoke(new ResearchSubject(topic));
    }

    public ResearchReport search(String topic) throws Exception{
        LOGGER.info("Listing agents start");
        agentPlatform.agents().stream().forEach(System.out::println);
        LOGGER.info("Listing agents end");

        Agent agent = agentPlatform.agents().stream()
                .filter(a -> a.getName().equalsIgnoreCase("WikiAgent"))
                .findFirst().orElseThrow();

        AgentProcess agentProcess = agentPlatform.createAgentProcessFrom(agent, ProcessOptions.DEFAULT, new ResearchSubject(topic));

        AgentProcess result = agentPlatform.start(agentProcess).get();
        AgentProcessStatusCode status = result.getStatus();
        if(status == AgentProcessStatusCode.COMPLETED) {
            return (ResearchReport) result.lastResult();
        }
        return null;
    }

}
