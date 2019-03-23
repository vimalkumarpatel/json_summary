package com.vimalkumarpatel.queries;

import java.util.List;
import java.util.Optional;

/**
 * this Interface is the base of all the statistical queries of this applications.
 * @param <T>
 */
public interface Query<T> {
    /**
     * this method takes in a list of data objects to process the query in batch mode.
     * mean while keeps accumulating enough data to summarize all the data in the end.
     * @param data
     * @return the method returns Optional containing calculated value.
     */
    Optional process(List<T> data);
    void reset();
    void init();

    /**
     * this method will provide with the optional containing final calculation
     * after the entire json file has been read.
     *
     * @return the method returns Optional containing calculated value for the entire file.
     */
    Optional output();

    /**
     * returns message string to be used while printing summaries.
     * @return
     */
    String getMessageString();
}
