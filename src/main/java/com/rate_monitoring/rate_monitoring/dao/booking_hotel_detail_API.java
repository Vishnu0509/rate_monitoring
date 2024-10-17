package com.rate_monitoring.rate_monitoring.dao;

public class booking_hotel_detail_API {
    private String requestid;
    private String apichannelid;
    private int rlineno;
    private String confirmId;
    private String ClientReferenceNumber;
    private String sourceId;

    public String getSourceId() {return sourceId;}

    public void setSourceId(String sourceId) {this.sourceId = sourceId;}

    public String getClientReferenceNumber() {return ClientReferenceNumber;}

    public void setClientReferenceNumber(String clientReferenceNumber) {ClientReferenceNumber = clientReferenceNumber;}

    public String getConfirmId() {
        return confirmId;
    }

    public void setConfirmId(String confirmId) {this.confirmId = confirmId;}

    public int getRlineno() {
        return rlineno;
    }

    public void setRlineno(int rlineno) {this.rlineno = rlineno;}

    public String getApichannelid() {
        return apichannelid;
    }

    public void setApichannelid(String apichannelid) {
        this.apichannelid = apichannelid;
    }

    public String getRequestid() {
        return requestid;
    }

    public void setRequestid(String requestid) {
        this.requestid = requestid;
    }

}
