package com.example.kitchentimer.legacy;


public class LegacyKitchenBuzzer {
    public static final int OK = 0;
    public static final int INVALID_OUTPUT = 11;
    public static final int DEVICE_BUSY = 22;

    public int pulse(int outputNumber, byte[] pulsePattern) throws LegacyHardwareException {
        if (outputNumber < 0 || pulsePattern == null || pulsePattern.length == 0) {
            return INVALID_OUTPUT;
        }
        return OK;
    }
}
