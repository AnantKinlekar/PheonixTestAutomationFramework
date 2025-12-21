package com.api.constant;

public enum Service_Location {
    SERVICE_LOCATION_A(1),
    SERVICE_LOCATION_B(2),
    SERVICE_LOCATION_C(3),
    SERVICE_LOCATION_D(4);

    int code;

    private Service_Location(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
