package burstcode.dictionary.ui.your_word;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import burstcode.dictionary.MainActivity;
import burstcode.dictionary.R;
import burstcode.dictionary.adapter.WordsAdapter;
import burstcode.dictionary.gesture.OnSwipeTouchListener;
import burstcode.dictionary.model.Word;

import static android.app.Activity.RESULT_OK;


public class YourWordFragment extends Fragment {
    private static final int RECOGNIZER_RESULT = 1;
    private ConstraintLayout ywContainer;
    private TextView txtCurrentWord, txtPoint;
    private ImageView btnMicro2;
    private RecyclerView recyclerView;
    private Switch swEngVie;
    private Word word;
    private WordsAdapter adapter;
    private static List<Word> words = new ArrayList<>();
    private static int point = 0;

    public YourWordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_your_word, container, false);
    }

    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ywContainer = view.findViewById(R.id.ywContainer);
        txtCurrentWord = view.findViewById(R.id.txtCurrentWord);
        txtPoint = view.findViewById(R.id.txtPoint);
        btnMicro2 = view.findViewById(R.id.btnMicro2);
        recyclerView = view.findViewById(R.id.recViewYourWords);
        swEngVie = view.findViewById(R.id.swEngVie);

        adapter = new WordsAdapter(words, getContext(), MainActivity.YOUR_WORDS);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setNestedScrollingEnabled(false);

        txtPoint.setText(point + " Points");
        word = getRandomWord();
        txtCurrentWord.setText(word.getWord());

        ywContainer.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            public void onSwipeRight() {
                word = getRandomWord();
                txtCurrentWord.setText(word.getWord());
            }

            public void onSwipeLeft() {
                word = getRandomWord();
                txtCurrentWord.setText(word.getWord());
            }
        });

        btnMicro2.setOnClickListener(view1 -> {
            Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            if (word.isEng())
                speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
            else
                speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "vi-VN");
            speechIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech to Text");
            startActivityForResult(speechIntent, RECOGNIZER_RESULT);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RECOGNIZER_RESULT && resultCode == RESULT_OK) {
            assert data != null;
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            assert matches != null;
            if (txtCurrentWord.getText().toString().toLowerCase().equals(matches.get(0).toLowerCase())) {
                Toast.makeText(getContext(), "Correct", Toast.LENGTH_SHORT).show();
                addPoint();
                addToYourWords(word);
                word = getRandomWord();
                txtCurrentWord.setText(word.getWord());
            } else {
                Toast.makeText(getContext(), "Incorrect", Toast.LENGTH_SHORT).show();
                word = getRandomWord();
                txtCurrentWord.setText(word.getWord());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private Word getRandomWord() {
        if (swEngVie.isChecked()) {
            return MainActivity.engVieWords.get(getRandomNumberInRange(0, MainActivity.engVieWords.size() - 1));
        } else {
            return MainActivity.vieEngWords.get(getRandomNumberInRange(0, MainActivity.vieEngWords.size() - 1));
        }
    }

    @SuppressLint("SetTextI18n")
    private void addPoint() {
        point += 10;
        txtPoint.setText(point + " Points");
    }

    private void addToYourWords(Word word) {
        if (!words.contains(word)) {
            words.add(word);
            adapter.notifyDataSetChanged();
        }
    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}