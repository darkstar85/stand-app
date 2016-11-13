package com.example.notes.api;

import com.example.notes.core.model.Note;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NoteJson {

    private String id;
    private Date created;

    @NotEmpty
    private String subject;
    @NotEmpty
    private String text;

    public static NoteJson create(Note note) {
        return new NoteJson(
                note.get_id().toString(),
                note.getCreated(),
                note.getSubject(),
                note.getText()
        );
    }

    public NoteJson(@JsonProperty("subject") String subject, @JsonProperty("text") String text) {
        this.created = new Date();
        this.subject = subject;
        this.text = text;
    }

    public NoteJson(String id, Date created, String subject, String text) {
        this.id = id;
        this.created = created;
        this.subject = subject;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
