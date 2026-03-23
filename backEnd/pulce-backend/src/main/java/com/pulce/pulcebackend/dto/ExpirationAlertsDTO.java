package com.pulce.pulcebackend.dto;

import com.pulce.pulcebackend.entity.Item;
import java.util.List;

public class ExpirationAlertsDTO {
    private List<Item> expired;
    private List<Item> urgent;
    private List<Item> warning;
    private List<Item> upcoming;

    public ExpirationAlertsDTO(List<Item> expired, List<Item> urgent, List<Item> warning, List<Item> upcoming) {
        this.expired = expired;
        this.urgent = urgent;
        this.warning = warning;
        this.upcoming = upcoming;
    }

    public List<Item> getExpired() { return expired; }

    public List<Item> getUrgent() {
        return urgent;
    }

    public List<Item> getWarning() {
        return warning;
    }

    public List<Item> getUpcoming() {
        return upcoming;
    }
}
