package burstcode.dictionary.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
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
            if (cursor.getInt(cursor.getColumnIndex(KEY_WORD_ID)) != 58 && cursor.getInt(cursor.getColumnIndex(KEY_WORD_ID)) != 59
                    && cursor.getInt(cursor.getColumnIndex(KEY_WORD_ID)) != 60) {
                Word word = new Word();
                word.setId(cursor.getInt(cursor.getColumnIndex(KEY_WORD_ID)));
                word.setWord(cursor.getString(cursor.getColumnIndex(KEY_WORD_WORD)));
                word.setContent(MainActivity.htmlConverter(cursor.getString(cursor.getColumnIndex(KEY_WORD_CONTENT))));
                word.setEng(true);

                list.add(word);
            }
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
            if (cursor.getInt(cursor.getColumnIndex(KEY_WORD_ID)) != 1 && cursor.getInt(cursor.getColumnIndex(KEY_WORD_ID)) != 2
                    && cursor.getInt(cursor.getColumnIndex(KEY_WORD_ID)) != 3) {
                Word word = new Word();
                word.setId(cursor.getInt(cursor.getColumnIndex(KEY_WORD_ID)));
                word.setWord(cursor.getString(cursor.getColumnIndex(KEY_WORD_WORD)));
                word.setContent(MainActivity.htmlConverter(cursor.getString(cursor.getColumnIndex(KEY_WORD_CONTENT))));
                word.setEng(false);

                list.add(word);
            }
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

}
