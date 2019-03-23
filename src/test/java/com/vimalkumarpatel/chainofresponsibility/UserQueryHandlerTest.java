package com.vimalkumarpatel.chainofresponsibility;

import com.vimalkumarpatel.model.User;
import com.vimalkumarpatel.model.requests.SummaryRequest;
import com.vimalkumarpatel.model.requests.ValueWrapper;
import com.vimalkumarpatel.queries.Query;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

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

    @After
    public void tearDown() throws Exception {
        List<User> usersField = (List<User>) FieldUtils.readField(subject, "users", true);
        usersField.clear();
    }

    @Test
    public void doHandling() throws Exception {
        List<Query> userQueriesField = (List<Query>) FieldUtils.readField(subject, "userQueries", true);
        assertNotNull(userQueriesField);
        assertEquals(5, userQueriesField.size());

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

        assertNotNull(usersField);
        assertEquals(1, usersField.size());
        assertEquals(user, usersField.get(0));
    }

    @Test
    public void doHandling_NonNullUser_1000() throws Exception {
        List<User> usersField = (List<User>) FieldUtils.readField(subject, "users", true);
        usersField.clear();
        vw.setUser(user);
        subject.doHandling(vw);
        assertNotNull(usersField);

        for(int i=1;i<999;i++) usersField.add(user);

        Handler spyHandler = spy(new SummaryHandler());
        subject.setNextHandler(spyHandler);

        assertNull(vw.getSummaryRequest());

        subject.doHandling(vw);

        assertEquals(0, usersField.size());
        assertNotNull(vw.getSummaryRequest());
        assertNotNull(vw.getSummaryRequest().getOptionals());
        assertEquals(5, vw.getSummaryRequest().getOptionals().size());
    }

    @Test
    public void doHandling_NonNullSummaryRequest() throws Exception {
        vw.setUser(null);
        SummaryRequest spySR = spy(sr);
        vw.setSummaryRequest(spySR);
        List<Query> userQueriesField = (List<Query>) FieldUtils.readField(subject, "userQueries", true);
        userQueriesField.clear();
        userQueriesField = spy(userQueriesField);
        FieldUtils.writeField(subject, "userQueries", userQueriesField, true);

        subject.doHandling(vw);

        verify(userQueriesField, times(2)).stream();
        assertNotNull(spySR.getOptionals());
        assertEquals(0, spySR.getOptionals().size());
    }

    @Test
    public void doHandling_NonNullNextHandler() throws Exception {
        Handler spyHandler = spy(new SummaryHandler());
        subject.setNextHandler(spyHandler);
        subject.doHandling(vw);
        verify(spyHandler, times(1)).doHandling(eq(vw));
    }


}