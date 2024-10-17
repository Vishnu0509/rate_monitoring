package com.rate_monitoring.rate_monitoring.services;

public class dateChecker {
    public boolean dateChecking(String curDate, String startDate, String endDate){
        int year1=Integer.parseInt(curDate.substring(0,4));
        int month1=Integer.parseInt(curDate.substring(5,7));
        int date1=Integer.parseInt(curDate.substring(8,10));
        int year2=Integer.parseInt(startDate.substring(0,4));
        int month2=Integer.parseInt(startDate.substring(5,7));
        int date2=Integer.parseInt(startDate.substring(8,10));
        int year3=Integer.parseInt(endDate.substring(0,4));
        int month3=Integer.parseInt(endDate.substring(5,7));
        int date3=Integer.parseInt(endDate.substring(8,10));
        return year1 >= year2 && year1 <= year3 && month1 >= month2 && month1 <= month3 && date1 >= date2 && date1 <= date3;
    }

}
