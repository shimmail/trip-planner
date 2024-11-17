package org.example.api.enums;

public enum ItineraryStatus {
    TODO(0, "待办"),
    DONE(1, "已完成");

    private final int code;
    private final String description;

    ItineraryStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static ItineraryStatus fromCode(int code) {
        for (ItineraryStatus status : ItineraryStatus.values()) {
            if (status.code == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }
}

