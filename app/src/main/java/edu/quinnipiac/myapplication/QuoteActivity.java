//Dominic Smorra
//SER 210
// Quote App

package edu.quinnipiac.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class QuoteActivity extends AppCompatActivity {

    /*
     *Sets the text in the quote activity
     *
     * @param saved state
     *
     * @return nothing
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);
        String quote = (String) getIntent().getExtras().get("quote");
        TextView textView = (TextView) findViewById(R.id.quote) ;
        textView.setText(quote);
//        Log.d("QUOTE2", quote);
//        Bundle bundle = new Bundle();
//        QuoteFragment obj = new QuoteFragment();
//        bundle.putString("quote", quote);
//        // set Fragmentclass Arguments
//        obj.setArguments(bundle);



    }
}
