package edu.cnm.deepdive.notes.viewmodel;

import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.cnm.deepdive.notes.model.entity.Note;
import edu.cnm.deepdive.notes.service.NoteRepository;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import java.util.List;
import javax.inject.Inject;

@HiltViewModel
public class NoteViewModel extends ViewModel implements DefaultLifecycleObserver {

  private final NoteRepository noteRepository;
  private final MutableLiveData<Long> noteId;
  private final LiveData<Note> note;
  private final MutableLiveData<Uri> captureUri;
  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending;

  private Uri pendingCaptureUri;

  @Inject
  NoteViewModel(NoteRepository noteRepository) {
    this.noteRepository = noteRepository;
    noteId = new MutableLiveData<>();
    note = Transformations.switchMap(noteId, noteRepository::get);
    captureUri = new MutableLiveData<>();
    throwable = new MutableLiveData<>();
    pending = new CompositeDisposable();
  }
  
  public void save(Note note) {
    throwable.setValue(null); // will be invoked from controller on UI thread. 
    noteRepository
        .save(note)
        .ignoreElement()
        .subscribe(
            () -> {},
            this::postThrowable,
            pending
        );

    // TODO: 2/12/2025 invoke the save mtehod of the repo to get the machine for saving
    // TODO: 2/12/2025 invoke the subscribe mthod on machine to start it and attach consumers
    //  there will be 2 consumers: one for a successful result(note)
    //  and one for an unsuccessful result (throwable). The first puts the note into a livedata
    //  bucket; the second invokes a helper method
  }

  public void fetch(long notedId) {
    throwable.setValue(null);
    // TODO: 2/18/2025 consider this.note.setValue(null) 
    this.noteId.setValue(notedId);
  }

  public void delete(Note note) {
    throwable.setValue(null);
    noteRepository
        .remove(note)
        .subscribe(
            () -> {},
            this::postThrowable,
            pending
        );
  }

  public void confirmCapture(boolean success) {
    captureUri.setValue(success ? pendingCaptureUri : null);
    pendingCaptureUri = null;
  }

  public LiveData<Long> getNoteId() {
    return noteId;
  }

  public LiveData<Note> getNote() {
    return note;
  }

  public LiveData<List<Note>> getNotes() {

    return noteRepository.getAll();
  }

  public LiveData<Uri> getCaptureUri() {
    return captureUri;
  }

  public LiveData<Throwable> getThrowable() {
    return throwable;
  }

  public void getPendingCaptureUri(Uri pendingCaptureUri) {
    this.pendingCaptureUri = pendingCaptureUri;
  }

  @Override
  public void onStop(@NonNull LifecycleOwner owner) {
    pending.clear();
    DefaultLifecycleObserver.super.onStop(owner);
  }

  private void postThrowable(Throwable throwable) {
    Log.e(NoteViewModel.class.getSimpleName(), throwable.getMessage(), throwable);
    this.throwable.setValue(throwable);
  }
}
