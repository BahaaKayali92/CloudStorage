package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.springframework.stereotype.Service;

@Service
public class NoteService {
    public NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public Note[] getNotesByUserId(Integer userid) {
        return noteMapper.getNote(userid);
    }

    public int saveNote(Note note) {
        return noteMapper.insert(note);
    }

    public int updateNote(Note note) {
        return noteMapper.updateNote(note);
    }

    public int deleteNote(Integer noteid) {
        return noteMapper.deleteNote(noteid);
    }
}
