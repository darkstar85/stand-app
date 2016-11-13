package com.example.notes.mdb;

import com.example.notes.core.model.Note;
import com.mongodb.DB;
import org.bson.types.ObjectId;
import org.mongojack.DBCursor;
import org.mongojack.DBQuery;
import org.mongojack.JacksonDBCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoteDao {

    private static final Logger log = LoggerFactory.getLogger(NoteDao.class);

    private static final String NOTE_COLLECTION = "note";
    public static final String ID_FIELD = "_id";

    private final JacksonDBCollection<Note, Object> noteCollection;

    public NoteDao(DB db) {
        noteCollection = JacksonDBCollection.wrap(db.getCollection(NOTE_COLLECTION), Note.class);
    }

    public Note getBy(ObjectId id) {
	    return noteCollection.findOne(DBQuery.is(ID_FIELD, id));
    }

    public DBCursor<Note> getAll() {
        return noteCollection.find();
    }

    public ObjectId create(Note note) {
        log.info("Inserting new note");
        return (ObjectId) noteCollection.insert(note).getSavedId();
    }

    public void delete(Note note) {
        log.info("Deleting note with id: {}", note.get_id().toString());
        noteCollection.remove(DBQuery.is(ID_FIELD, note.get_id()));
    }
}
