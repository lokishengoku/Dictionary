package burstcode.dictionary;

import android.Manifest;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;

import android.view.Menu;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import burstcode.dictionary.db.DatabaseAccess;
import burstcode.dictionary.db.FavoriteWordsRepository;
import burstcode.dictionary.model.Word;
import burstcode.dictionary.ui.eng_vie.EngVieFragment;
import burstcode.dictionary.ui.vie_eng.VieEngFragment;


public class MainActivity extends AppCompatActivity {
    public static final Integer RecordAudioRequestCode = 1;
    public static final Integer ENG_VIE = 1;
    public static final Integer VIE_ENG = 2;
    public static final Integer FAVORITE = 3;
    public static final Integer YOUR_WORDS = 4;

    private AppBarConfiguration mAppBarConfiguration;

    private static FavoriteWordsRepository favoriteWordsRepository;
    @SuppressLint("StaticFieldLeak")
    public static Toolbar toolbar;
    public static List<Word> engVieWords = new ArrayList<>(),
            vieEngWords = new ArrayList<>(),
            favoriteWords = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_eng_vie, R.id.nav_vie_eng, R.id.nav_favorite, R.id.nav_your_word)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            checkPermission();
        }


        asyncRun();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public static String htmlConverter(String str) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(str, Html.FROM_HTML_MODE_COMPACT).toString();
        } else {
            return Html.fromHtml(str).toString();
        }
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, RecordAudioRequestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RecordAudioRequestCode && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        }
    }

    public void asyncRun() {
        new GetEngVieWords().execute();
        new GetVieEngWords().execute();
        new GetFavoriteWords().execute();
    }

    @SuppressLint("StaticFieldLeak")
    public class GetEngVieWords extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplication());
            databaseAccess.openAnhViet();
            engVieWords = databaseAccess.getWordsAnhViet();
            databaseAccess.closeAnhViet();

//            databaseAccess.openVietAnh();
//            vieEngWords = databaseAccess.getWordsVietAnh();
//            databaseAccess.closeVietAnh();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            EngVieFragment.updateData();
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class GetVieEngWords extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplication());
            databaseAccess.openVietAnh();
            vieEngWords = databaseAccess.getWordsVietAnh();
            databaseAccess.closeVietAnh();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            VieEngFragment.updateData();
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class GetFavoriteWords extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            favoriteWordsRepository = new FavoriteWordsRepository(getApplication());
            List<Word> dbWords = favoriteWordsRepository.getWords();
            favoriteWords.addAll(dbWords);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

        }
    }

    public static void removeFavorite(Word word) {
        favoriteWordsRepository.deleteWord(word);
        for (int i = 0; i < favoriteWords.size(); i++) {
            if (word.getWord().equals(favoriteWords.get(i).getWord())) {
                favoriteWords.remove(i);
                break;
            }
        }
    }

    public static void insertFavorite(Word word) {
        favoriteWordsRepository.insertWords(word);
    }
}