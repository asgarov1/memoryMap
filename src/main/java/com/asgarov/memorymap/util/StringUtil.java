package com.asgarov.memorymap.util;

import static java.lang.String.format;

public class StringUtil {
    public static String fixedLengthString(String string, int length) {
        return string + "_".repeat(length - string.length());
    }

    public static String customFormat(long number) {
        return format("0x%X", number).replace("0x", "") + "h";
    }
}
