package com.example.myapplication.util;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;

public class Date {
    @SuppressLint("SimpleDateFormat")
    public static String getNowDateTime() {

        SimpleDateFormat s_format = new SimpleDateFormat("yyyyMMddhhmmss");

        return s_format.format(new Date());

    }
    @SuppressLint("SimpleDateFormat")

    public static String getNowTime() {

        SimpleDateFormat s_format = new SimpleDateFormat("HH:mm:ss");

        return s_format.format(new Date());

    }



}

