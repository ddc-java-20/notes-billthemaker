package edu.cnm.deepdive.notes.service;

import edu.cnm.deepdive.notes.model.dao.NoteDao;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class NotesRepository {

  private final NoteDao noteDao;

  @Inject
  NotesRepository(NoteDao noteDao) {
    this.noteDao = noteDao;
  }
}
