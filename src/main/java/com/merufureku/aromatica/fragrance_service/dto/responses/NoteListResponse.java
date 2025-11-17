package com.merufureku.aromatica.fragrance_service.dto.responses;

import org.springframework.data.domain.Pageable;

import java.util.List;

public record NoteListResponse(List<NoteResponse> noteResponseList, Pageable pageable) {}
