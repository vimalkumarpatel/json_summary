package com.vimalkumarpatel.queries.impl;

import com.vimalkumarpatel.model.User;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class UsersRegisterdByYearQueryImpl extends GroupingByQuery<User, String> {

    private static final Logger logger = LoggerFactory.getLogger(UsersRegisterdByYearQueryImpl.class);
    private Function<User, String> classifierFunction;
    private Map<String, Long> userCountByYearMap;

    public UsersRegisterdByYearQueryImpl(){
        this.userCountByYearMap = new HashMap<>();
        this.classifierFunction = new Function<User, String>() {
            @Override
            public String apply(User user) {
                if(StringUtils.isEmpty(user.getRegistered())) return "UNKNOWN_YEAR";
                String [] times = StringUtils.strip(user.getRegistered()).split("-");
                if(ArrayUtils.isEmpty(times) || StringUtils.isEmpty(times[0])) return "UNKNOWN_YEAR";
                else return times[0];
            }
        };
    }

    public Optional process(List<User> users) {
        logger.debug("Processing batch of Users to group by registered year.");
        Map<String, Long> usersByYear = simpleGroupByAndCounting(users.stream(), classifierFunction);
        logger.debug("simpleGroupByAndCounting results for batch: {}", usersByYear);
        userCountByYearMap = updateUserCountByYearMap(userCountByYearMap, usersByYear);
        return Optional.of(usersByYear);
    }

    private Map<String, Long> updateUserCountByYearMap(Map<String, Long> userCountByYearMap, Map<String, Long> usersByYear) {
        usersByYear.forEach((k,v)->{
            if(userCountByYearMap.get(k) == null) {
                userCountByYearMap.put(k, v);
            } else {
                userCountByYearMap.put(k, v + userCountByYearMap.get(k));
            }
        });
        return userCountByYearMap;
    }

    @Override
    public void reset() {
        //wipe data accumulated so far.
        userCountByYearMap.clear();
    }

    @Override
    public void init() {
        //don't need to do anything. just a convenience method for extensibility
    }

    @Override
    public Optional<Map<String, Long>> output() {
        return Optional.of(userCountByYearMap);
    }

    @Override
    public String getMessageString() {
        return "Users registered in each Year";
    }

}
