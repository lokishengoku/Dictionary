package burstcode.dictionary.ui.eng_vie;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import burstcode.dictionary.MainActivity;
import burstcode.dictionary.R;
import burstcode.dictionary.db.DatabaseAccess;
import burstcode.dictionary.model.Word;


public class EngVieFragment extends Fragment {
    private static final String TAG = "EngVieFragment";
    private List<Word> words;

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

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(view.getContext());
                databaseAccess.openAnhViet();
                words = databaseAccess.getWordsAnhViet();
                databaseAccess.closeAnhViet();
                for (Word word : words) Log.d(TAG, "onViewCreated: " + word.getWord());
            }
        });

    }
}