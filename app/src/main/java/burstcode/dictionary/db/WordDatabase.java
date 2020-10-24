package burstcode.dictionary.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import burstcode.dictionary.model.Word;

@Database(entities = {Word.class}, version = 1)
public abstract class WordDatabase extends RoomDatabase {
    public abstract WordDao wordDao();

    public static WordDatabase instance;

    public static WordDatabase getInstance(Context mContext) {
        if (instance == null) {
            instance = Room.databaseBuilder(mContext,
                    WordDatabase.class,
                    "database-word")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
