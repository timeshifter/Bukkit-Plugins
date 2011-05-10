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
        for (int i = 0; i < parts.length; i++) {
            builder.append(parts[i].toString());
            if (i != parts.length - 1) {
                builder.append(glue);
            }
        }
        return builder.toString();
    }
}