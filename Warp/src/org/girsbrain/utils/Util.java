/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.girsbrain.utils;

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