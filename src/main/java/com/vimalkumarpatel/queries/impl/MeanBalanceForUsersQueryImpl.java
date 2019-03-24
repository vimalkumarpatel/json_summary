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
                    return Double.valueOf(balStr).doubleValue();
                } catch (NumberFormatException ex){
                    return 0D;
                }
            }
        };

    }
    @Override
    public Optional<Double> process(List<User> data) {
        logger.debug("Processing Mean balances for batch");
        double [] userBalance = data
                .stream()
                .mapToDouble(toDoubleFunction)
                .toArray();
        OptionalDouble userBalanceAvg = getMean(userBalance);
        Optional<Double> retValue = Optional.of(userBalanceAvg.isPresent() ? userBalanceAvg.getAsDouble() : new Double(0));
        logger.debug("Mean for current batch found ?={}, retValue={}", userBalanceAvg.isPresent(), retValue.get());

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
