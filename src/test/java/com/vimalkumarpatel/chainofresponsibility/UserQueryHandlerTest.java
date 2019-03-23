package com.vimalkumarpatel.chainofresponsibility;

import com.vimalkumarpatel.model.User;
import com.vimalkumarpatel.model.requests.SummaryRequest;
import com.vimalkumarpatel.model.requests.ValueWrapper;
import com.vimalkumarpatel.queries.Query;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import sun.reflect.Reflection;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.spy;

public class UserQueryHandlerTest {

    private UserQueryHandler subject;
    private ValueWrapper vw;
    private User user;
    private SummaryRequest sr;
    private Map<String, Optional> optionals;
    @Before
    public void setUp() throws Exception {
        subject = new UserQueryHandler();
        vw = new ValueWrapper();
        user = new User();
        sr = new SummaryRequest();
        optionals = new HashMap<>();
    }

    @Test
    public void doHandling() throws Exception {
        List<Query> userQueriesField = (List<Query>) FieldUtils.readField(subject, "userQueries", true);
        Assert.assertNotNull(userQueriesField);
        Assert.assertEquals(5, userQueriesField.size());

        ValueWrapper spyVW = spy(vw);
        subject.doHandling(vw);
        Mockito.verify(spyVW, Mockito.atMost(1)).getUser();
        Mockito.verify(spyVW, Mockito.atMost(1)).getSummaryRequest();
    }

    @Test
    public void doHandling_NonNullUser() throws Exception {
        vw.setUser(user);
        subject.doHandling(vw);

        List<User> usersField = (List<User>) FieldUtils.readField(subject, "users", true);

        Assert.assertNotNull(usersField);
        Assert.assertEquals(1, usersField.size());
        Assert.assertEquals(user, usersField.get(0));
    }

//    @Test
    public void doHandling_NonNullSummaryRequest() throws Exception {
        vw.setUser(null);
        SummaryRequest spySR = spy(sr);
        vw.setSummaryRequest(spySR);
        List<Query> userQueriesField = (List<Query>) FieldUtils.readField(subject, "userQueries", true);

        subject.doHandling(vw);

        Assert.assertNotNull(spySR.getOptionals());
        Assert.assertEquals(0, spySR.getOptionals().size());
    }
}