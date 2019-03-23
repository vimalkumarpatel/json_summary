package com.vimalkumarpatel.chainofresponsibility;

import com.vimalkumarpatel.model.requests.SummaryRequest;
import com.vimalkumarpatel.model.requests.ValueWrapper;

public class SummaryHandler extends Handler<ValueWrapper> {

    @Override
    public void doHandling(ValueWrapper vw) {
        SummaryRequest sr = vw.getSummaryRequest();

        if(sr != null && sr.getOptionals() != null) {
            sr.getOptionals()
                    .forEach(
                            (k,v) -> System.out.println(k + ":" + ((v.isPresent())?v.get():"NOT_FOUND"))
                    );
        }
        if(nextHandler != null) {
            nextHandler.doHandling(vw);
        }
    }
}
