package burstcode.dictionary.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import burstcode.dictionary.MainActivity;
import burstcode.dictionary.model.Word;

public class DatabaseAccess {
    private static final String KEY_WORD_ID = "id";
    private static final String KEY_WORD_WORD = "word";
    private static final String KEY_WORD_CONTENT = "content";

    private SQLiteAssetHelper openHelperAnhViet;
    private SQLiteAssetHelper openHelperVietAnh;
    private SQLiteDatabase databaseAnhViet;
    private SQLiteDatabase databaseVietAnh;
    private static DatabaseAccess instance;

    private DatabaseAccess(Context context) {
        this.openHelperAnhViet = new DatabaseOpenHelper(context, true);
        this.openHelperVietAnh = new DatabaseOpenHelper(context, false);
    }

    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) instance = new DatabaseAccess(context);
        return instance;
    }

    public void openAnhViet() {
        this.databaseAnhViet = openHelperAnhViet.getWritableDatabase();
    }

    public void openVietAnh() {
        this.databaseVietAnh = openHelperVietAnh.getWritableDatabase();
    }

    public void closeAnhViet() {
        if (databaseAnhViet != null) {
            this.databaseAnhViet.close();
        }
    }

    public void closeVietAnh() {
        if (databaseVietAnh != null) {
            this.databaseVietAnh.close();
        }
    }

    //---------------------------------------------
    //Data getter for anh_viet
    //Favorite words getter
    public List<Word> getWordsAnhViet() {
        List<Word> list = new ArrayList<>();
        Cursor cursor = databaseAnhViet.rawQuery("SELECT * FROM anh_viet", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Word word = new Word();
            word.setId(cursor.getInt(cursor.getColumnIndex(KEY_WORD_ID)));
            word.setWord(cursor.getString(cursor.getColumnIndex(KEY_WORD_WORD)));
            word.setContent(MainActivity.htmlConverter(cursor.getString(cursor.getColumnIndex(KEY_WORD_CONTENT))));

            list.add(word);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    //test hashmap
    public HashMap<Integer, Word> getMapEngVie() {
        HashMap<Integer, Word> map = new HashMap<>();
        Cursor cursor = databaseAnhViet.rawQuery("SELECT * FROM anh_viet", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Word word = new Word();
            word.setId(cursor.getInt(cursor.getColumnIndex(KEY_WORD_ID)));
            word.setWord(cursor.getString(cursor.getColumnIndex(KEY_WORD_WORD)));
            word.setContent(MainActivity.htmlConverter(cursor.getString(cursor.getColumnIndex(KEY_WORD_CONTENT))));
            map.put(cursor.getInt(cursor.getColumnIndex(KEY_WORD_ID)) - 1, word);
            cursor.moveToNext();
        }
        cursor.close();
        return map;
    }

    //Favorite words getter
    public List<Word> getFavoriteAnhViet() {
        List<Word> list = new ArrayList<>();
        Cursor cursor = databaseAnhViet.rawQuery("SELECT * FROM anh_viet", null);
        Cursor cursorFavorite = databaseAnhViet.rawQuery("SELECT * FROM favorite", null);
        cursorFavorite.moveToFirst();
        while (!cursorFavorite.isAfterLast()) {
            int index = cursorFavorite.getInt(cursor.getColumnIndex(KEY_WORD_ID));
            cursor.moveToPosition(index);
            Word word = new Word();
            word.setId(index);
            word.setWord(cursor.getString(cursor.getColumnIndex(KEY_WORD_WORD)));
            word.setContent(cursor.getString(cursor.getColumnIndex(KEY_WORD_CONTENT)));

            list.add(word);
            cursorFavorite.moveToNext();
        }
        cursor.close();
        cursorFavorite.close();
        return list;
    }

    //Your words getter
    public List<Word> getYourWordAnhViet() {
        List<Word> list = new ArrayList<>();
        Cursor cursor = databaseAnhViet.rawQuery("SELECT * FROM your_word", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Word word = new Word();
            word.setId(cursor.getInt(cursor.getColumnIndex(KEY_WORD_ID)));
            word.setWord(cursor.getString(cursor.getColumnIndex(KEY_WORD_WORD)));
            word.setContent(cursor.getString(cursor.getColumnIndex(KEY_WORD_CONTENT)));

            list.add(word);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    //----------------------------------------------
    //Data getter for viet_anh
    //Favorite words getter
    public List<Word> getWordsVietAnh() {
        List<Word> list = new ArrayList<>();
        Cursor cursor = databaseVietAnh.rawQuery("SELECT * FROM viet_anh", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Word word = new Word();
            word.setId(cursor.getInt(cursor.getColumnIndex(KEY_WORD_ID)));
            word.setWord(cursor.getString(cursor.getColumnIndex(KEY_WORD_WORD)));
            word.setContent(cursor.getString(cursor.getColumnIndex(KEY_WORD_CONTENT)));

            list.add(word);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    //Favorite words getter
    public List<Word> getFavoriteVietAnh() {
        List<Word> list = new ArrayList<>();
        Cursor cursor = databaseVietAnh.rawQuery("SELECT * FROM viet_anh", null);
        Cursor cursorFavorite = databaseVietAnh.rawQuery("SELECT * FROM favorite", null);
        cursorFavorite.moveToFirst();
        while (!cursorFavorite.isAfterLast()) {
            int index = cursorFavorite.getInt(cursor.getColumnIndex(KEY_WORD_ID));
            cursor.moveToPosition(index);
            Word word = new Word();
            word.setId(index);
            word.setWord(cursor.getString(cursor.getColumnIndex(KEY_WORD_WORD)));
            word.setContent(cursor.getString(cursor.getColumnIndex(KEY_WORD_CONTENT)));

            list.add(word);
            cursorFavorite.moveToNext();
        }
        cursor.close();
        cursorFavorite.close();
        return list;
    }

    //Your words getter
    public List<Word> getYourWordVietAnh() {
        List<Word> list = new ArrayList<>();
        Cursor cursor = databaseVietAnh.rawQuery("SELECT * FROM your_word", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Word word = new Word();
            word.setId(cursor.getInt(cursor.getColumnIndex(KEY_WORD_ID)));
            word.setWord(cursor.getString(cursor.getColumnIndex(KEY_WORD_WORD)));
            word.setContent(cursor.getString(cursor.getColumnIndex(KEY_WORD_CONTENT)));

            list.add(word);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
}
