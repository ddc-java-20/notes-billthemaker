package edu.cnm.deepdive.notes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import edu.cnm.deepdive.notes.databinding.ItemNoteBinding;
import edu.cnm.deepdive.notes.model.entity.Note;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<ViewHolder> {

  private final LayoutInflater inflater;
  private final List<Note> notes;
  private final OnLongClickListener listener;

  public NotesAdapter(@NonNull Context context,@NonNull List<Note> notes, @NonNull OnLongClickListener listener) {
    this.notes = notes;
    inflater = LayoutInflater.from(context);
    this.listener = listener;
  }


  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
    ItemNoteBinding binding = ItemNoteBinding.inflate(inflater, viewGroup, false);
    return new Holder(binding, listener);

  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    ((Holder) holder).bind(position, notes.get(position));
    // TODO: 2025-02-13 Invoke holder.bind with the object in position i.
  }

  @Override
  public int getItemCount() {
    return notes.size();
  }

  private static class Holder extends ViewHolder {

    private ItemNoteBinding binding;
    private final OnLongClickListener listener;

    public Holder(@NonNull ItemNoteBinding binding, @NonNull OnLongClickListener listener) {
      super(binding.getRoot());
      this.binding = binding;

      this.listener = listener;
    }

    public void bind(int position, Note note) {
      binding.title.setText(note.getTitle());
      binding.modifiedOn.setText(note.getModifiedOn().toString());
      binding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
          return listener.onLongClick(view, note, position);
        }
      });
    };
  }

  @FunctionalInterface
  public interface OnLongClickListener {

    boolean onLongClick(View view, Note note, int position);
  }
}
