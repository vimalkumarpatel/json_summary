package com.vimalkumarpatel.queries.impl;

import com.vimalkumarpatel.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

public class MedianUserAgeQueryImpl extends MedianQuery<User> {

    private static final Logger logger = LoggerFactory.getLogger(MedianUserAgeQueryImpl.class);


    @Override
    public Optional<Double> process(List<User> users) {
        logger.debug("Processing Median User Age for batch");
        double [] userAges = users
                .stream()
                .mapToDouble(User::getAge).toArray();

        OptionalDouble medianAgesForBatch = getMedian(userAges);
        logger.debug("Median Age in current batch = {},  medianAgesForBatch = {}", medianAgesForBatch, collectedValues.size());

        return (medianAgesForBatch.isPresent()) ? Optional.of(medianAgesForBatch.getAsDouble()) : Optional.empty();
    }

    @Override
    public void init() {

    }

    @Override
    public String getMessageString() {
        return "Median age for Users";
    }
}
