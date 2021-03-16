package com.elcompanies.eventhubkafkaconsumer.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConsumerMessage {
    private int processAtMinutes;
    private String message;
}
