package com.merufureku.aromatica.fragrance_service.dto.params;

import java.util.Set;

public record ExcludeFragranceBatchParam(Set<Long> excludedFragranceIds) {}
