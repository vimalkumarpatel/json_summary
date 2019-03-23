package com.vimalkumarpatel.queries.impl;

import com.vimalkumarpatel.queries.Query;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class GroupingByQuery<T, K> implements Query<T> {

    Map<K, Long> simpleGroupByAndCounting(Stream<T> data, Function<T, K> classifierFunction){
        return data.collect(Collectors.groupingBy(classifierFunction, Collectors.counting()));
    }
}
