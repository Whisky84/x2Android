/**
 * 
 */

package it.wm.perdue;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Gabriele "Whisky" Visconti
 */
public final class Utils {
    
    public static String stripEsercente(CharSequence c) {
        StringBuilder builder = new StringBuilder(c);
        
        if (builder.length() >= 13 && builder.substring(0, 13).equals("{\"Esercente\":")) {
            builder.delete(0, 13);
            builder.deleteCharAt(builder.length() - 1);
        }
        
        if (builder.length() >= 23 && builder.substring(0, 23).equals("{\"Esercente:FirstRows\":")) {
            Log.d("UTILS", "SUBSTRING firstRows = " + builder.substring(0, 23));
            builder.delete(0, 23);
            builder.deleteCharAt(builder.length() - 1);
        }
        if (builder.length() >= 22 && builder.substring(0, 22).equals("{\"Esercente:MoreRows\":")) {
            Log.d("UTILS", "SUBSTRING moreRows = " + builder.substring(0, 22));
            builder.delete(0, 22);
            builder.deleteCharAt(builder.length() - 1);
        }
        if (builder.length() >= 20 && builder.substring(0, 20).equals("{\"Esercente:Search\":")) {
            Log.d("UTILS", "SUBSTRING search = " + builder.substring(0, 20));
            builder.delete(0, 20);
            builder.deleteCharAt(builder.length() - 1);
        }
        
        return builder.toString();
    }
    
    public static String stripFinalFalse(CharSequence c) {
        StringBuilder builder = new StringBuilder(c);
        int start = builder.length() - ",false]".length();
        int end = builder.length();
        if (builder.substring(start, end).equals(",false]")) {
            builder.replace(start, end, "]");
        }
        return builder.toString();
    }
    
    public static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd kk:mm:ss");
        return gsonBuilder.create();
    }
}
