package com.merufureku.aromatica.fragrance_service.services.factory;

import com.merufureku.aromatica.fragrance_service.services.impl.NotesServiceImpl0;
import com.merufureku.aromatica.fragrance_service.services.impl.NotesServiceImpl1;
import com.merufureku.aromatica.fragrance_service.services.interfaces.INotesService;
import org.springframework.stereotype.Component;

@Component
public class NotesServiceFactory {

    private final NotesServiceImpl0 notesServiceImpl0;
    private final NotesServiceImpl1 notesServiceImpl1;

    public NotesServiceFactory(NotesServiceImpl0 notesServiceImpl0, NotesServiceImpl1 notesServiceImpl1) {
        this.notesServiceImpl0 = notesServiceImpl0;
        this.notesServiceImpl1 = notesServiceImpl1;
    }


    public INotesService getService(int version) {
        return switch (version) {
            case 0 -> notesServiceImpl0;
            case 1 -> notesServiceImpl1;
            default -> throw new IllegalArgumentException("Unsupported service version: " + version);
        };
    }
}
