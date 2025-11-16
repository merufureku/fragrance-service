package com.merufureku.aromatica.fragrance_service.helper;

import com.merufureku.aromatica.fragrance_service.dao.entity.Fragrance;
import com.merufureku.aromatica.fragrance_service.dto.params.UpdateFragranceParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Component
public class FragranceHelper {

    private final Logger logger = LogManager.getLogger(this.getClass());

    public Fragrance updateFragrance(Fragrance existingFragrance, UpdateFragranceParam param) {
        setIfNotNull(param::brand, existingFragrance::setBrand);
        setIfNotNull(param::description, existingFragrance::setDescription);
        setIfNotNull(param::type, existingFragrance::setType);
        setIfNotNull(param::countryOfOrigin, existingFragrance::setCountryOfOrigin);
        setIfNotNull(param::gender, existingFragrance::setGender);
        setIfNotNull(param::releaseYear, existingFragrance::setReleaseYear);
        setIfNotNull(param::text, existingFragrance::setImageUrl);

        logger.info("Fragrance has been updated: {}", existingFragrance);

        return existingFragrance;
    }

    private static <T> void setIfNotNull(Supplier<T> source, Consumer<T> target) {
        T value = source.get();
        if (value != null) {
            target.accept(value);
        }
    }

}
