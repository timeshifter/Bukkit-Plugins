package org.girsbrain.utils;

/**
 * @author jlogsdon
 */
public class StringFormatter {
    static public String join(Object[] parts) {
        return join(" ", parts);
    }

    static public String join(String glue, Object[] parts) {
        if (parts.length == 0) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        for (Object part : parts) {
            builder.append(part.toString()).append(glue);
        }
        return builder.toString().trim();
    }
}