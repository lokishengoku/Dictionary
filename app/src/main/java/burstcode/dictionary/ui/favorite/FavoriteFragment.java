package burstcode.dictionary.ui.favorite;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import burstcode.dictionary.MainActivity;
import burstcode.dictionary.R;
import burstcode.dictionary.adapter.WordsAdapter;
import burstcode.dictionary.model.Word;

import static android.app.Activity.RESULT_OK;

public class FavoriteFragment extends Fragment {
    public static int REQUEST_FAVORITE = 6;
    private RecyclerView recyclerView;
    private static WordsAdapter adapter;

    public FavoriteFragment() {
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
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recViewFavorite);
        adapter = new WordsAdapter(MainActivity.favoriteWords, getContext(), MainActivity.FAVORITE);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    public static void updateUI() {
        adapter.setWords(MainActivity.favoriteWords);
    }
}