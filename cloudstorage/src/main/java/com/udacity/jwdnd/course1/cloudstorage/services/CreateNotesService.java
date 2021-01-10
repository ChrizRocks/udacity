package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreateNotesService {

    private final NoteMapper noteMapper;

    public CreateNotesService(NoteMapper noteMapper) { this.noteMapper = noteMapper;}

    public List<Note> getNotes(int userId){
        return noteMapper.getAllNotesByUserId(userId);
    }

    public int createNote(Note note){
        return noteMapper.insert(note);
    }

    public int updateNote(Note note){
        return noteMapper.updateNote(note);
    }

    public int deleteNote(int noteId){
        return noteMapper.deleteNote(noteId);
    }
}
