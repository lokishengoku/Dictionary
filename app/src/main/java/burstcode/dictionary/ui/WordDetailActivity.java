package burstcode.dictionary.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import burstcode.dictionary.R;

public class WordDetailActivity extends AppCompatActivity {

    private TextView txtDetailWord;
    private TextView txtDetailContent;
    private ImageView btnSpeaker;
    private boolean isEngVie;

    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Word Detail");

        txtDetailWord = findViewById(R.id.txtDetailWord);
        txtDetailContent = findViewById(R.id.txtDetailContent);
        final Intent intent = getIntent();
        txtDetailWord.setText(intent.getStringExtra("word"));
        txtDetailContent.setText(intent.getStringExtra("content"));
        isEngVie = intent.getBooleanExtra("isEngVie", true);

        btnSpeaker = findViewById(R.id.btnSpeaker);
        textToSpeech = new TextToSpeech(getApplicationContext(),
                new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int i) {
                        if (i == TextToSpeech.SUCCESS) {
                            if (isEngVie) {
                                int lang = textToSpeech.setLanguage(Locale.ENGLISH);
                            } else {
                                int lang = textToSpeech.setLanguage(new Locale("vi", "VN"));
                            }

                        }
                    }
                });
        btnSpeaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int speech = textToSpeech.speak(intent.getStringExtra("word"),
                        TextToSpeech.QUEUE_FLUSH,
                        null);
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}