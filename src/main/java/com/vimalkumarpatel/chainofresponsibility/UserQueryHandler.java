package com.vimalkumarpatel.chainofresponsibility;

import com.vimalkumarpatel.model.User;
import com.vimalkumarpatel.model.requests.SummaryRequest;
import com.vimalkumarpatel.model.requests.ValueWrapper;
import com.vimalkumarpatel.queries.Query;
import com.vimalkumarpatel.queries.impl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static com.vimalkumarpatel.util.Constants.BATCH_SIZE;

/**
 * this handler will handle User objects.
 * It encapsulates all the queries for the application.
 *
 */
public class UserQueryHandler extends Handler<ValueWrapper> {

    private static final Logger logger = LoggerFactory.getLogger(UserQueryHandler.class);
    private List<Query> userQueries;
    private List<User> users;

    public UserQueryHandler(){

        UsersRegisterdByYearQueryImpl usersRegisterdByYearQuery = new UsersRegisterdByYearQueryImpl();
        MedianFriendsQueryImpl medianFriendsQuery = new MedianFriendsQueryImpl();
        MedianUserAgeQueryImpl medianUserAgeQuery = new MedianUserAgeQueryImpl();
        MeanBalanceForUsersQueryImpl meanBalanceForUsersQuery = new MeanBalanceForUsersQueryImpl();
        MeanUnreadMessageActiveFemaleQueryImpl meanUnreadMessageQuery = new MeanUnreadMessageActiveFemaleQueryImpl();

        userQueries = new ArrayList<>();
        userQueries.add(usersRegisterdByYearQuery);
        userQueries.add(medianFriendsQuery);
        userQueries.add(medianUserAgeQuery);
        userQueries.add(meanBalanceForUsersQuery);
        userQueries.add(meanUnreadMessageQuery);

        users = new ArrayList<>();
    }
    @Override
    public void doHandling(ValueWrapper vw) {
        User user = vw.getUser();
        SummaryRequest sr = vw.getSummaryRequest();
        if(user != null){
            users.add(user);
            if(users.size() == BATCH_SIZE) {
                Map<String, Optional> optionals = processBatch(users);
                users.clear();
                logger.debug("User Batch processing complete, and users cleared.");
                if(sr == null) vw.setSummaryRequest(new SummaryRequest());
                vw.getSummaryRequest().setOptionals(optionals);
            }
        } else if(sr != null && sr.getOptionals() == null) {
            Map<String, Optional> smallBatchOptionals = processBatch(users);
            Map<String, Optional> optionals = new HashMap<>();
            userQueries
                    .stream()
                    .parallel()
                    .forEach(q -> {
                        optionals.put(q.getMessageString(), q.output());
                    });
            vw.getSummaryRequest().setOptionals(optionals);
        }

        if(nextHandler != null) {
            nextHandler.doHandling(vw);
        }
    }

    /**
     * this method processes the queries for a batch of users.
     * @param users
     * @return map of string summary messages for queries and their corresponding optionals.
     */
    private Map<String,Optional> processBatch(List<User> users) {
        logger.debug("Starting batch process for Batch Size={}", users.size());
        Map<String, Optional> optionals = new HashMap<>();
        userQueries
                .stream()
                .parallel()
                .forEach(q -> {
                    optionals.put(q.getMessageString(), q.process(users));
                });
        return optionals;
    }
}
