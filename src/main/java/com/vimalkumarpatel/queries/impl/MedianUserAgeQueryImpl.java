package com.vimalkumarpatel.queries.impl;

import com.vimalkumarpatel.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class MedianUserAgeQueryImpl extends MedianQuery<User> {

    private static final Logger logger = LoggerFactory.getLogger(MedianUserAgeQueryImpl.class);

    public MedianUserAgeQueryImpl() {
        this.collectedValues = new PriorityQueue<>();
    }

    @Override
    public Optional process(List<User> users) {
        logger.debug("Processing Median User Age for batch");
        List<Double> userAges = users
                .stream()
                .mapToDouble(User::getAge).boxed().collect(Collectors.toList());
        collectedValues.addAll(userAges);

        List<Double> medianAgesForBatch = getMedianElements(userAges, Comparator.naturalOrder(), null);

        logger.debug("Median Age(s) found, medianAgesForBatch = {}", medianAgesForBatch);
        double sum = 0;
        for (double medians : medianAgesForBatch) {
            sum = sum + medians;
        }
        Double median = sum / medianAgesForBatch.size();
        logger.debug("Median Age in current batch = {}, collectedValues.size()", median, collectedValues.size());
        return Optional.of(median);
    }

    @Override
    public void init() {

    }

    @Override
    public String getMessageString() {
        return "Median age for Users";
    }
}
