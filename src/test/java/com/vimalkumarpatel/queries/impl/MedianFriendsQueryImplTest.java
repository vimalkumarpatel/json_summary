package com.vimalkumarpatel.queries.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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

    @Before
    public void setUp() throws Exception {
        subject = new MedianFriendsQueryImpl();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void process() throws Exception {
    }

}