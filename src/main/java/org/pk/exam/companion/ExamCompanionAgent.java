//package org.pk.exam.companion;
//
//
//import com.embabel.agent.api.annotation.Action;
//import com.embabel.agent.api.annotation.Agent;
//import com.embabel.agent.api.common.OperationContext;
//import com.embabel.agent.domain.io.UserInput;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.pk.exam.companion.dtos.MCQ;
//import tools.jackson.databind.ObjectMapper;
//
//import java.util.Collections;
//import java.util.List;
//
//@Agent(
//        name = "ExamCompanionAgent",
//        version = "1.0.0",
//        description =
//                """
//                        This would be like your Friend/Teacher/Collegauge who helps you to study in advanced way like will ask questions on topic and you will be answering, This agent with analyze and understand what key points missed in the answers and ask more question to cover up points.
//                        It generates 10 multiple choice questions (MCQs) each time after done with the topic.
//
//                        Features.
//                        1> Asking more questions on weak areas and analyze and correct him.
//                        2> Generates 10 MCQs after done with the topic.
//                        3> At last it generates summary about the progress made shows the score between minimum 0 and maximum 100 percentage.
//
//                        To understand topic in better way you can use various tools like web search, wikipedia search.
//                        """
//)
//public class ExamCompanionAgent {
//
//    private static final Logger LOGGER = LogManager.getLogger(ExamCompanionAgent.class);
//
//    @Action(description = "Assume user has entered this topic.")
//    public String topic() {
//        String topic = "What is cosmoses process?";
//        LOGGER.info("User selected topic is : {}", topic);
//        return topic;
//    }
//
//
//    @Action(description = """
//            Your job is to extract the subjects by understanding user input.
//            It could possible that user prompt may have multiple subjects.
//            ex. Science, Physics, Mathematics""")
//    public String[] getSubject(UserInput input, OperationContext context) {
//        LOGGER.info("User input content is : {}", input.getContent());
//        return context.ai()
//                .withAutoLlm()
//                .createObject(input.getContent(), String[].class);
//    }
//
////    @Action(description = """
////            Your job is to generate 10 Multiple Choice Questions with there respective answers for the given topic.
////            """)
////    public String generateQuestion(String subject, String topic, OperationContext context) {
////        LOGGER.info("generateQuestion: subject: {}, topic: {}", subject, topic);
////        return context.ai()
////                .withAutoLlm()
////                .createObject(("""
////                                Go through the topic user entered/select topic and prepare 10 MCQs with there answers.
////                                Subject:%s
////                                Topic:%s
////                                """)
////                                .formatted(subject, topic)
////                        , String.class);
////    }
//
//
//    @Action(description = """
//            Your job is to generate 10 Multiple Choice Questions with there respective answers for the given topic.
//            """)
//    public List<MCQ> generateMCQs(String subject, String topic, OperationContext context) {
//        return Collections.singletonList(
//                context.ai()
//                        .withAutoLlm()
//                        .createObject("""
//                                        Go through the topic user entered/select topic and prepare 10 MCQs with there answers.
//                                        Subject:%s
//                                        Topic:%s
//                                        """.formatted(subject, topic),
//                                MCQ.class)
//        );
//    }
//
//    @Action(description = """
//            Your job is to analyze topic and answered MCQs and generate score minimum 0 ad maximum 100
//            """)
//    public Float analyze(String subject, String topic, List<MCQ> mcqs, OperationContext context) {
//        return
//                context.ai()
//                        .withAutoLlm()
//                        .createObject("""
//                                        Go through the subject, topic, MCQs with answers and analyze.
//                                        Generate a score minimum 0 and maximum 100
//                                        Subject:%s
//                                        Topic:%s,
//                                        MCQs: %s
//                                        """.formatted(subject, topic, new ObjectMapper().writeValueAsString(mcqs)),
//                                Float.class);
//
//    }
//
//
//}
