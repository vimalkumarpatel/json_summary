package com.vimalkumarpatel.queries.impl;

import com.vimalkumarpatel.model.User;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UsersRegisterdByYearQueryImplTest {

    private UsersRegisterdByYearQueryImpl subject;
    User user;
    @Before
    public void setUp() throws Exception {
        subject = new UsersRegisterdByYearQueryImpl();
        user = new User();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void constructorInit_lambda_classifierFunction() throws Exception {

        Map<String, Long> userCountByYearMap = (Map<String, Long>)FieldUtils.readField(subject, "userCountByYearMap", true);
        assertEquals(0, userCountByYearMap.size());

        Function<User, String> classifierFunction = (Function<User, String>) FieldUtils.readField(subject, "classifierFunction", true);

        user.setRegistered("     ");
        assertEquals("UNKNOWN_YEAR", classifierFunction.apply(user));

        user.setRegistered(null);
        assertEquals("UNKNOWN_YEAR", classifierFunction.apply(user));

        user.setRegistered("");
        assertEquals("UNKNOWN_YEAR", classifierFunction.apply(user));

        user.setRegistered("----");
        assertEquals("UNKNOWN_YEAR", classifierFunction.apply(user));

        user.setRegistered("2017-11-14T04:11:28 +08:00");
        assertEquals("2017", classifierFunction.apply(user));
    }


    @Test
    public void process() throws Exception {
        user.setRegistered("2017-11-14T04:11:28 +08:00");
        List<User> users = new ArrayList<>(); users.add(user);
        Optional optional = subject.process(users);
        assertTrue(optional.isPresent());
        Map<String, Long> map = (Map<String, Long>) optional.get();
        assertEquals(1,map.size());
        assertEquals(1,map.get("2017").intValue());
    }

    @Test
    public void process_2_reset() throws Exception {
        user.setRegistered("2017-11-14T04:11:28 +08:00");
        List<User> users = new ArrayList<>(); users.add(user);

        Optional optional = subject.process(users);
        Optional optional2 = subject.process(users);

        assertTrue(optional2.isPresent());
        Map<String, Long> map = (Map<String, Long>) optional2.get();
        assertEquals(1,map.size());
        assertEquals(1,map.get("2017").intValue());

        Map<String, Long> totalMap = (Map<String, Long>) FieldUtils.readField(subject, "userCountByYearMap", true);
        assertEquals(1,totalMap.size());
        assertEquals(2,totalMap.get("2017").intValue());

        subject.reset();

        assertEquals(0,totalMap.size());
    }

    @Test
    public void output() throws Exception {
        Optional optional = subject.output();
        assertTrue(optional.isPresent());
        assertEquals(0, ((Map<String, Long>)optional.get()).size());
    }

}