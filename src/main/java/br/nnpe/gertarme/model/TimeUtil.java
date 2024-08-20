package br.nnpe.gertarme.model;

import java.sql.Timestamp;

import java.text.DecimalFormat;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class TimeUtil {

    public static String gregorianCalendarToString(long time) {
        DecimalFormat decimalFormat = new DecimalFormat("00");
        GregorianCalendar gregCal = new GregorianCalendar();
        gregCal.setTimeInMillis(time);
        int ano = gregCal.get(Calendar.YEAR);
        int mes = gregCal.get(Calendar.MONTH) + 1;
        int dia = gregCal.get(Calendar.DAY_OF_MONTH);
        int hora = gregCal.get(Calendar.HOUR_OF_DAY);
        int minuto = gregCal.get(Calendar.MINUTE);

        return decimalFormat.format(dia) + "/" + decimalFormat.format(mes) +
        "/" + ano + " " + decimalFormat.format(hora) + ":" +
        decimalFormat.format(minuto);
    }

    public static long string2GregorianCalendar(String data) {
        int dia = Integer.parseInt(data.substring(0, 2));
        int mes = Integer.parseInt(data.substring(3, 5));
        int ano = Integer.parseInt(data.substring(6, 10));
        int hora = Integer.parseInt(data.substring(11, 13));
        int minuto = Integer.parseInt(data.substring(14, 16));
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.set(ano, mes - 1, dia, hora, minuto);

        return gregorianCalendar.getTimeInMillis();
    }
}
