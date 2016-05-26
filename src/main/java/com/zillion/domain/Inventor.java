package com.zillion.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Konstantin on 4/24/16.
 *
 * for comparing single property, that is count - using Comparable interface.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Inventor implements Comparable<Inventor>{
    private String name;
    private int count;

    public Inventor(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int compareTo(Inventor compareInventor) {

        int compareCount = compareInventor.getCount();

        //descending order
        return compareCount - this.count;
    }
}
