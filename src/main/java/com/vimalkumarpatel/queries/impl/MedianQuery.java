package com.vimalkumarpatel.queries.impl;

import com.vimalkumarpatel.queries.Query;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.stat.descriptive.rank.Median;

import java.util.*;

public abstract class MedianQuery<T> implements Query<T> {

    List<Double> collectedValues;
    Median median;

    MedianQuery(){
        collectedValues = new ArrayList<>();
        this.median = new Median();
        median.setData(new double [0]);
    }
    /**
     * support method to calculate the medians of a batch
     * @param data
     * @return
     */
    OptionalDouble getMedian(double [] data) {
        if(data != null && data.length == 0) return OptionalDouble.empty();

        Double [] dataObj = ArrayUtils.toObject(data);
        collectedValues.addAll(Arrays.asList(dataObj));

        Median tempMedian = new Median(); tempMedian.setData(data);
        double batchMedian = tempMedian.evaluate();
        return (batchMedian == Double.NaN)?OptionalDouble.empty():OptionalDouble.of(batchMedian);
    }


    public Optional<Double> output() {
        if (collectedValues.size() == 0) {
            return Optional.empty();
        } else {
            double [] values = collectedValues.stream().mapToDouble(i->i).toArray();
            double totalMedian = median.evaluate(values);
            return (totalMedian == Double.NaN)?Optional.empty():Optional.of(totalMedian);
        }
    }

    @Override
    public void reset() {
        collectedValues.clear();
        median = new Median();
    }

}
