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
    public void output_null_data() throws Exception {
        User u = new User();
        users.add(u);
        Optional optional = subject.process(users);
        assertTrue(optional.isPresent());
        assertEquals(0.0D, optional.get());

        optional = subject.output();
        assertTrue(optional.isPresent());
        assertEquals(0.0D, optional.get());
    }


    @Test
    public void output_1Friend_reset() throws Exception {
        User u = new User(); u.setFriends(new Friend[1]); u.getFriends()[0]=new Friend();
        users.add(u);
        Optional optional = subject.process(users);
        assertTrue(optional.isPresent());
        assertEquals(1.0, optional.get());

        subject.reset();

        Optional optional1 = subject.output();
        assertTrue(!optional1.isPresent());
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

    @Test //1,11,30,40,50
    public void output_mixed_Friend() throws Exception {
        users.clear();
        int [] friends = {11,1,50,40,30};
        for(int i=0;i<5;i++){
            User u = new User(); u.setFriends(new Friend[friends[i]]);
            users.add(u);
        }

        Optional optional = subject.process(users);
        assertTrue(optional.isPresent());
        assertEquals(30.0D, optional.get());

    }

    @Test
    public void output_1To5_Friend() throws Exception {
        users.clear();
        for(int i=1;i<=5;i++){
            User u = new User(); u.setFriends(new Friend[i]);
            users.add(u);
        }

        Optional optional = subject.process(users);
        assertTrue(optional.isPresent());
        assertEquals(3.0D, optional.get());

        Optional optional1 = subject.output();
        assertTrue(optional1.isPresent());
        assertEquals(3.0D, optional1.get());
    }

    @Test
    public void output_11To20_Friend() throws Exception {

        users.clear();
        for(int i=11;i<=20;i++){
            User u = new User(); u.setFriends(new Friend[i]);
            users.add(u);
        }

        Optional optional = subject.process(users);
        assertTrue(optional.isPresent());
        assertEquals(15.5D, optional.get());

    }

    @Test
    public void output_1To5_11To20_Friend() throws Exception {

        for(int i=1;i<=5;i++){
            User u = new User(); u.setFriends(new Friend[i]);
            users.add(u);
        }

        Optional optional = subject.process(users);
        assertTrue(optional.isPresent());
        assertEquals(3.0D, optional.get());

        users.clear();
        for(int i=11;i<=20;i++){
            User u = new User(); u.setFriends(new Friend[i]);
            users.add(u);
        }

        optional = subject.process(users);
        assertTrue(optional.isPresent());
        assertEquals(15.5D, optional.get());

        Optional optional1 = subject.output();
        assertTrue(optional1.isPresent());
        assertEquals(13.0D, optional1.get());
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