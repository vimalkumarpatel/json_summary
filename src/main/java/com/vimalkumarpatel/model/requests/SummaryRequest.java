package com.vimalkumarpatel.model.requests;

import java.util.Map;
import java.util.Optional;

public class SummaryRequest {
    private Map<String, Optional> optionals;

    public Map<String, Optional> getOptionals() {
        return optionals;
    }

    public void setOptionals(Map<String, Optional> optionals) {
        this.optionals = optionals;
    }
}
