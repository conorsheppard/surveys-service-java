package com.conorsheppard.surveys.service;

import com.conorsheppard.surveys.models.Choice;
import com.conorsheppard.surveys.models.Question;
import com.conorsheppard.surveys.models.Response;
import com.conorsheppard.surveys.models.Survey;
import com.conorsheppard.surveys.repository.ResponseRepo;
import com.conorsheppard.surveys.repository.SurveyRepo;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SurveyService {
    private final SurveyRepo surveyRepo;
    private final ResponseRepo responseRepo;

    public SurveyService(SurveyRepo surveyRepo, ResponseRepo responseRepo) {
        this.surveyRepo = surveyRepo;
        this.responseRepo = responseRepo;
    }

    /**
     * Returns a mapping between Respondent ID (Integer) and the number of questions answered by the given respondent (Long).
     * Groups by respondent and counts occurrences.
     * Time complexity: O(n).
     * Space complexity: O(k), where k is the distinct number of respondents, at most n.
     */
    public Map<Integer, Long> getRespondentAnswerCounts() {
        return responseRepo.getResponses().stream()
                .collect(Collectors.groupingBy(
                        Response::respondent,
                        Collectors.counting()
                ));
    }

    /**
     * Returns a mapping between Respondent ID (Integer) and the amount earned (in pence) for questions answered.
     * Time complexity: O(q + n), where q is the number of questions (when building the payout map) and n is the number of responses.
     * Space complexity: O(q + k), where we're storing an entry for each question q, and k is the number of unique respondents.
     *
     * @param surveyId ID of the survey the respondent has completed (or in the process of).
     */
    public Map<Integer, Long> getRespondentAmountsEarned(int surveyId) {
        var questionToPayoutMap = surveyRepo.getSurveyById(surveyId)
                .questions()
                .stream()
                .collect(Collectors.toMap(Question::id, Question::payout));

        return responseRepo.getResponses()
                .stream()
                .filter(response -> questionToPayoutMap.containsKey(response.question())) // filter out questions from other surveys
                .collect(Collectors.groupingBy(
                        Response::respondent,
                        Collectors.summingLong(r -> questionToPayoutMap.get(r.question()))));
    }

    /**
     * Returns true if all the choice indexes are valid for a respondents answers
     *
     * @param responses input list of responses for a given respondent
     * @param survey    input survey the respondent has answered
     */
    public boolean allChoicesAreValid(List<Response> responses, Survey survey) {
        // construct a questions-to-num-options map
        var questionToChoiceCountMap = survey.questions()
                .stream()
                .collect(
                        Collectors.toMap(Question::id, q -> (long) q.options().size())
                );

        return responses.stream()
                .allMatch(response ->
                        response.choice() > 0 && response.choice() < questionToChoiceCountMap.get(response.question()));
    }

    /**
     * Returns a response based on the input parameters (i.e. the respondent and their answer)
     *
     * @param respondent the ID of the respondent answering the question
     * @param question   the ID of the question being answered
     * @param choice     the index of the choice made on the question
     */
    public Response makeChoice(int respondent, int question, int choice) {
        return new Response();
    }

    public static class SurveyCycleDetector {

        /**
         * Return true if the Survey contains a cycle, false otherwise.
         *
         * @param survey Survey input for which to assess if it's questions contain a cycle
         */
        public boolean hasCycle(Survey survey) {
            Map<Integer, List<Integer>> questionRouteGraph = buildGraph(survey.questions());

            Set<Integer> visited = new HashSet<>();
            Set<Integer> stack = new HashSet<>(); // recursion stack

            for (Integer question : questionRouteGraph.keySet()) {
                if (dfs(question, questionRouteGraph, visited, stack)) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Returns a map of questions mapped to their routes
         */
        private Map<Integer, List<Integer>> buildGraph(List<Question> questions) {
            var g = questions.stream().collect(Collectors.toMap(
                    Question::id,
                    q -> q.options().stream()
                            .filter(choice -> choice.route() != -1)
                            .mapToInt(Choice::route)
                            .boxed()
                            .toList()
            ));

            return g;
        }

        private boolean dfs(Integer currentQuestion, Map<Integer, List<Integer>> graph,
                            Set<Integer> visited, Set<Integer> stack) {

            if (stack.contains(currentQuestion)) return true; // cycle detected
            if (visited.contains(currentQuestion)) return false; // avoid duplicate work

            visited.add(currentQuestion);
            stack.add(currentQuestion);

            for (Integer neighbor : graph.getOrDefault(currentQuestion, List.of())) {
                if (dfs(neighbor, graph, visited, stack)) return true;
            }

            stack.remove(currentQuestion);
            return false;
        }
    }
}
