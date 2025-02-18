package com.tddinaction;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPlainTextSegment {
    @Test
    public void plainTextEvaluatesAsIs() throws Exception {
        Map<String, String> variables = new HashMap<String, String>();
        String text = "abc def";
        assertEquals(text, new Segment.PlainText(text).evaluate(variables));
    }
}
