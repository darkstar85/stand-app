package com.example.notes.resources;

import com.example.notes.api.NoteJson;
import com.example.notes.core.model.Note;
import com.example.notes.core.service.NoteService;
import com.yammer.metrics.annotation.Timed;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static com.example.notes.api.NoteJson.create;

@Path("/notes")
public class NotesResource {

    private final NoteService noteService;

    public NotesResource(NoteService noteService) {
        this.noteService = noteService;
    }

    @GET
    @Timed
    @Path(value = "/list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<NoteJson> list() {
        List<NoteJson> notes = new ArrayList<NoteJson>();
        List<Note> foundNotes = noteService.getAll();
        for (Note note : foundNotes) {
            notes.add(create(note));
        }
        return notes;
    }

    @POST
    @Timed
    @Path(value = "/addnote")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addNote(@Valid NoteJson note) {
        noteService.addNote(note);
        return Response.noContent().build();
    }

    @DELETE
    @Timed
    @Path(value = "/removeNote/{id}")
    public Response removeNote(@PathParam("id") String id) {
        noteService.removeNote(id);
        return Response.noContent().build();
    }
}