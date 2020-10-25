package burstcode.dictionary.ui.eng_vie;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import burstcode.dictionary.MainActivity;
import burstcode.dictionary.R;
import burstcode.dictionary.adapter.WordsAdapter;
import burstcode.dictionary.model.Word;


import static android.app.Activity.RESULT_OK;
import static burstcode.dictionary.MainActivity.toolbar;


public class EngVieFragment extends Fragment {
    private static final int RECOGNIZER_RESULT = 1;
    @SuppressLint("StaticFieldLeak")
    private static WordsAdapter adapter;
    private static ProgressDialog pDialog;
    public static boolean showDialog = true;

    private EditText edtSearch;

    public EngVieFragment() {
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
        return inflater.inflate(R.layout.fragment_eng_vie, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar.setTitle("English - Vietnamese");


        List<Word> words = new ArrayList<>(MainActivity.engVieWords);
        adapter = new WordsAdapter(words, view.getContext(), MainActivity.ENG_VIE);
        RecyclerView recyclerView = view.findViewById(R.id.engVieRecView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter.onSearch("");

        if (showDialog) {
            pDialog = new ProgressDialog(view.getContext());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        } else {
            pDialog.dismiss();
        }

        edtSearch = view.findViewById(R.id.searchEngVie);
        ImageView btnMicro = view.findViewById(R.id.btnMicro);

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.onSearch(charSequence.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnMicro.setOnClickListener(view1 -> {
            Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
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
            edtSearch.setText(matches.get(0).toLowerCase());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public static void updateData() {
        List<Word> words = new ArrayList<>(MainActivity.engVieWords);
        adapter.setWords(words);
        showDialog = false;
        pDialog.dismiss();
    }


}