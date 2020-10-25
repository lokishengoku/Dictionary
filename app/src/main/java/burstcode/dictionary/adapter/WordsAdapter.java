package burstcode.dictionary.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import burstcode.dictionary.MainActivity;
import burstcode.dictionary.R;
import burstcode.dictionary.model.Word;
import burstcode.dictionary.ui.WordDetailActivity;
import burstcode.dictionary.ui.favorite.FavoriteFragment;

public class WordsAdapter extends RecyclerView.Adapter<WordsAdapter.ViewHolder> {
    private Context mContext;
    private int target;
    private List<Word> words;
    private List<Word> allWords;

    public WordsAdapter(List<Word> words, Context context, int target) {
        this.mContext = context;
        this.target = target;
        this.words = words;
        this.allWords = new ArrayList<>(words);
    }

    public void setWords(List<Word> words) {
        this.words = words;
        this.allWords.clear();
        this.allWords.addAll(words);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.txtWord.setText(words.get(position).getWord());
        holder.itemParents.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, WordDetailActivity.class);
            intent.putExtra("word", words.get(position));
            intent.putExtra("target", target);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtWord;
        private CardView itemParents;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtWord = itemView.findViewById(R.id.txtWord);
            itemParents = itemView.findViewById(R.id.list_item_parents);
        }
    }

    public void onSearch(String str) {
        if (str.isEmpty()) {
            this.words.clear();
            this.words.addAll(this.allWords);
            notifyDataSetChanged();
        } else {
            this.words.clear();
            for (Word word :
                    this.allWords) {
                if (word.getWord().toLowerCase().contains(str))
                    this.words.add(word);
            }
            notifyDataSetChanged();
        }
    }


}
