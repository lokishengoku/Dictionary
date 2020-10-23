package burstcode.dictionary.ui.eng_vie;

import android.app.ProgressDialog;
import android.os.AsyncTask;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import burstcode.dictionary.MainActivity;
import burstcode.dictionary.R;
import burstcode.dictionary.adapter.EngVieAdapter;
import burstcode.dictionary.db.DatabaseAccess;
import burstcode.dictionary.model.Word;


public class EngVieFragment extends Fragment {
    private static final String TAG = "EngVieFragment";
    private ProgressDialog pDialog;
    public static List<Word> engVieWords;
    private EngVieAdapter adapter;


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

        engVieWords = new ArrayList<>();
        adapter = new EngVieAdapter(engVieWords);
        RecyclerView recyclerView = view.findViewById(R.id.engVieRecView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));


//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(view.getContext());
//                databaseAccess.openAnhViet();
//                engVieWords = databaseAccess.getWordsAnhViet();
//                databaseAccess.closeAnhViet();
//                adapter.setWords(engVieWords);
//                adapter.notifyDataSetChanged();
//            }
//        });

        asyncRun();

    }

    public void asyncRun() {
        new GetWords().execute();
    }

    public class GetWords extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getContext());
            databaseAccess.openAnhViet();
            engVieWords = databaseAccess.getWordsAnhViet();
            databaseAccess.closeAnhViet();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            adapter.setWords(engVieWords);
            pDialog.dismiss();
        }
    }

}