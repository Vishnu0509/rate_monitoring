package com.rate_monitoring.rate_monitoring.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.rate_monitoring.rate_monitoring.dao.booking_hotel_detail;
import com.rate_monitoring.rate_monitoring.dao.booking_hotel_detail_API;

import java.util.Objects;

import static com.rate_monitoring.rate_monitoring.services.costConvertion.toUsd;

public class IWTX {

    static ObjectMapper mapper = new ObjectMapper();

    public static String iwtxApiResponse(String conformation, String source){
        try {
            String Body = "{\r\n  \"profile\": {\r\n      \"password\":\"iOLxD3vT!3$t\",\r\n      \"code\":\"iOLXforDev\",\r\n    \"tokenNumber\": \"string\"\r\n  },\r\n  \"bookingDetails\": {\r\n    \"bookingNo\": " + conformation + ",\r\n    \"source\": " + source + "\r\n  }\r\n}";
            HttpResponse<String> response = Unirest.post("https://api.iwtxconnect.com/hotel/booking/retrieve")
                    .header("Content-Type", "application/json")
                    .body(Body)
                    .asString();
            return response.getBody();
        } catch (Exception e) {
            System.out.println("Problem occurred in getting Api Response due to "+e);
        }
        return null;
    }
    public static void iwtxCostAsPerResponse(booking_hotel_detail_API booking_hotel_details_API, booking_hotel_detail booking_hotel_details) {
        try{
        String responseString = iwtxApiResponse(booking_hotel_details_API.getConfirmId(),booking_hotel_details_API.getSourceId());
        JsonNode responseNode = mapper.readTree(responseString);
        double responseCost=0;
        if (!responseNode.has("BookingDetails")){
            responseCost=booking_hotel_details.getInt_costprice();
        }
        else{
            responseCost = responseNode.get("BookingDetails").get("BookingTotalRate").asDouble();
            if (Objects.equals(responseNode.get("BookingDetails").get("Currency").asText(), "AED")){
                responseCost=toUsd(responseCost);
            }
        }
        booking_hotel_details.setCost_as_per_response(responseCost);
    } catch (Exception e) {
            System.out.println("Problem occurred in api response string due to "+e);
        }
    }
}
