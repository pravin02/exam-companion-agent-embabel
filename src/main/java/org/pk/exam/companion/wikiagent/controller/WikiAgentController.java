package org.pk.exam.companion.wikiagent.controller;

import org.pk.exam.companion.wikiagent.records.ResearchReport;
import org.pk.exam.companion.wikiagent.service.WikiAgentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agent/wiki/search")
public class WikiAgentController {

    private final WikiAgentService wikiAgentService;

    public WikiAgentController(WikiAgentService service) {
        this.wikiAgentService = service;
    }

    @GetMapping
    public ResearchReport search(@RequestParam("topic") String topic) throws Exception {
        return wikiAgentService.search(topic);
    }
}
