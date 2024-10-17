package com.rate_monitoring.rate_monitoring.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.rate_monitoring.rate_monitoring.dao.booking_guest;
import com.rate_monitoring.rate_monitoring.dao.booking_hotel_detail;
import com.rate_monitoring.rate_monitoring.dao.booking_hotel_detail_API;
import com.rate_monitoring.rate_monitoring.services.costConvertion;

import java.util.Objects;

public class Jumeirah {

    static ObjectMapper mapper = new ObjectMapper();

    public static String jumeirahApiResponse(String hotelcode, String confnumber, String lastname, String email){
        try {
            HttpResponse<String> response;
            String url = "https://servicesuat.jumeirah.com/hotels/book/v1/" + hotelcode.trim() + "/reservations/" + confnumber.trim() + "?last_name=" + lastname.trim() + "&email=" + email.trim();
            response = Unirest.get(url)
                    .header("Ocp-Apim-Subscription-Key", "bf87cfb947a14606a2c35dea01695c74")
                    .header("Content-Type", "application/json;charset=utf-8")
                    .header("Accept", "application/json")
                    .header("Cookie", "DH=!oRX0wbb+wQaGkQY/FvXPeOqBwMW5Mb4vQ2eunlCnyldKJOZRkjKl3DL87DfxiEvK32lIdPg+WjSw; TS0166d696=0156d765a510276253af6de3441bfa46927129ebb8af3f38ad21e88e3080b7b5ae72656c7e39781928c122a34821a783c5865bb89ec0fb13743842d7601f98e6964bb73d88")
                    .asString();
            return response.getBody();
        } catch (Exception e) {
            System.out.println("Problem occurred in api response due to "+e);
        }
        return null;
    }
    public static void jumeirahCostAsPerResponse(booking_hotel_detail_API booking_hotel_details_API, booking_hotel_detail booking_hotel_details, booking_guest booking_guests){
        try {
            String emaii = "reservations@royalpark.com";
            if (booking_hotel_details_API.getRequestid().startsWith("RGT")) {
                emaii = "reservations@royalgulf.com";
            }
            String responseString = jumeirahApiResponse(booking_hotel_details.getInt_partycode(), booking_hotel_details_API.getConfirmId(), booking_guests.getLastname(), emaii);
            JsonNode responseNode = mapper.readTree(responseString);
            double responseCost = 0;
            if (!responseNode.has("amount_after_tax")) {
                responseCost = booking_hotel_details.getInt_costprice();
            } else {
                responseCost = responseNode.get("amount_after_tax").asDouble();
                if (Objects.equals(responseNode.get("Currency").asText(), "AED")) {
                    responseCost = costConvertion.toUsd(responseCost);
                }
            }
            booking_hotel_details.setCost_as_per_response(responseCost);
        } catch (Exception e) {
            System.out.println("Problem occurred in response string due to "+e);
        }}}