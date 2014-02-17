package de.fruchuxs.wavefrontobjloader;

import java.util.Arrays;
import java.util.List;

/**
 * Einige Methoden, die beim parsen einer *.obj oder *.mtl Datei häufig 
 * gebraucht werden
 */
public class ParseUtils {
    /**
     * Zerteilt einen String bei den Leerzeichen
     * 
     * @param pLine String der zerteilt werden soll
     * @return Liste mit den Substrings
     */
    public static List<String> seperateValuesAtWhiteSpaces(final String pLine) {
        return seperateValues(pLine, "\\s+");
    }

    /**
     * Zerteilt einen String durch regEx-Angabe
     * 
     * @param pValue String der zerteilt werden soll
     * @param regEx RegEx fuer die Zerteil Vorschrift
     * @return Liste mit den Substrings
     */
    public static List<String> seperateValues(final String pValue, final String regEx) {
        return Arrays.asList(pValue.split(regEx));
    }

    /**
     * Parst einen String als Float
     * 
     * @param pToParse String der in Float umgewandelt werden soll
     * @return Der umgewandelte Float Wert
     */
    public static Float parseAsFloat(final String pToParse) {
        return Float.parseFloat(pToParse.trim());
    }
    
    /**
     * Parst einen String als Integer
     * 
     * @param pToParse
     * @return Den umgewandelten Integer Wert
     */
    public static Integer parseAsInteger(final String pToParse) {
        return Integer.parseInt(pToParse.trim());
    }

    /**
     * Parst eine Liste von Strings als Floats und gibt das resultierende Float Array zurueck
     * 
     * @param pToParse Liste der Strings die in Float konvertiert werden sollen
     * @return Float Array
     */
    public static Float[] parseAsFloat(final List<String> pToParse) {
        Float[] result = new Float[pToParse.size()];

        for (int i = 0; i < pToParse.size(); i++) {
            result[i] = parseAsFloat(pToParse.get(i));
        }

        return result;
    }
    
    /**
     * Baut aus einer Liste von Substrings einen String zusammen
     * 
     * @param pToConcat Liste der Teile die zusammengeführt werden sollen
     * @return Den zusammengesetzten String
     */
    public static String concatStringByList(List<String> pToConcat) {
        String result = "";
        
        for(String i : pToConcat) {
            result = result + i;
        }
        
        return result;
    }
}
