package com.tddinaction;

import com.tddinaction.Segment.PlainText;
import com.tddinaction.Segment.Variable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTemplateParse {
    TemplateParse parse;

    List<?> segments;

    @BeforeEach
    public void setUp() {
        parse = new TemplateParse();
        segments = null;
    }

    private void assertSegments(Object... expected) {
        assertEquals(expected.length, segments.size(), "Number of segments doesn't match.");
        assertEquals(Arrays.asList(expected), segments);
    }

    @Test
    public void templateWithOnlyPlainText() throws Exception {
        segments = parse.parse("plain text only");
        assertSegments("plain text only");
    }

    @Test
    public void emptyTemplateRendersAsEmptyString() throws Exception {
        segments = parse.parse("");
        assertSegments("");
    }

    @Test
    public void parsingMultipleVariables() throws Exception {
        segments = parse.parse("${a}:${b}:${c}");
        assertSegments("${a}", ":", "${b}", ":", "${c}");
    }

    @Test
    public void parsingTemplateIntoSegmentObjects() throws Exception {
        TemplateParse p = new TemplateParse();
        segments = p.parseSegments("a ${b} c ${d}");
        assertSegments(
          new PlainText("a "), new Variable("b"),
          new PlainText(" c "), new Variable("d")
        );
    }
}
