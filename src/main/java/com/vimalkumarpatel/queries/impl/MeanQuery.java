package com.vimalkumarpatel.queries.impl;

import com.vimalkumarpatel.queries.Query;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.ToDoubleFunction;

public abstract class MeanQuery<T> implements Query<T> {

    double totalSum;
    long totalCount;

    OptionalDouble getMean(List<T> data, ToDoubleFunction<T> mappingFuction){
        return data.stream().mapToDouble(mappingFuction).average();
    }

    OptionalDouble getMean(List<Integer> data){
        return data.stream().mapToInt(i -> i).average();
    }

    public Optional<Double> output() {
        if(totalCount == 0) {
            return Optional.empty();
        } else {
            return Optional.of(new Double(totalSum/totalCount));
        }
    }

    @Override
    public void reset() {
        this.totalCount = 0;
        this.totalSum = 0;
    }
}
