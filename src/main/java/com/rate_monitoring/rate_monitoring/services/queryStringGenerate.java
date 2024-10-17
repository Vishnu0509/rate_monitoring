package com.rate_monitoring.rate_monitoring.services;

import java.util.List;

import com.rate_monitoring.rate_monitoring.dao.booking_hotel_detail;
import com.rate_monitoring.rate_monitoring.dao.booking_hotel_detail_API;

public class queryStringGenerate {
    public static String getString(booking_hotel_detail booking_hotel_details, booking_hotel_detail_API booking_hotel_details_API, List<Object> arr) {
        String quer = "insert into rate_monitoring values('%s','%s','%s','%s','%s',%f, %f, %f, %f, %f, %f, %f, %f,'%s')";
        String _1= booking_hotel_details.getSource();
        String _2= booking_hotel_details_API.getRequestid();
        String _3= booking_hotel_details.getPartyname();
        String _4= booking_hotel_details.getCheckin();
        String _5= booking_hotel_details.getCheckout();
        double _6= booking_hotel_details.getCostvalue();
        double _7= booking_hotel_details.getCostvalue()*3.6725;
        double _8= booking_hotel_details.getMarkupInAed()/3.6725;
        double _9= booking_hotel_details.getActual_markup();
        double _10= booking_hotel_details.getSalevalue();
        double _11= booking_hotel_details.getCost_as_per_response();
        double _12= booking_hotel_details.getCost_as_per_response()+booking_hotel_details.getActual_markup();
        double _13= booking_hotel_details.getDifference_sale();
        String _14= booking_hotel_details.getResult();
        arr.add(_1);arr.add(_2);arr.add(_3);arr.add(_4);arr.add(_5);arr.add(_6);arr.add(_7);arr.add(_8);arr.add(_9);arr.add(_10);arr.add(_11);arr.add(_12);arr.add(_13);arr.add(_14);
        return String.format(quer,_1,_2,_3,_4,_5,_6,_7,_8,_9,_10,_11,_12,_13,_14);
    }
}
