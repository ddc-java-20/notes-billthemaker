package edu.cnm.deepdive.notes.viewmodel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import edu.cnm.deepdive.notes.databinding.ItemNoteBinding;
import edu.cnm.deepdive.notes.model.entity.Note;
import edu.cnm.deepdive.notes.viewmodel.NotesAdapter.Holder;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<ViewHolder> {

  private final LayoutInflater inflater;
  private final List<Note> notes;

  public NotesAdapter(Context context, List<Note> notes) {
    this.notes = notes;
    inflater = LayoutInflater.from(context);
  }


  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
    ItemNoteBinding binding = ItemNoteBinding.inflate(inflater, viewGroup, false);
    return new Holder(binding);

  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    ((Holder) holder).bind(notes.get(position));
    // TODO: 2025-02-13 Invoke holder.bind with the object in position i.
  }

  @Override
  public int getItemCount() {
    return notes.size();
  }

  static class Holder extends ViewHolder {

    private ItemNoteBinding binding;

    public Holder(@NonNull ItemNoteBinding binding) {
      super(binding.getRoot());
      this.binding = binding;

    }

    public void bind(Note note) {
      binding.title.setText(note.getTitle());
      binding.modifiedOn.setText(note.getModifiedOn().toString());
    };
  }

}
