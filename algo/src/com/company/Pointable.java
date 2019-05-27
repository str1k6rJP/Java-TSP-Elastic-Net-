package com.company;

import javafx.util.Pair;

public interface Pointable {
    void setX (double x);
    void setY (double y);
    void setPointMarker(double x,double y);
    double getX();
    double getY();
    Pair<Double,Double> getPointMarker();



}
