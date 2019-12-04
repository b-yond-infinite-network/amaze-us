package com.eureka.common.domain;

/**
 * Basic object indicating a range of objects to retrieve. Default is 10 objects (starting at zero).
 *
 * @author Costin Leau
 */
public class Range {

    private static final int SIZE = 9;
    public int begin = 0;
    public int end = SIZE;

    public Range() {
    }

    public Range(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    public Range(int pageNumber) {
        this.begin = 0;
        this.end = pageNumber * SIZE;
    }

    public int getPages() {
        return (int) Math.round(Math.ceil(end / SIZE));
    }
}