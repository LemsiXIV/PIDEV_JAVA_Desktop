package com.webandit.webuild.controllers.Project;

import java.sql.Date;
import java.time.ZonedDateTime;

public class CalendarActivity {
    private Date datee;
    private String clientName;
    private Integer serviceNo;

    public CalendarActivity(Date date, String clientName, Integer serviceNo) {
        this.datee = date;
        this.clientName = clientName;
        this.serviceNo = serviceNo;
    }

    public Date getDate() {
        return datee;
    }

    public void setDate(Date date) {
        this.datee = date;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Integer getServiceNo() {
        return serviceNo;
    }

    public void setServiceNo(Integer serviceNo) {
        this.serviceNo = serviceNo;
    }

    @Override
    public String toString() {
        return "CalenderActivity{" +
                "date=" + datee +
                ", clientName='" + clientName + '\'' +
                ", serviceNo=" + serviceNo +
                '}';
    }
}