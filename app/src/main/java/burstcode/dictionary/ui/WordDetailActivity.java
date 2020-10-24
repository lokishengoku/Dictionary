package burstcode.dictionary.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import burstcode.dictionary.R;

public class WordDetailActivity extends AppCompatActivity {

    private TextView txtDetailWord;
    private TextView txtDetailContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Word Detail");

        txtDetailWord = findViewById(R.id.txtDetailWord);
        txtDetailContent = findViewById(R.id.txtDetailContent);

        Intent intent = getIntent();
        txtDetailWord.setText(intent.getStringExtra("word"));
        txtDetailContent.setText(intent.getStringExtra("content"));

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}