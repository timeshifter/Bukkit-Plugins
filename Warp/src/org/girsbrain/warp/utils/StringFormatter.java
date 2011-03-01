package org.girsbrain.warp.utils;

import java.util.ArrayList;

/**
 * @author jlogsdon
 */
public class StringFormatter {
    public static String join(String glue, ArrayList<String> parts) {
        return join(glue, parts.toArray(new String[]{}));
    }

    public static String join(String glue, String[] parts) {
        StringBuilder builder = new StringBuilder();

        for (String part : parts) {
            builder.append(part);
            builder.append(", ");
        }
        if (builder.length() > 1) {
            builder.deleteCharAt(builder.length() - 1);
        }

        return builder.toString();
    }
}
