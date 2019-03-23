package com.vimalkumarpatel.chainofresponsibility;

/**
 * A chain of responsibility implementation
 * @param <T>
 */
public abstract class Handler<T> {
    Handler<T> nextHandler;

    public void setNextHandler(Handler<T> nextHandler) {
        this.nextHandler = nextHandler;
    }

    public void doHandling(T t) {
        if(nextHandler != null) {
            nextHandler.doHandling(t);
        }
    }
}
