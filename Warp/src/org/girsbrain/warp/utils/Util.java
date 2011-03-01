package org.girsbrain.warp.utils;

/**
 *
 * @author jlogsdon
 */
public class Util {
    public static boolean isInteger(String toParse) {
        try {
            Integer.parseInt(toParse);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}