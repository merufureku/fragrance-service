package com.merufureku.aromatica.fragrance_service.dto.params;

import java.util.Set;

public record ExcludeFragranceBatchNotesParam(Set<Long> excludedFragranceIds) {}
