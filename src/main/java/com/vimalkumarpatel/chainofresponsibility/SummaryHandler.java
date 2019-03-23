package com.vimalkumarpatel.chainofresponsibility;

import com.vimalkumarpatel.model.requests.SummaryRequest;
import com.vimalkumarpatel.model.requests.ValueWrapper;

/**
 * this handler is responsible for printing summaries
 */
public class SummaryHandler extends Handler<ValueWrapper> {

    @Override
    public void doHandling(ValueWrapper vw) {
        SummaryRequest sr = vw.getSummaryRequest();

        if(sr != null && sr.getOptionals() != null) {
            System.out.println("======= SUMMARY =======");
            sr.getOptionals()
                    .forEach(
                            (k,v) -> System.out.println(k + ":" + ((v.isPresent())?v.get():"NOT_FOUND"))
                    );
            System.out.println("=======================");
        }
        if(nextHandler != null) {
            nextHandler.doHandling(vw);
        }
    }
}
