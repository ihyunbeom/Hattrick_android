package org.hat.hattrick;

/**
 * Created by ihyunbeom on 2017-06-01.
 */

public class Notice {

    String time;
    String name;

    public Notice(String name, String time) {
        this.time = time;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
