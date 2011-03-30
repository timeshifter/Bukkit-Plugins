package org.girsbrain.utils;

/**
 * @author jlogsdon
 */
public class StringFormatter {
    static public String join(String[] parts) {
        return join(" ", parts);
    }

    static public String join(String glue, String[] parts) {
        if (parts.length == 0) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        for (String part : parts) {
            builder.append(part).append(glue);
        }
        return builder.toString().trim();
    }
}