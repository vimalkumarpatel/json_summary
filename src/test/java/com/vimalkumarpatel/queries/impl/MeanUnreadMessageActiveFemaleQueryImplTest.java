package com.vimalkumarpatel.queries.impl;

import com.vimalkumarpatel.model.Gender;
import com.vimalkumarpatel.model.User;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.function.Predicate;
import java.util.function.ToIntFunction;

import static org.junit.Assert.*;

public class MeanUnreadMessageActiveFemaleQueryImplTest {
    private MeanUnreadMessageActiveFemaleQueryImpl subject;

    @Before
    public void setUp() throws Exception {
        subject = new MeanUnreadMessageActiveFemaleQueryImpl();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void constructorInit() throws Exception {

        Predicate<User> userPredicate = (Predicate<User>) FieldUtils.readField(subject, "userPredicate", true);
        ToIntFunction<User> toIntFunction = (ToIntFunction<User>) FieldUtils.readField(subject, "toIntFunction", true);

        assertNotNull(userPredicate);
        assertNotNull(toIntFunction);

    }

    @Test
    public void constructorInit_lambda_ToIntFunction() throws Exception {
        ToIntFunction<User> toIntFunction = (ToIntFunction<User>) FieldUtils.readField(subject, "toIntFunction", true);
        assertNotNull(toIntFunction);

        User u = new User();


        u.setGreeting("      ");
        assertEquals(0, toIntFunction.applyAsInt(u));

        u.setGreeting("");
        assertEquals(0, toIntFunction.applyAsInt(u));

        u.setGreeting(null);
        assertEquals(0, toIntFunction.applyAsInt(u));

        u.setGreeting("lorem ipsum lorem");
        assertEquals(0, toIntFunction.applyAsInt(u));

        u.setGreeting("Hello, dummy user! You have 82 unread messages.");
        assertEquals(82, toIntFunction.applyAsInt(u));

        u.setGreeting("Hello, dummy user! You have 99.66 unread messages.");
        assertEquals(0, toIntFunction.applyAsInt(u));

    }


    @Test
    public void constructorInit_lambda_userPredicate() throws Exception {
        Predicate<User> userPredicate = (Predicate<User>) FieldUtils.readField(subject, "userPredicate", true);
        assertNotNull(userPredicate);

        User u = new User(); u.setGender(Gender.female); u.setActive(true);
        assertTrue(userPredicate.test(u));

        User u2 = new User(); u2.setGender(Gender.male); u2.setActive(true);
        assertFalse(userPredicate.test(u2));

        User u3 = new User(); u3.setGender(Gender.female); u3.setActive(false);
        assertFalse(userPredicate.test(u3));

        User u4 = new User(); u4.setGender(Gender.male); u4.setActive(false);
        assertFalse(userPredicate.test(u4));

    }

}