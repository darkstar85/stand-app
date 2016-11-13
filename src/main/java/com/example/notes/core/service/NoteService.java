package com.example.notes.core.service;

import com.example.notes.api.NoteJson;
import com.example.notes.core.model.Note;
import com.example.notes.mdb.NoteDao;
import com.mongodb.DB;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class NoteService {

    private static final Logger log = LoggerFactory.getLogger(NoteDao.class);

    private final NoteDao noteDao;

    public NoteService(DB db) {
        noteDao = new NoteDao(db);
    }

    public List<Note> getAll() {
        return noteDao.getAll().toArray();
    }

    public void addNote(NoteJson noteJson) {
        log.debug("Adding new note");
        Note note = Note.create(noteJson);
        noteDao.create(note);
    }

    public void removeNote(String id) {
        log.debug("Removing note {}", id);
        ObjectId objectId = new ObjectId(id);
        Note note = noteDao.getBy(objectId);
        if (note != null) {
            noteDao.delete(note);
        }
    }
}
