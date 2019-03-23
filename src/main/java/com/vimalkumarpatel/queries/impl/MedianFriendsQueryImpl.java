package com.vimalkumarpatel.queries.impl;

import com.vimalkumarpatel.model.User;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class MedianFriendsQueryImpl extends MedianQuery<User> {
    private static final Logger logger = LoggerFactory.getLogger(MedianFriendsQueryImpl.class);

    public MedianFriendsQueryImpl() {
        super.collectedValues = new PriorityQueue<>();
    }

    @Override
    public Optional process(List<User> users) {
        logger.debug("Processing MedianFriends");
        List<Double> noOfFriends = users
                .stream()
                .mapToDouble(user -> (ArrayUtils.isEmpty(user.getFriends())) ? 0 : user.getFriends().length).boxed().collect(Collectors.toList());
        collectedValues.addAll(noOfFriends);

        List<Double> medianFriendsOfBatch = getMedianElements(noOfFriends, Comparator.naturalOrder(), null);

        logger.debug("Median friends found, medianFriendsOfBatch = {}", medianFriendsOfBatch);
        double sum = 0;
        for (double medians : medianFriendsOfBatch) {
            sum = sum + medians;
        }
        Double median = sum / medianFriendsOfBatch.size();
        logger.debug("Median number of Friends in current batch = {}, collectedValues.size()={}", median, collectedValues.size());
        return Optional.of(median);
    }

    @Override
    public void reset() {
        collectedValues.clear();
    }

    @Override
    public void init() {

    }

    @Override
    public String getMessageString() {
        return "Median for Number of Friends";
    }
}
