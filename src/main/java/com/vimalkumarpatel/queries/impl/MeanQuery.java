package com.vimalkumarpatel.queries.impl;

import com.vimalkumarpatel.queries.Query;
import org.apache.commons.math3.stat.descriptive.moment.Mean;

import java.util.Arrays;
import java.util.Optional;
import java.util.OptionalDouble;

public abstract class MeanQuery<T> implements Query<T> {

    Mean mean;

    MeanQuery() {
        mean = new Mean();
    }

    OptionalDouble getMean(double [] data){
        Mean tempMean = new Mean();
        Arrays.stream(data).forEach(d -> {
            tempMean.increment(d);
            mean.increment(d);
        });
        return OptionalDouble.of(tempMean.getResult());
    }

    public Optional<Double> output() {
        if(mean.getN() == 0) return Optional.empty();
        else return Optional.of(mean.getResult());
    }

    @Override
    public void reset() {
        mean.clear();
    }
}
