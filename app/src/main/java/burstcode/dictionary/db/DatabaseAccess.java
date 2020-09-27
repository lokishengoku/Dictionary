package burstcode.dictionary.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.List;

public class DatabaseAccess {
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

    public void close() {
        if (databaseVietAnh != null && databaseAnhViet != null) {
            this.databaseAnhViet.close();
            this.databaseVietAnh.close();
        }
    }

//    public List<String> getWords(){
//
//    }
}
