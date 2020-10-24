package burstcode.dictionary.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import burstcode.dictionary.model.Word;

@Dao
public interface WordDao {
    @Query("SELECT * FROM word")
    List<Word> getAllWords();

    @Insert
    void insertAll(Word... words);

    @Delete
    void delete(Word word);

}
