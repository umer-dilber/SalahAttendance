package com.example.salahattendance_app;

import java.sql.Date;

public class MyData {
    private String date;
    private int Fajar;
    private int Zuhr;
    private int Asar;
    private int Maghrib;
    private int Esha;

    public MyData() {
    }

    public MyData(String dt, int Faj, int zh, int asr, int mag, int es) {
        this.date = dt;
        this.Fajar = Faj;
        this.Zuhr = zh;
        this.Asar = asr;
        this.Maghrib = mag;
        this.Esha = es;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getFajar() {
        return Fajar;
    }

    public void setFajar(int fajar) {
        this.Fajar = fajar;
    }

    public int getZuhr() {
        return Zuhr;
    }

    public void setZuhr(int zuhr) {
        this.Zuhr = zuhr;
    }

    public int getAsar() {
        return Asar;
    }

    public void setAsar(int asar) {
        this.Asar = asar;
    }

    public int getMaghrib() {
        return Maghrib;
    }

    public void setMaghrib(int maghrib) {
        this.Maghrib = maghrib;
    }

    public int getEsha() {
        return Esha;
    }

    public void setEsha(int esha) {
        this.Esha = esha;
    }
}

