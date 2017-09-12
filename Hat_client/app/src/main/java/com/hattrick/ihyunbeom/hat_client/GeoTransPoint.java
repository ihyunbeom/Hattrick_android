package com.hattrick.ihyunbeom.hat_client;

/**
 * Created by ihyunbeom on 2017-09-13.
 */
public class GeoTransPoint {
    double x;
    double y;
    double z;
    /**
     *
     */
    public GeoTransPoint() {
        super();
    }
    /**
     * @param x
     * @param y
     */
    public GeoTransPoint(double x, double y) {
        super();
        this.x = x;
        this.y = y;
        this.z = 0;
    }
    /**
     * @param x
     * @param y
     * @param y
     */
    public GeoTransPoint(double x, double y, double z) {
        super();
        this.x = x;
        this.y = y;
        this.z = 0;
    }
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}