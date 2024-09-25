package com.tddinaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Template {
    private Map<String, String> variables;

    private String templateText;

    public Template(String s) {
        templateText = s;
        variables = new HashMap<>();
    }

    public static boolean isVariable(String segment) {
        return segment.startsWith("${") && segment.endsWith("}");
    }

    private static void checkMissingValues(String result) {
        Matcher m = Pattern.compile("\\$\\{.+}").matcher(result);
        if (m.find()) {
            throw new MissingValueException("No value for " + m.group());
        }
    }

    public void set(String name, String value) {
        variables.put(name, value);
    }

    public String evaluate() {

        TemplateParse parser = new TemplateParse();
        List<Segment> segments = parser.parseSegments(templateText);
        return concatenate(segments);

    }

    private String concatenate(List<Segment> segments) {
        StringBuilder result = new StringBuilder();
        for (var segment : segments) {
            result.append(segment.evaluate(variables));
//            append(segment, result);
        }
        return result.toString();
    }

    private void append(String segment, StringBuilder result) {
        if (isVariable(segment)) {
            evaluateVariable(segment, result);
        } else {
            result.append(segment);
        }
    }

    private void evaluateVariable(String segment, StringBuilder result) {
        String var = segment.substring(2, segment.length() - 1);
        if (!variables.containsKey(var)) {
            throw new MissingValueException("No value for " + segment);
        }
        result.append(variables.get(var));
    }

    private String replaceValriable() {
        String result = templateText;
        for (var entry : variables.entrySet()) {
            String regex = "\\$\\{" + entry.getKey() + "\\}";
            result = result.replaceAll(regex, entry.getValue());
        }
        return result;
    }
}
