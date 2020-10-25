package burstcode.dictionary.db;

import androidx.room.Dao;
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

    @Query("DELETE FROM word WHERE word.word=:wordX")
    void delete(String wordX);

}
