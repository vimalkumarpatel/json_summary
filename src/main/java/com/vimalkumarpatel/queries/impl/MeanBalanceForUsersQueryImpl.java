package com.vimalkumarpatel.queries.impl;

import com.vimalkumarpatel.model.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.ToDoubleFunction;

/**
 * this query will calculate average balance for users batch wise and for entire json.
 */
public class MeanBalanceForUsersQueryImpl extends MeanQuery<User> {

    private static final Logger logger = LoggerFactory.getLogger(MeanBalanceForUsersQueryImpl.class);
    private ToDoubleFunction<User> toDoubleFunction;

    public MeanBalanceForUsersQueryImpl() {
        this.toDoubleFunction = new ToDoubleFunction<User>() {
            @Override
            public double applyAsDouble(User user) {
                String balStr = StringUtils.trimToEmpty(user.getBalance())
                        .replaceAll("[$]","")
                        .replaceAll(",","");
                try{
                    return Double.valueOf(balStr);
                } catch (NumberFormatException ex){
                    return Double.valueOf(0);
                }
            }
        };

        this.totalCount = 0;
        this.totalSum = 0;
    }
    @Override
    public Optional process(List<User> data) {
        logger.debug("Processing Mean balances for batch");
        OptionalDouble userBalanceAvg = data
                .stream()
                .mapToDouble(toDoubleFunction)
                .average();
        Optional<Double> retValue = Optional.of(userBalanceAvg.isPresent() ? userBalanceAvg.getAsDouble() : new Double(0));
        logger.debug("Mean for current batch found ?={}, retValue={}", userBalanceAvg.isPresent(), retValue.get());

        totalCount = totalCount + data.size();
        totalSum = totalSum + (data.size() * retValue.get());
        logger.debug("updated - total Users={}, total Balance={}", totalCount, totalSum);

        return retValue;
    }

    @Override
    public void init() {

    }

    @Override
    public String getMessageString() {
        return "Mean Balance Amount";
    }
}
