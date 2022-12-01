package net.backend.questions.softarextask.model;

import net.backend.questions.softarextask.exception.TypeAnswerException;

public enum TypeAnswer {
    SINGLE_LINE("SINGLE_LINE"),
    MULTILINE("MULTILINE"),
    RADIO_BUTTON("RADIO_BUTTON"),
    CHECKBOX("CHECKBOX"),
    COMBOBOX("COMBOBOX"),
    DATE("DATE");
    private final String value;

    TypeAnswer(String value) {
        this.value = value;
    }

    public static TypeAnswer fromString(String value) {
        if (value != null) {
            for (TypeAnswer pt : TypeAnswer.values()) {
                if (value.equalsIgnoreCase(pt.value)) {
                    return pt;
                }
            }
        }
        throw new TypeAnswerException("Such value is not exist in the TypeAnswer enum", value);
    }
}

