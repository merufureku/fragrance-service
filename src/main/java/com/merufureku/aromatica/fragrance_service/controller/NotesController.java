package com.merufureku.aromatica.fragrance_service.controller;

import com.merufureku.aromatica.fragrance_service.dto.params.BaseParam;
import com.merufureku.aromatica.fragrance_service.dto.params.InsertNoteParam;
import com.merufureku.aromatica.fragrance_service.dto.responses.BaseResponse;
import com.merufureku.aromatica.fragrance_service.dto.responses.NoteListResponse;
import com.merufureku.aromatica.fragrance_service.dto.responses.NoteResponse;
import com.merufureku.aromatica.fragrance_service.services.factory.NotesServiceFactory;
import com.merufureku.aromatica.fragrance_service.services.interfaces.INotesService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class NotesController {

    private final NotesServiceFactory notesServiceFactory;

    public NotesController(NotesServiceFactory notesServiceFactory) {
        this.notesServiceFactory = notesServiceFactory;
    }

    @GetMapping("/public/notes")
    @Operation(summary = "Get notes")
    public ResponseEntity<BaseResponse<NoteListResponse>> getNote(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String type,
            @RequestParam(required = false, defaultValue = "1") int version,
            @RequestParam(required = false, defaultValue = "") String correlationId,
            Pageable pageable) {

        var baseParam = new BaseParam(version, correlationId);
        var response = notesServiceFactory.getService(version).getNotes(name, type, pageable, baseParam);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/notes")
    @Operation(summary = "Insert new notes")
    public ResponseEntity<BaseResponse<Void>> insertNotes(
            @Valid @RequestBody InsertNoteParam param,
            @RequestParam(required = false, defaultValue = "1") int version,
            @RequestParam(required = false, defaultValue = "") String correlationId) {

        var baseParam = new BaseParam(version, correlationId);
        var response = notesServiceFactory.getService(version).insertNotes(param, baseParam);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/notes/{id}")
    @Operation(summary = "Get note by ID")
    public ResponseEntity<BaseResponse<NoteResponse>> getNote(
            @PathVariable(name = "id") Long id,
            @RequestParam(required = false, defaultValue = "1") int version,
            @RequestParam(required = false, defaultValue = "") String correlationId) {

        var baseParam = new BaseParam(version, correlationId);
        var response = notesServiceFactory.getService(version).getNoteById(id, baseParam);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/notes/{id}")
    @Operation(summary = "Delete note by ID")
    public ResponseEntity<Void> deleteNote(
            @PathVariable(name = "id") Long id,
            @RequestParam(required = false, defaultValue = "1") int version,
            @RequestParam(required = false, defaultValue = "") String correlationId) {

        var baseParam = new BaseParam(version, correlationId);
        notesServiceFactory.getService(version).deleteNoteById(id, baseParam);

        return ResponseEntity.noContent().build();
    }
}


