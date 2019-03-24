package com.vimalkumarpatel.queries.impl;

import com.vimalkumarpatel.model.User;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.ToDoubleFunction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MeanBalanceForUsersQueryImplTest {

    private MeanBalanceForUsersQueryImpl subject;
    private User user;
    private List<User> users;

    @Before
    public void setUp() throws Exception {
        subject = new MeanBalanceForUsersQueryImpl();
        user = new User();
        user.setBalance("$1,000.00");

        users = new ArrayList<>();
    }

    @Test
    public void output_allusers() throws Exception {
        users.clear();
        users.add(user);
        Optional mean = subject.process(users);
        Assert.assertTrue(mean.isPresent());
        assertEquals(1000.0D, mean.get());

        users.clear();
        users.add(user);
        users.add(new User());
        Optional mean2 = subject.process(users);
        Assert.assertTrue(mean2.isPresent());
        assertEquals(500.0D, mean2.get());

        Optional<Double> totalMean = subject.output();
        Assert.assertTrue(totalMean.isPresent());
        assertEquals(666.6666D, totalMean.get().doubleValue(), 0.0001);
    }

    @Test
    public void output_reset() throws Exception {
        users.clear();
        users.add(user);
        Optional mean = subject.process(users);
        Assert.assertTrue(mean.isPresent());
        assertEquals(1000.0D, mean.get());

        users.clear();
        users.add(user);
        users.add(new User());
        Optional mean2 = subject.process(users);
        Assert.assertTrue(mean2.isPresent());
        assertEquals(500.0D, mean2.get());

        subject.reset();

        Optional<Double> totalMean = subject.output();
        Assert.assertTrue(!totalMean.isPresent());
    }

    @Test
    public void output_empty() throws Exception {
        users.clear();
        Optional<Double> totalMean = subject.output();
        Assert.assertTrue(!totalMean.isPresent());
    }


    @Test
    public void getMean_2User() throws Exception {
        users.clear();
        users.add(user);
        users.add(new User());
        Optional mean = subject.process(users);
        Assert.assertTrue(mean.isPresent());
        assertEquals(500.0D, mean.get());
    }

    @Test
    public void getMean_1User() throws Exception {
        users.clear();
        users.add(user);
        Optional mean = subject.process(users);
        Assert.assertTrue(mean.isPresent());
        assertEquals(1000.0D, mean.get());
    }

    @Test
    public void constructorInitTest() throws Exception {
        ToDoubleFunction func = (ToDoubleFunction) FieldUtils.readField(subject, "toDoubleFunction", true);
        assertNotNull(func);


        assertEquals(1000.00, func.applyAsDouble(user), 0.001);

        user.setBalance("-$1000.00");
        assertEquals(-1000.00, func.applyAsDouble(user), 0.001);

        user.setBalance("$abc.def");
        assertEquals(0.00, func.applyAsDouble(user), 0.001);
    }

    @Test
    public void process() throws Exception {
    }

    @Test
    public void getMessageString() throws Exception {
        String msg = subject.getMessageString();
        assertNotNull(msg);
    }

}