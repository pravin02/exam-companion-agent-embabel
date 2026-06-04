package org.pk.exam.companion;

import com.embabel.agent.api.annotation.*;
import com.embabel.agent.api.common.OperationContext;
import com.embabel.agent.core.CoreToolGroups;
import com.embabel.agent.core.hitl.WaitFor;
import com.embabel.common.ai.model.LlmOptions;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.stream.Collectors;

record FocusAreas(List<FocusArea> focusAreas) {
}

record FocusArea(String topic) {
}

record ResearchReport(String subject, String summary) {
}

record ResearchSubject(String name) {
}

@Agent(description = "Find information from wikipedia according to the user's request.")
public class WikiAgent {

    private final int wordCount;
    private final LlmOptions llm;

    public WikiAgent(
            @Value("${wiki.wordcount:200}") int wordCount,
            @Value("${wiki.llm:gpt-4.1-nano}") String model) {
        this.wordCount = wordCount;
        this.llm = LlmOptions.withModel(model);
    }

    @Action
    FocusAreas focusAreas(ResearchSubject researchSubject, OperationContext context) {
        return context.ai()
                .withLlm(llm.withTemperature(.6))
                .withToolGroup(CoreToolGroups.WEB)
                .createObject("""
                        What are some interesting topics to explore about %s?
                        Please provide a list of focus areas, each as a separate line.
                        Go beyond the obvious: emphasize quirky and lesser-known aspects.
                        Use Wikipedia as the source of information.
                        """.formatted(researchSubject.name()), FocusAreas.class);
    }

    @Action
    FocusArea findFocusArea(ResearchSubject researchSubject, FocusAreas focusAreas) {
        return WaitFor.formSubmission(
                """
                        Great, there are lots of interesting things to explore about %s.
                        Choices:
                            %s
                        Please select one of the topics above to focus on.
                        """.formatted(researchSubject.name(),
                        focusAreas.focusAreas().stream().map(FocusArea::topic)
                                .collect(Collectors.joining("\n"))),
                FocusArea.class);
    }

    @Action
    @AchievesGoal(description = "Find information from wikipedia according to the user's request.",
            export = @Export(remote = true, name = "wikiSearch", startingInputTypes = {ResearchSubject.class})
    )
    ResearchReport performResearch(ResearchSubject researchSubject, FocusArea focusArea, OperationContext context) {
        return context.ai()
                .withLlm(llm.withTemperature(.8))
                .withToolGroup(CoreToolGroups.WEB)
                .createObject("""
                                Research the following subject on Wikipedia, with the given focus area, and summarize it
                                in %d words:
                                Subject: %s
                                Focus Area: %s
                                """.formatted(wordCount, researchSubject.name(), focusArea.topic()),
                        ResearchReport.class);
    }
}