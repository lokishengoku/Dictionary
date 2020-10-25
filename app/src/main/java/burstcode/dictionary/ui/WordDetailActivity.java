package burstcode.dictionary.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;
import java.util.Objects;

import burstcode.dictionary.MainActivity;
import burstcode.dictionary.R;
import burstcode.dictionary.model.Word;
import burstcode.dictionary.ui.favorite.FavoriteFragment;

public class WordDetailActivity extends AppCompatActivity {

    private FloatingActionButton btnAddToFavorite;
    private boolean isFavorite = false;

    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_detail);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Word Detail");

        TextView txtDetailWord = findViewById(R.id.txtDetailWord);
        TextView txtDetailContent = findViewById(R.id.txtDetailContent);
        final Intent intent = getIntent();
        Word word = (Word) intent.getSerializableExtra("word");
        assert word != null;
        txtDetailWord.setText(word.getWord());
        txtDetailContent.setText(word.getContent());

        ImageView btnSpeaker = findViewById(R.id.btnSpeaker);
        textToSpeech = new TextToSpeech(getApplicationContext(),
                i -> {
                    if (i == TextToSpeech.SUCCESS) {
                        if (word.isEng()) {
                            textToSpeech.setLanguage(Locale.ENGLISH);
                        } else {
                            textToSpeech.setLanguage(new Locale("vi", "VN"));
                        }

                    }
                });
        btnSpeaker.setOnClickListener(view -> textToSpeech.speak(word.getWord(),
                TextToSpeech.QUEUE_FLUSH,
                null));

        btnAddToFavorite = findViewById(R.id.btnAddToFavorite);
        for (Word w :
                MainActivity.favoriteWords) {
            if (w.getWord().equals(word.getWord())) {
                isFavorite = true;
                break;
            }
        }
        if (isFavorite) {
            btnAddToFavorite.setImageResource(R.drawable.ic_baseline_favorite_24);
        } else {
            btnAddToFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24);
        }
        btnAddToFavorite.setOnClickListener(view -> {
            if (isFavorite) {
                btnAddToFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                MainActivity.removeFavorite(word);
                FavoriteFragment.updateUI();
                finish();

            } else {
                btnAddToFavorite.setImageResource(R.drawable.ic_baseline_favorite_24);
                MainActivity.favoriteWords.add(word);
                MainActivity.insertFavorite(word);
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        setResult(Activity.RESULT_CANCELED);
        finish();
        return super.onSupportNavigateUp();
    }
}