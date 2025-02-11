package edu.cnm.deepdive.notes.viewmodel;

import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.cnm.deepdive.notes.service.NotesRepository;
import javax.inject.Inject;

@HiltViewModel
public class NoteViewModel extends ViewModel {

  private final NotesRepository notesRepository;

  @Inject
  NoteViewModel(NoteRepository noteRepository) {
    this.notesRepository = noteRepository;
  }
}
