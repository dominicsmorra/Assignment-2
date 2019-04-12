//Dominic Smorra
//SER 210
// Quote App

package edu.quinnipiac.myapplication;



import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements StartFragment.ToNextActivity{

    private final String LOG_TAG = MainActivity.class.getSimpleName();

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

        // Get the ActionProvider for later usage

        // Get the ActionProvider for later usage
        MenuItem shareItem =  menu.findItem(R.id.action_share);
        provider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

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
            case R.id.action_like:
                Toast.makeText(this,"I stand upon my desk to remind myself that we must constantly look at things in a different way by Robin Williams is my favorite quote",Toast.LENGTH_LONG).show();
                return  true;
            case R.id.action_trending:
                Toast.makeText(this,"What does not kill you makes you stronger will always be a hot quote",Toast.LENGTH_LONG).show();
                return  true;
            case R.id.action_help:
                Toast.makeText(this,"This app uses Bestquotes API to get a random quote from their database and display it on screen",Toast.LENGTH_LONG).show();
                return  true;
            case R.id.action_blue:
                ConstraintLayout background = (ConstraintLayout) findViewById(R.id.main);
                background.setBackgroundColor(Color.BLUE);
                Toast.makeText(this,"Background color changed to blue",Toast.LENGTH_LONG).show();
                return  true;
            case R.id.action_share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "message");
                provider.setShareIntent(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

                String quoteJSON = getBufferStringFromBuffer(reader).toString();
                quote=  quoteHandler.getQuote(quoteJSON);
                Log.d("QUOTE", quote);
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
