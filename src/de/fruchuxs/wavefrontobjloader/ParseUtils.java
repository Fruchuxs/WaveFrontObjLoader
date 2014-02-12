/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.fruchuxs.wavefrontobjloader;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author FloH
 */
public class ParseUtils {
    public static List<String> seperateValuesAtWhiteSpaces(final String pLine) {
        return seperateValues(pLine, "\\s+");
    }

    public static List<String> seperateValues(final String pValue, final String regEx) {
        return Arrays.asList(pValue.split(regEx));
    }

    public static Float parseAsFloat(final String pToParse) {
        return Float.parseFloat(pToParse.trim());
    }

    public static Integer parseAsInteger(final String pToParse) {
        return Integer.parseInt(pToParse.trim());
    }

    public static Float[] parseAsFloat(final List<String> pToParse) {
        Float[] result = new Float[pToParse.size()];

        for (int i = 0; i < pToParse.size(); i++) {
            result[i] = parseAsFloat(pToParse.get(i));
        }

        return result;
    }
    
    public static String concatStringByList(List<String> pToConcat) {
        String result = "";
        
        for(String i : pToConcat) {
            result = result + i;
        }
        
        return result;
    }
}
