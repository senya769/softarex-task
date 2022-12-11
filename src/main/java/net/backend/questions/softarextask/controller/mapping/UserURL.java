package net.backend.questions.softarextask.controller.mapping;

public interface UserURL {
    String BASE = "/users";
    String GET_ALL = BASE;
    String GET_BY_ID = BASE + "/{userId}";
    String PATCH_BY_ID = BASE + "/{userId}";
    String DELETE_BY_ID = BASE + "/{userId}";
    String POST_CHECK_PASSWORD = BASE + "/check-password";
    String POST_RESET_PASSWORD = BASE + "/reset-password";
}
