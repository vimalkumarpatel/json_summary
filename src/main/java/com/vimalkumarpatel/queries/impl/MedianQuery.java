package com.vimalkumarpatel.queries.impl;

import com.vimalkumarpatel.queries.Query;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class MedianQuery<T> implements Query<T> {

    PriorityQueue<Double> collectedValues;

    /**
     * support method to calculate the medians of a batch
     * @param unsortedData
     * @param comparator
     * @param mappingFunction
     * @return
     */
    List<Double> getMedianElements(List<Double> unsortedData, Comparator<Double> comparator, Function mappingFunction) {
        if(unsortedData.size()<2) return unsortedData;
        Stream<Double> sortedStream = unsortedData.stream().sorted(comparator);
        List<Double> medianList = unsortedData.size()%2 == 0?
                sortedStream.skip(unsortedData.size()/2-1).limit(2).collect(Collectors.toList()):
                sortedStream.skip(unsortedData.size()/2).limit(1).collect(Collectors.toList());

        return medianList;
    }


    public Optional<Double> output() {
        if (collectedValues.size() == 0) {
            return Optional.empty();
        } else {
            int totalSize = collectedValues.size();
            int toPoll = totalSize%2==0?totalSize/2-1:totalSize/2;
            for(int i=0;i<toPoll;i++){
                collectedValues.poll();
            }

            if(totalSize%2==0) {
                double d1 = collectedValues.poll();
                double d2 = collectedValues.poll();
                return Optional.of(new Double((d1+d2)/2));
            } else {
                double d1 = collectedValues.poll();
                return Optional.of(new Double(d1));
            }
        }
    }

}
