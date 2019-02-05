package com.challenge.suitecrm.test;

import java.time.LocalDate;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.math3.random.JDKRandomGenerator;

public class CommonGenerator {

    private static JDKRandomGenerator jdkRandomGenerator = new JDKRandomGenerator();

    public static String generateId() {
        return String.valueOf(jdkRandomGenerator.nextLong());
    }

    public static boolean nextBoolean() {
        return jdkRandomGenerator.nextBoolean();
    }

    public static String nextString() {
        return "VALUE_" + jdkRandomGenerator.nextInt();
    }

    public static Byte nextByte() {
        return Integer.valueOf(jdkRandomGenerator.nextInt()).byteValue();
    }

    public static Integer nextInt(int i) {
        return jdkRandomGenerator.nextInt(i);
    }

    public static Integer nextInt() {
        return jdkRandomGenerator.nextInt();
    }

    public static Integer nextBooleanInt() {
        return BooleanUtils.toInteger(jdkRandomGenerator.nextBoolean());
    }

    public static LocalDate nextLocalDate() {
        LocalDate start = LocalDate.of(1970, Month.JANUARY, 1);
        long days = ChronoUnit.DAYS.between(start, LocalDate.now());
        return start.plusDays(jdkRandomGenerator.nextInt((int ) days + 1));
    }

    public static OffsetDateTime nextOffsetDateTime() {
        OffsetDateTime start = OffsetDateTime.now(ZoneId.of("UTC"));
        return start.plus(jdkRandomGenerator.nextLong(), ChronoUnit.MILLIS);
    }
}
