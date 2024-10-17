package com.rate_monitoring.rate_monitoring.services;

public class costConvertion {
    public static double toAed(double Usd){
        return Usd*3.6725;
    }
    public static double toUsd(double Aed){
        return Aed/3.6725;
    }
}
