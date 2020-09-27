package burstcode.dictionary.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

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

    public void open() {
        this.databaseAnhViet = openHelperAnhViet.getWritableDatabase();
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

    //Data getter for anh_viet
    public List<Word> getWordsAnhViet() {
        List<Word> list = new ArrayList<>();
        Cursor cursor = databaseAnhViet.rawQuery("SELECT * FROM anh_viet", null);
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

    public List<Word> getFavoriteAnhViet() {
        List<Word> list = new ArrayList<>();
        Cursor cursor = databaseAnhViet.rawQuery("SELECT * FROM anh_viet", null);
        Cursor cursorFavorite = databaseAnhViet.rawQuery("SELECT * FROM favorite", null);
        cursorFavorite.moveToFirst();
        while (!cursorFavorite.isAfterLast()) {
            int index = cursorFavorite.getInt(cursor.getColumnIndex(KEY_WORD_ID));
            Word word = new Word();
            word.setId(index);
            word.setWord(cursor.getString(index));
            word.setContent(cursor.getString(index));

            list.add(word);
            cursorFavorite.moveToNext();
        }
        cursor.close();
        cursorFavorite.close();
        return list;
    }

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

    //Data getter for viet_anh
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

    public List<Word> getFavoriteVietAnh() {
        List<Word> list = new ArrayList<>();
        Cursor cursor = databaseVietAnh.rawQuery("SELECT * FROM viet_anh", null);
        Cursor cursorFavorite = databaseVietAnh.rawQuery("SELECT * FROM favorite", null);
        cursorFavorite.moveToFirst();
        while (!cursorFavorite.isAfterLast()) {
            int index = cursorFavorite.getInt(cursor.getColumnIndex(KEY_WORD_ID));
            Word word = new Word();
            word.setId(index);
            word.setWord(cursor.getString(index));
            word.setContent(cursor.getString(index));

            list.add(word);
            cursorFavorite.moveToNext();
        }
        cursor.close();
        cursorFavorite.close();
        return list;
    }

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
