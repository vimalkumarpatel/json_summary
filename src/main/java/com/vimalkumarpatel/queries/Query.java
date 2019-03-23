package com.vimalkumarpatel.queries;

import java.util.List;
import java.util.Optional;

public interface Query<T> {
    Optional process(List<T> data);
    void reset();
    void init();
    Optional output();
    String getMessageString();
}
