package com.vimalkumarpatel.queries.impl;

import com.vimalkumarpatel.model.Gender;
import com.vimalkumarpatel.model.User;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

public class MeanUnreadMessageActiveFemaleQueryImpl extends MeanQuery<User> {
    private static final Logger logger = LoggerFactory.getLogger(MeanUnreadMessageActiveFemaleQueryImpl.class);

    private final Predicate<User> userPredicate;
    private final ToIntFunction<User> toIntFunction;

    public MeanUnreadMessageActiveFemaleQueryImpl(){
        Predicate<User> activeFemalePredicate = new Predicate<User>() {
            @Override
            public boolean test(User user) {
                return (user.isActive() && Gender.female == user.getGender());
            }
        };

        ToIntFunction<User> userUnreadMessages = new ToIntFunction<User>() {
            @Override
            public int applyAsInt(User user) {
                if(StringUtils.isEmpty(user.getGreeting())) return 0;
                String [] msg = StringUtils.split(user.getGreeting());
                if(ArrayUtils.isEmpty(msg) || msg.length < 3) return 0;
                try{
                    return Integer.valueOf(msg[msg.length-3]).intValue();
                } catch (NumberFormatException ex) {
                    return 0;
                }
            }
        };
        this.userPredicate = activeFemalePredicate;
        this.toIntFunction = userUnreadMessages;
    }

    @Override
    public Optional<Double> process(List<User> users) {
        logger.debug("Processing Mean unread messages for Active Female users");
        double [] filteredUsersUnreadMessage = users
                .stream()
                .filter(userPredicate)
                .mapToInt(toIntFunction)
                .asDoubleStream()
                .toArray();
        logger.debug("Total active females in current batch = {}", filteredUsersUnreadMessage.length);

        OptionalDouble meanForBatch = getMean(filteredUsersUnreadMessage);
        Optional<Double> retValue = Optional.of(meanForBatch.isPresent() ? meanForBatch.getAsDouble() : new Double(0));
        logger.debug("Mean for current batch found ?={}, retValue={}", meanForBatch.isPresent(), retValue.get());

        return retValue;
    }

    @Override
    public void init() {

    }

    @Override
    public String getMessageString() {
        return "Mean for number of Unread messages for Active females";
    }
}
