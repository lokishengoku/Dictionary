package burstcode.dictionary.db;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import burstcode.dictionary.model.Word;

public class FavoriteWordsRepository {
    private WordDao wordDao;
    private static List<Word> words;

    public FavoriteWordsRepository(Application application) {
        WordDatabase wordDatabase = WordDatabase.getInstance(application);
        wordDao = wordDatabase.wordDao();
        words = wordDao.getAllWords();
    }

    public void insertWords(Word newWord) {
        new InsertWordAsyncTask(wordDao).execute(newWord);
    }

    public void deleteWord(Word word) {
        new DeleteWordAsyncTask(wordDao).execute(word);
    }

    public List<Word> getWords() {
        return words;
    }

    private static class InsertWordAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDao wordDao;

        private InsertWordAsyncTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }


        @Override
        protected Void doInBackground(Word... words) {
            wordDao.insertAll(words[0]);
            return null;
        }
    }

    private static class DeleteWordAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDao wordDao;

        private DeleteWordAsyncTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }


        @Override
        protected Void doInBackground(Word... words) {
            wordDao.delete(words[0].getWord());
            return null;
        }
    }

}
