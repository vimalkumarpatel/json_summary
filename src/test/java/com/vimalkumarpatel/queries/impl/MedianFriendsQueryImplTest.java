package com.vimalkumarpatel.queries.impl;

import com.vimalkumarpatel.model.Friend;
import com.vimalkumarpatel.model.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MedianFriendsQueryImplTest {

    String user_json = "{\n" +
            "    \"guid\": \"893663ef-1911-4318-a477-626774fc05b6\",\n" +
            "    \"isActive\": true,\n" +
            "    \"balance\": \"$4,611.22\",\n" +
            "    \"age\": 44,\n" +
            "    \"eyeColor\": \"blue\",\n" +
            "    \"name\": \"Esperanza Wilkerson\",\n" +
            "    \"gender\": \"female\",\n" +
            "    \"email\": \"esperanzawilkerson@avit.com\",\n" +
            "    \"phone\": \"+1 (920) 582-3308\",\n" +
            "    \"address\": \"217 Mill Lane, Fivepointville, North Carolina, 8708\",\n" +
            "    \"registered\": \"2017-11-14T04:11:28 +08:00\",\n" +
            "    \"friends\": [\n" +
            "      {\n" +
            "        \"name\": \"Chan Patel\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"greeting\": \"Hello, Esperanza Wilkerson! You have 82 unread messages.\",\n" +
            "    \"favoriteFruit\": \"blueberry\"\n" +
            "  }";

    private MedianFriendsQueryImpl subject;
    List<User> users;
    @Before
    public void setUp() throws Exception {
        subject = new MedianFriendsQueryImpl();
        users = new ArrayList<>();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void process_1User_0Friends() throws Exception {
        User u = new User(); u.setFriends(new Friend[0]);
        users.add(u);
        Optional optional = subject.process(users);
        assertTrue(optional.isPresent());
        assertEquals(0.0, optional.get());
    }

    @Test
    public void process_1User_1Friends() throws Exception {
        User u = new User(); u.setFriends(new Friend[1]); u.getFriends()[0]=new Friend();
        users.add(u);
        Optional optional = subject.process(users);
        assertTrue(optional.isPresent());
        assertEquals(1.0, optional.get());
    }

    @Test
    public void process_2User_1Friend() throws Exception {
        User u = new User(); u.setFriends(new Friend[1]); u.getFriends()[0]=new Friend();
        users.add(u);
        User u2 = new User(); u2.setFriends(new Friend[0]);
        users.add(u2);
        Optional optional = subject.process(users);
        assertTrue(optional.isPresent());
        assertEquals(0.5, optional.get());
    }

    @Test
    public void process_emptyUsers() throws Exception {
        Optional optional = subject.process(users);
        System.out.println("optional for empty users:"+ optional);
        assertTrue(!optional.isPresent());

    }

    @Test
    public void output_1Friend() throws Exception {
        User u = new User(); u.setFriends(new Friend[1]); u.getFriends()[0]=new Friend();
        users.add(u);
        Optional optional = subject.process(users);
        assertTrue(optional.isPresent());
        assertEquals(1.0, optional.get());

        Optional optional1 = subject.output();
        assertTrue(optional1.isPresent());
        assertEquals(1.0, optional1.get());
    }

    @Test
    public void output_2Friend() throws Exception {
        User u = new User(); u.setFriends(new Friend[2]); u.getFriends()[0]=new Friend(); u.getFriends()[1]=new Friend();
        users.add(u);
        Optional optional = subject.process(users);
        assertTrue(optional.isPresent());
        assertEquals(2.0, optional.get());

        optional = subject.process(users);
        assertTrue(optional.isPresent());
        assertEquals(2.0, optional.get());

        Optional optional1 = subject.output();
        assertTrue(optional1.isPresent());
        assertEquals(2.0, optional1.get());
    }

    @Test
    public void output_empty() throws Exception {
        Optional optional = subject.output();
        System.out.println("optional for empty users:"+ optional);
        assertTrue(!optional.isPresent());
    }

    @Test
    public void getMessage() throws Exception {
        String msg = subject.getMessageString();
        Assert.assertNotNull(msg);
        Assert.assertEquals("Median for Number of Friends", msg);
    }

}