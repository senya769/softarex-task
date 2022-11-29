package net.backend.questions.softarextask.controller.mapping;

public interface AnswerURL {
    String BASE = "/users/{userId}/answers";
    String GET_BY_ID = BASE + "/{answerId}";
    String PATCH_BY_QUESTION_ID = BASE + "/{questId}";
}
