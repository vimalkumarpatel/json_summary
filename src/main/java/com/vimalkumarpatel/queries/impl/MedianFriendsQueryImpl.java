package com.vimalkumarpatel.queries.impl;

import com.vimalkumarpatel.model.User;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.ToDoubleFunction;

public class MedianFriendsQueryImpl extends MedianQuery<User> {
    private static final Logger logger = LoggerFactory.getLogger(MedianFriendsQueryImpl.class);

    private ToDoubleFunction<User> userToNoOfFriends = new ToDoubleFunction<User>() {
        @Override
        public double applyAsDouble(User user) {
            return (ArrayUtils.isEmpty(user.getFriends())) ? 0 : user.getFriends().length;
        }
    };
    public MedianFriendsQueryImpl() {

    }

    @Override
    public Optional<Double> process(List<User> users) {
        logger.debug("Processing MedianFriends");
        double [] noOfFriends = users
                .stream()
                .mapToDouble(userToNoOfFriends).toArray();

        OptionalDouble medianFriendsOfBatch = getMedian(noOfFriends);

        logger.debug("Median friends found, medianFriendsOfBatch = {}", medianFriendsOfBatch);
        if(!medianFriendsOfBatch.isPresent()) return Optional.empty();
        logger.debug("Median number of Friends in current batch = {}, collectedValues.size()={}", medianFriendsOfBatch.getAsDouble(), collectedValues.size());
        return Optional.of(medianFriendsOfBatch.getAsDouble());
    }

    @Override
    public void init() {

    }

    @Override
    public String getMessageString() {
        return "Median for Number of Friends";
    }
}
