package com.eureka.common.web;

/**
        * Utility converting the date (stored as long) into human-friendly message.
        *
        * @author Costin Leau
        */
public class WebUtils {

    public static String timeInWords(long time) {
        long elapsed = System.currentTimeMillis() - time;

        // seconds
        elapsed /= 1000;

        if (elapsed < 10) {
            return "time.now";
        }
        if (elapsed < 60) {
            return "time.minute.less";
        }

        // minutes
        elapsed /= 60;

        if (elapsed < 2) {
            return "time.minute";
        }

        if (elapsed < 45) {
            return "time.minutes#" + elapsed;
        }

        if (elapsed < 90) {
            return "time.hour";
        }

        if (elapsed < 1440) {
            return "time.hours#" + elapsed / 60;
        }

        if (elapsed < 2880) {
            return "time.day";
        }

        if (elapsed < 43200) {
            return "time.days#" + (elapsed / 1440);
        }

        if (elapsed < 86400) {
            return "time.month";
        }

        if (elapsed < 525600) {
            return "time.months#" + (elapsed / 43200);
        }

        if (elapsed < 1051199) {
            return "time.year";
        }

        return "time.years#" + (elapsed / 525600);
    }
}
