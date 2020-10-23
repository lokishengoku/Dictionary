package burstcode.dictionary.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import burstcode.dictionary.R;
import burstcode.dictionary.model.Word;

public class EngVieAdapter extends RecyclerView.Adapter<EngVieAdapter.ViewHolder> {
    private List<Word> words;

    public EngVieAdapter(List<Word> words) {
        this.words = words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtWord.setText(words.get(position).getWord());
        holder.txtContent.setText(words.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtWord, txtContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtWord = itemView.findViewById(R.id.txtWord);
            txtContent = itemView.findViewById(R.id.txtContent);
        }
    }
}
