package com.ms.tourist_app.config;

import com.fasterxml.uuid.Generators;
import org.springframework.stereotype.Component;

@Component
public class IdGenerator {
    public Long generateId() {
        return Generators.timeBasedGenerator().generate().timestamp();
    }
}
