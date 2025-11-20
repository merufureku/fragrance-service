package com.merufureku.aromatica.fragrance_service.helper;

import com.merufureku.aromatica.fragrance_service.dao.entity.Fragrance;
import com.merufureku.aromatica.fragrance_service.dto.params.UpdateFragranceParam;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class FragranceHelperTest {

    @InjectMocks
    private FragranceHelper fragranceHelper;

    @Test
    void testUpdateFragrance_shouldUpdateOnlyNonNullFields() {
        var fragrance = Fragrance.builder()
                .id(1l)
                .build();

        var param = new UpdateFragranceParam("Parfums De Marly", "",
                "Niche", "France", "male",
                2022, "url");

        Fragrance response = fragranceHelper.updateFragrance(fragrance, param);

        assertEquals(param.brand(), response.getBrand());
        assertEquals(param.description(), response.getDescription());
        assertEquals(param.countryOfOrigin(), response.getCountryOfOrigin());
    }
}
