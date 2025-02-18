package com.tddinaction;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateParse {
    private static final Pattern pattern = Pattern.compile("\\$\\{[^}]*}");

    public List<String> parse(String template) {
        if (template.isEmpty()) {
            return List.of("");
        }
        List<String> segments = new ArrayList<String>();
        int index = collectSegments(segments, template);
        addTail(segments, template, index);
        return segments;
    }

    private int collectSegments(List<String> segs, String src) {
        Matcher matcher = pattern.matcher(src);
        int index = 0;
        while (matcher.find()) {
            addPrecedingPlainText(segs, src, matcher, index);
            addVariable(segs, src, matcher);
            index = matcher.end();
        }
        return index;
    }

    private void addTail(List<String> segs, String template, int index) {
        if (index < template.length()) {
            segs.add(template.substring(index));
        }
    }

    private void addVariable(List<String> segs, String src, Matcher m) {
        segs.add(src.substring(m.start(), m.end()));
    }

    private void addPrecedingPlainText(List<String> segs, String src,
                                       Matcher m, int index) {
        if (index != m.start()) {
            segs.add(src.substring(index, m.start()));
        }
    }

    public List<Segment> parseSegments(String template) {
        List<String> strings = parse(template);
        return strings.stream()
                 .<Segment>mapMulti((s, consumer) -> {
                     if (Template.isVariable(s)) {
                         String name = s.substring(2, s.length() - 1);
                         consumer.accept(new Segment.Variable(name));
                     } else {
                         consumer.accept(new Segment.PlainText(s));
                     }
                 }).toList();
    }
}


