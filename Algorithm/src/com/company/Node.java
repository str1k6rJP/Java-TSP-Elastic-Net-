package com.company;

import javafx.util.Pair;

public class Node implements Pointable {
    private double x;
    private double y;
    private double[] weights;

    public Node(double x,double y,int citiesQuantity) {
        this.x = x;
        this.y = y;
        weights = new double[citiesQuantity];
    }

    public Node(double x,double y) {
        this.x = x;
        this.y = y;
    }

        public void setWeightsQuantity(int quantity){
        weights = new double[quantity];
    }

    public void setWeightOnIndex(double weight, int index) throws IndexOutOfBoundsException,NullPointerException{
        if ((double)index<weights.length) {
            weights[index] = weight;
        }
    }

    public double getWeightOnIndex(int index){
        if (index<weights.length) {
            return weights[index];
        }
        else return Double.MIN_VALUE;
    }

    public double[] getWeights(){
        return weights;
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
