package com.rate_monitoring.rate_monitoring.dao;

public class booking_hotel_detail {
    private String checkin;
    private String checkout;
    private double Int_costprice;
    private double costvalue;
    private double salevalue;
    private String Int_costcurrcode;
    private String salecurrcode;
    private String costcurrcode;
    private double MarkupInAed;
    private double actual_markup;
    private String result;
    private String source;
    private String partyname;
    private String roomDisplayString;
    private double cost_as_per_response;
    private String Int_partycode;
    private double difference_sale;

    public double getCostvalue() {return costvalue;}

    public void setCostvalue(double costvalue) {this.costvalue = costvalue;}

    public String getCostcurrcode() {return costcurrcode;}

    public void setCostcurrcode(String costcurrcode) {this.costcurrcode = costcurrcode;}

    public String getInt_partycode() {
        return Int_partycode;
    }

    public void setInt_partycode(String int_partycode) {Int_partycode = int_partycode;}

    public double getCost_as_per_response() {
        return cost_as_per_response;
    }

    public void setCost_as_per_response(double cost_as_per_response) {this.cost_as_per_response = cost_as_per_response;}

    public String getRoomDisplayString() {
        return roomDisplayString;
    }

    public void setRoomDisplayString(String roomDisplayString) {this.roomDisplayString = roomDisplayString;}

    public String getPartyname() {
        return partyname;
    }

    public void setPartyname(String partyname) {
        this.partyname = partyname;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public double getDifference_sale() {return difference_sale;}

    public void setDifference_sale(double difference_sale) {this.difference_sale = difference_sale;}

    public double getMarkupInAed() {return MarkupInAed;}

    public void setMarkupInAed(double markupInAed) {MarkupInAed = markupInAed;}

    public double getActual_markup() {
        return actual_markup;
    }

    public void setActual_markup(double actual_markup) {
        this.actual_markup = actual_markup;
    }

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }

    public double getInt_costprice() {
        return Int_costprice;
    }

    public void setInt_costprice(double Int_costprice) {
        this.Int_costprice = Int_costprice;
    }

    public double getSalevalue() {
        return salevalue;
    }

    public void setSalevalue(double salevalue) {
        this.salevalue = salevalue;
    }

    public String getInt_costcurrcode() {return Int_costcurrcode;}

    public void setInt_costcurrcode(String Int_costcurrcode) {
        this.Int_costcurrcode = Int_costcurrcode;
    }

    public String getSalecurrcode() {
        return salecurrcode;
    }

    public void setSalecurrcode(String salecurrcode) {
        this.salecurrcode = salecurrcode;
    }

}
