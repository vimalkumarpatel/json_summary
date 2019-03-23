package com.vimalkumarpatel.chainofresponsibility;

import com.vimalkumarpatel.model.Friend;
import com.vimalkumarpatel.model.requests.SummaryRequest;
import com.vimalkumarpatel.model.requests.ValueWrapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;

public class SummaryHandlerTest {

    private SummaryHandler subject;
    private ValueWrapper vw;

    @Before
    public void setUp() throws Exception {
        subject = new SummaryHandler();
        vw = new ValueWrapper();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void doHandling_NonNullNextHandler() throws Exception {
        Handler spyhandler = spy(new SummaryHandler());
        subject.setNextHandler(spyhandler);

        subject.doHandling(vw);

        Mockito.verify(spyhandler , Mockito.atMost(1)).doHandling(Mockito.eq(vw));

    }

    @Test
    public void doHandling_NonEmptyOptional() throws Exception {

        Friend f = new Friend(); f.setName("DUMMY_FRIEND");
        Friend spyF = spy(f);
        Map<String, Optional> optionals = new HashMap<>(); optionals.put("DUMMY_KEY", Optional.of(spyF));
        Map<String, Optional> spyOptionals = Mockito.spy(optionals);
        vw.setSummaryRequest(new SummaryRequest());
        vw.getSummaryRequest().setOptionals(spyOptionals);

        subject.doHandling(vw);

        Mockito.verify(spyF , Mockito.atMost(1)).getName();
    }
    @Test
    public void doHandling_EmptyOptional() throws Exception {

        Map<String, Optional> optionals = new HashMap<>(); optionals.put("DUMMY_KEY", Optional.empty());
        Map<String, Optional> spyOptionals = Mockito.spy(optionals);
        vw.setSummaryRequest(new SummaryRequest());
        vw.getSummaryRequest().setOptionals(spyOptionals);

        subject.doHandling(vw);

        Mockito.verify(spyOptionals, never()).get("DUMMY_KEY");
    }
}