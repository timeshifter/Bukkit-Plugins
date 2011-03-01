package org.girsbrain.groupmessage.utils;

import java.util.ArrayList;

/**
 * @author jlogsdon
 */
public class StringFormatter {
    public static String join(String glue, ArrayList<String> parts) {
        return join(glue, parts.toArray(new String[]{}));
    }

    public static String join(String glue, String[] parts) {
        return join(glue, parts, 0);
    }

    public static String join(String glue, String[] parts, int start) {
        StringBuilder builder = new StringBuilder();

        for (; start < parts.length; start++) {
            builder.append(parts[start]);
            builder.append(glue);
        }
        if (builder.length() > 1) {
            builder.deleteCharAt(builder.length() - 1);
        }

        return builder.toString();
    }
}
