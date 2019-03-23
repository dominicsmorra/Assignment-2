package edu.quinnipiac.myapplication;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;


public class QuoteHandler {

    private static final int START_YEAR = 1950;
    private static final int END_YEAR= 2010;
    public static final String YEAR_FACT = "YEAR_FACT";
    final public static String [] years = new String[END_YEAR - START_YEAR +1];;

    public QuoteHandler(){

    }

    public String getQuote(String quoteJSON) throws JSONException {
        String[] quoteInfo = {"" , ""};
        JSONObject quoteJSONObj = new JSONObject(quoteJSON);
        quoteInfo[0] = quoteJSONObj.getString("author");
        quoteInfo[1] = quoteJSONObj.getString("message");

        return "'" + quoteInfo[1] + "'\n- " + quoteInfo[0];
    }

}
