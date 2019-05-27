package com.company;

import javafx.util.Pair;

public class City implements Pointable {
    private double x;
    private double y;
    public int number;
    public int nearestNodeIndex;
    public double distanceToNearestNode;
    int countOfUses = 0;



    City(double x,double y){
        this.x = x;
        this.y = y;
        distanceToNearestNode = 10000;
    }

    public void setDistanceToNearestNode(double distanceToNearestNode) {
        this.distanceToNearestNode = distanceToNearestNode;
    }

    @Override
    public double getX() {
        return x;
    }


    @Override
    public double getY() {
        return y;
    }

    @Override
    public Pair<Double, Double> getPointMarker() {
        return new Pair<>(x, y);
    }

    @Override
    public void setX(double x) {
        this.x = x;
    }

    @Override
    public void setY(double y) {
        this.y = y;
    }

    @Override
    public void setPointMarker(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
