package net.backend.questions.softarextask.controller.mapping;

public interface QuestionURL {
    String BASE = "/users/{userId}/questions";
    String POST_CREATE = BASE;
    String GET_ALL_BY_USER = BASE;
    String GET_BY_ID = BASE + "/{questId}";
    String DELETE_BY_ID = BASE + "/{questId}";
    String PATCH_BY_ID = BASE + "/{questId}";
}
