package com.vimalkumarpatel.queries.impl;

import com.vimalkumarpatel.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.function.ToDoubleFunction;

import static org.junit.Assert.*;

public class MeanBalanceForUsersQueryImplTest {

    private MeanBalanceForUsersQueryImpl subject;
    private User user;

    @Before
    public void setUp() throws Exception {
        subject = new MeanBalanceForUsersQueryImpl();
        user = new User();
        user.setBalance("$1,000.00");

    }

    @Test
    public void constructorInitTest() throws Exception {
        assertEquals(0.0, subject.totalSum, 0.001);
        ToDoubleFunction func = (ToDoubleFunction) FieldUtils.readField(subject, "toDoubleFunction", true);
        assertNotNull(func);
        assertEquals(0, subject.totalCount);


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