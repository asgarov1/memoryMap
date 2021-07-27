package com.asgarov.memorymap.util;

import static com.asgarov.memorymap.constants.Constants.IO_DEVS_BLOCK_SIZE;
import static com.asgarov.memorymap.constants.Constants.NUMBER_OF_PARTITIONS;
import static com.asgarov.memorymap.util.StringUtil.customFormat;
import static com.asgarov.memorymap.util.StringUtil.fixedLengthString;
import static java.lang.System.lineSeparator;

public class Formatter {
    public static final String TAB = "\t";

    public static String getRamLine(long rom, long ram) {
        return getRamTitle(ram) + customFormat(rom) + " - " + customFormat(rom + ram - 1) + lineSeparator();
    }

    public static String getRomLine(long rom) {
        return getRomTitle(rom) + 0 + " - " + customFormat(rom - 1) + lineSeparator();
    }


    public static String getFlashDriveLine(long rom, long ram, long flashDrive) {
        if (flashDrive != 0) {
            return getFlashdriveTitle(flashDrive) + customFormat(rom + ram) + " - " + customFormat(rom + ram + flashDrive - 1) +
                    lineSeparator();
        }
        return "";
    }

    public static String getUnusedLine(long usedMemory, long totalMemory, long ioDevsBlockSize) {
        return getUnusedTitle(usedMemory, totalMemory, ioDevsBlockSize) +
                customFormat(usedMemory) + " - " +
                customFormat(totalMemory - ioDevsBlockSize - 1) +
                lineSeparator();
    }

    public static String getIoDevsLine(long totalMemory, long ioDevsBlockSize) {
        return "IO DEVS (1K): " + customFormat(totalMemory - ioDevsBlockSize) + " - " + customFormat(totalMemory - 1) + lineSeparator();
    }

    public static String getDetailedIoDevs(long numberOfDevices, long totalMemory) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < numberOfDevices; i++) {
            result.append(TAB)
                    .append("IO" + (i + 1) + ": ")
                    .append(customFormat(totalMemory - IO_DEVS_BLOCK_SIZE + ((long) getPartitionSize() * i)))
                    .append(" - ")
                    .append(customFormat(totalMemory - IO_DEVS_BLOCK_SIZE + ((long) getPartitionSize() * (i + 1) - 1)))
                    .append(System.lineSeparator());
        }
        result.append(TAB)
                .append("unused: ")
                .append(customFormat(totalMemory - IO_DEVS_BLOCK_SIZE + ((long) getPartitionSize() * numberOfDevices)))
                .append(" - ")
                .append(customFormat(totalMemory - 1));
        return result.toString();
    }

    private static int getPartitionSize() {
        return IO_DEVS_BLOCK_SIZE / NUMBER_OF_PARTITIONS;
    }

    private static String getRomTitle(long rom) {
        return "ROM (" + rom / 1024 + "K): ";
    }

    private static String getFlashdriveTitle(long flashDrive) {
        return "FLASHDRIVE (" + flashDrive + "K): ";
    }

    private static String getUnusedTitle(long usedMemory, long totalMemory, long ioDevsBlockSize) {
        return "UNUSED (" + (totalMemory - (usedMemory + ioDevsBlockSize)) / 1024 + "K): ";
    }

    private static String getRamTitle(long ram) {
        return "RAM (" + ram / 1024 + "K): ";
    }
}
