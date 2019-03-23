//Dominic Smorra
//SER 210
// Quote App

package edu.quinnipiac.myapplication;



import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = MainActivity.class.getSimpleName();
    // Will contain the raw JSON response as a string.
    String yearFactJsonStr = null;

    QuoteHandler quoteHandler = new QuoteHandler();

    boolean userSelect = false;
    private String full = "https://qvoca-bestquotes-v1.p.rapidapi.com/quote";
    private ShareActionProvider provider;

    /*
     *Creates the options menu
     *
     * @param the menu
     *
     * @return the call on the super method
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    /*
     *Does actions based on which option selected
     *
     * @param the menu item selected
     *
     * @return a boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_history:
                Toast.makeText(this,"Here is the history of quotes",Toast.LENGTH_SHORT).show();
                return  true;
            case R.id.action_like:
                Toast.makeText(this,"I stand upon my desk to remind myself that we must constantly look at things in a different way by Robin Williams is my favorite quote",Toast.LENGTH_LONG).show();
                return  true;
            case R.id.action_trending:
                Toast.makeText(this,"What does not kill you makes you stronger will always be a hot quote",Toast.LENGTH_LONG).show();
                return  true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button playButton = (Button) findViewById(R.id.buttonGo);

        String [] array = {"famous", "movies"};

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,array);

        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    }

    /*
     *Handles the click on the go button
     *
     * @param the view
     *
     * @return nothing
     */
    public void goHandler(View view){
        new FetchYearFact().execute();
    }



    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        userSelect = true;

    }

    private class FetchYearFact extends AsyncTask<String,Void,String>{

        /*
         *Gets the information from the Quote APi
         *
         * @param String params
         *
         * @return the quote
         */
        @Override
        protected String doInBackground(String... params) {

            HttpURLConnection urlConnection =null;
            BufferedReader reader =null;
            String quote = null;

            try {
                URL url = new URL(full);


                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");

                urlConnection.setRequestProperty("X-RapidAPI-Key","dcd371f145msh7eb1a846f31e891p135dc8jsnc6346dab1071");

                urlConnection.connect();

                InputStream in = urlConnection.getInputStream();
                if (in == null) {
                    return null;
                }
                reader  = new BufferedReader(new InputStreamReader(in));
                // call getBufferString to get the string from the buffer.


                String quoteJSON = getBufferStringFromBuffer(reader).toString();


                // call a method to parse the json data and return a string.
                quote=  quoteHandler.getQuote(quoteJSON);
                Intent intent = new Intent(MainActivity.this,QuoteActivity.class);
                intent.putExtra("quote",quote);
                startActivity(intent);


            }catch(Exception e){
                Log.e(LOG_TAG,"Error" + e.getMessage());
                return null;
            }finally{
                if (urlConnection != null){
                    urlConnection.disconnect();
                }
                if (reader != null){
                    try{
                        reader.close();
                    }catch (IOException e){
                        Log.e(LOG_TAG,"Error" + e.getMessage());
                        return null;
                    }
                }
            }

            return quote;
        }


        /*
         *Gets the buffer string from the buffer
         *
         * @param the BufferedReader
         *
         * @return the StringBuffer
         */
        private StringBuffer getBufferStringFromBuffer(BufferedReader br) throws Exception{
            Log.d("hello", "hello");

            StringBuffer buffer = new StringBuffer();

            String line;
            while((line = br.readLine()) != null){
                buffer.append(line + '\n');
            }

            if (buffer.length() == 0)
                return null;

            return buffer;
        }
    }


}
