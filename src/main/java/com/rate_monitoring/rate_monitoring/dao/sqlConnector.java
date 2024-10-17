package com.rate_monitoring.rate_monitoring.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Objects;

import static com.rate_monitoring.rate_monitoring.services.costConvertion.toUsd;

public class sqlConnector {
    JdbcTemplate jdbcTemplate;
    public List<booking_hotel_detail_API> booking_hotel_details_API;
    public List<booking_hotel_detail> booking_hotel_details;
    public List<booking_guest> booking_guests;
    String sql1 = "select requestid,apichannelid,rlineno,confirmId,ClientReferenceNumber,sourceId from NewColumbus.dbo.booking_hotel_detail_API where isnull(confirmid,'')<>'' and requestid not in (select bookId from rate_monitoring)";
    String sql2 = "select NewColumbus.dbo.booking_hotel_detail.MarkupInAed,NewColumbus.dbo.booking_hotel_detail.checkin, NewColumbus.dbo.booking_hotel_detail.checkout, NewColumbus.dbo.booking_hotel_detail.costvalue, NewColumbus.dbo.booking_hotel_detail.Int_costprice, NewColumbus.dbo.booking_hotel_detail.salevalue, NewColumbus.dbo.booking_hotel_detail.costcurrcode, NewColumbus.dbo.booking_hotel_detail.Int_costcurrcode, NewColumbus.dbo.booking_hotel_detail.salecurrcode, NewColumbus.dbo.booking_hotel_detail.Int_partycode, NewColumbus.dbo.booking_hotel_detail.roomDisplayString, NewColumbus.dbo.partymast.partyname from  NewColumbus.dbo.booking_hotel_detail, NewColumbus.dbo.partymast where  NewColumbus.dbo.booking_hotel_detail.requestid='%s' and NewColumbus.dbo.booking_hotel_detail.rlineno='%d' and NewColumbus.dbo.partymast.partycode=NewColumbus.dbo.booking_hotel_detail.partycode;";
    String sql3 = "select lastname from NewColumbus.dbo.booking_guest where requestid='%s' and rlineno='%s'";

    public sqlConnector(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
        this.booking_hotel_details_API = jdbcTemplate.query(sql1, BeanPropertyRowMapper.newInstance(booking_hotel_detail_API.class));
    }
    public void fetchData(int i){
        booking_hotel_details = jdbcTemplate.query(String.format(sql2,booking_hotel_details_API.get(i).getRequestid(),booking_hotel_details_API.get(i).getRlineno()), BeanPropertyRowMapper.newInstance(booking_hotel_detail.class));
        booking_guests = jdbcTemplate.query(String.format(sql3,booking_hotel_details_API.get(i).getRequestid(),booking_hotel_details_API.get(i).getRlineno()),BeanPropertyRowMapper.newInstance(booking_guest.class));
        if (booking_guests.isEmpty()){
            booking_guests = jdbcTemplate.query(String.format(String.format("select lastname from NewColumbus.dbo.booking_guest where requestid='%s'",booking_hotel_details_API.get(i).getRequestid())),BeanPropertyRowMapper.newInstance(booking_guest.class));
        }
        if (Objects.equals(booking_hotel_details.getFirst().getCostcurrcode(), "AED")){
            booking_hotel_details.getFirst().setCostvalue(toUsd(booking_hotel_details.getFirst().getCostvalue()));
        }
        if (Objects.equals(booking_hotel_details.getFirst().getSalecurrcode(), "AED")) {
            booking_hotel_details.getFirst().setSalevalue(toUsd(booking_hotel_details.getFirst().getSalevalue()));
        }
        if (Objects.equals(booking_hotel_details.getFirst().getInt_costcurrcode(), "AED")) {
            booking_hotel_details.getFirst().setInt_costprice(toUsd(booking_hotel_details.getFirst().getInt_costprice()));
        }
    }
    public void execute(String query){
        jdbcTemplate.execute(query);
    }
}
