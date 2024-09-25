package com.tddinaction;

import java.util.Map;

public sealed interface Segment {
    String evaluate(Map<String, String> variables);

    record PlainText(String text) implements Segment {

        @Override
        public String evaluate(Map<String, String> variables) {
            return text;
        }
    }

    record Variable(String name) implements Segment {

        @Override
        public String evaluate(Map<String, String> variables) {
            if (!variables.containsKey(name)) {
                throw new MissingValueException("No value for ${" + name + "}");
            }
            return variables.get(name);
        }
    }
}
