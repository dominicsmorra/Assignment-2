package edu.quinnipiac.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class QuoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);
        String quote = (String) getIntent().getExtras().get("quote");
        TextView textView = (TextView) findViewById(R.id.quote);
        textView.setText(quote);

    }
}
