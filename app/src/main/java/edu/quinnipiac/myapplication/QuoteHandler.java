package edu.quinnipiac.myapplication;


import org.json.JSONException;
import org.json.JSONObject;


public class QuoteHandler {


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
