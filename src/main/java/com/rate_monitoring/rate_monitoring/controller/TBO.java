package com.rate_monitoring.rate_monitoring.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.rate_monitoring.rate_monitoring.dao.booking_hotel_detail;
import com.rate_monitoring.rate_monitoring.dao.booking_hotel_detail_API;

import java.util.Objects;

import static com.rate_monitoring.rate_monitoring.services.costConvertion.toUsd;

public class TBO {

    static ObjectMapper mapper = new ObjectMapper();

    public static String tboApiResponse(String conformation, String reference) {
            HttpResponse<String> response = null;
            try {
                String body = "{\r\n    \"PaymentMode\": \"Limit\",\r\n    \"ConfirmationNumber\": \"" + conformation + "\",\r\n    \"BookingReferenceId\": \"" + reference + "\"\r\n\r\n}";
                response = Unirest.post("http://api.tbotechnology.in/TBOHolidays_HotelAPI/BookingDetail")
                        .header("Content-Type", "application/json")
                        .header("Authorization",
                                "Basic cGFya3JveWFsVGVzdDpSb3lAMjYxNzg5MTM=")
                        .body(body)
                        .asString();
                return response.getBody();
            } catch (Exception e) {
                System.out.println("Problem occurred in getting api response due to " + e);
            }
            return null;

    }
    public static void tboCostAsPerResponse(booking_hotel_detail_API booking_hotel_details_API, booking_hotel_detail booking_hotel_details) {
        try {
            JsonNode responseNode = null;
                String responseString = tboApiResponse(booking_hotel_details_API.getConfirmId(), booking_hotel_details_API.getClientReferenceNumber());
                responseNode = mapper.readTree(responseString);
            double responseCost = 0;
            assert responseNode != null;
            if (!responseNode.has("BookingDetail")) {
                responseCost = booking_hotel_details.getInt_costprice();
            } else {
                responseCost = responseNode.get("BookingDetail").get("Rooms").get(0).get("TotalFare").asDouble();
                if (Objects.equals(responseNode.get("BookingDetail").get("Rooms").get(0).get("Currency").asText(), "AED")) {
                    responseCost = toUsd(responseCost);
                }
            }
            booking_hotel_details.setCost_as_per_response(responseCost);
        } catch (Exception e) {
            System.out.println("Problem occurred in Api response string due to "+e);
        }
    }
}
