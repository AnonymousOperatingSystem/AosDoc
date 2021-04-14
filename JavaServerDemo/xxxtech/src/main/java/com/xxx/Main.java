package com.xxx;

import java.util.Calendar;

public class Main {
    public static void maixn(String[] args) {
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.get(Calendar.YEAR)+"-"
                +calendar.get(Calendar.MONTH)+"-"
                +calendar.get(Calendar.DAY_OF_MONTH)+"-"
                +calendar.get(Calendar.HOUR_OF_DAY)+"-"
                +calendar.get(Calendar.MINUTE)+"-"
                +calendar.get(Calendar.SECOND)+"-");

        calendar.add(Calendar.MONTH, 120);

        System.out.println(calendar.get(Calendar.YEAR)+"-"
                +calendar.get(Calendar.MONTH)+"-"
                +calendar.get(Calendar.DAY_OF_MONTH)+"-"
                +calendar.get(Calendar.HOUR_OF_DAY)+"-"
                +calendar.get(Calendar.MINUTE)+"-"
                +calendar.get(Calendar.SECOND)+"-");
    }
}
