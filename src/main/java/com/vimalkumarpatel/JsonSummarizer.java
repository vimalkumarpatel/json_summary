package com.vimalkumarpatel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.vimalkumarpatel.chainofresponsibility.Handler;
import com.vimalkumarpatel.chainofresponsibility.SummaryHandler;
import com.vimalkumarpatel.chainofresponsibility.UserQueryHandler;
import com.vimalkumarpatel.model.User;
import com.vimalkumarpatel.model.requests.SummaryRequest;
import com.vimalkumarpatel.model.requests.ValueWrapper;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import static java.lang.System.exit;

public class JsonSummarizer {

    private static final Logger logger = LoggerFactory.getLogger(JsonSummarizer.class);
    private Handler chainOfResponsibilityHandler;

    public static void main(String[] args) {
        logger.info("executing json summarizer with arguments : {}", Arrays.toString(args));
        if(args == null || args.length == 0){
            //print usage/error and exit
            System.err.println("Expected complete path to at least one JSON data file in arguments, but found none.");
            exit(1);
        }

        //instantiate and bootstrap the application
        JsonSummarizer jsonSummarizer = new JsonSummarizer();
        jsonSummarizer.bootstrapApp();

        //we may have multiple file. lets try to parse and summarize each one
        for(String filePath : args) {
            jsonSummarizer.parseAndSummarize(filePath);
        }

        logger.info("executing json summarizer complete.");
    }

    public void bootstrapApp() {
        this.chainOfResponsibilityHandler = initializeChainOfResponsibility();

    }

    /**
     * this method initializes the chain of responsibility.
     * @return
     */
    private static Handler initializeChainOfResponsibility() {
        UserQueryHandler userQueryHandler = new UserQueryHandler();
        SummaryHandler summaryHandler = new SummaryHandler();

        userQueryHandler.setNextHandler(summaryHandler);
        return userQueryHandler;
    }

    public void parseAndSummarize(String filePath) {
        try {
            final InputStream stream = FileUtils.openInputStream(FileUtils.getFile(filePath));
            JsonReader reader = new JsonReader(new InputStreamReader(stream));
            Gson gson = new GsonBuilder().create();

            // Reading file in stream mode
            reader.beginArray();
            while (reader.hasNext()) {
                User user = gson.fromJson(reader, User.class);
                logger.trace("Parsed user = {}", user);
                ValueWrapper vw = new ValueWrapper(); vw.setUser(user);
                chainOfResponsibilityHandler.doHandling(vw);
            }
            reader.close();
            ValueWrapper vw = new ValueWrapper(); vw.setSummaryRequest(new SummaryRequest());
            chainOfResponsibilityHandler.doHandling(vw);
        } catch (IOException ex) {
            logger.error("IOException during parsing Json file {}. IOException: {}", filePath, ex);
        }
    }
}
