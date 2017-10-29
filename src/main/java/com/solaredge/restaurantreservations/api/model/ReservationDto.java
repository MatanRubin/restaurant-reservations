package com.solaredge.restaurantreservations.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
public class ReservationDto {
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startTime;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endTime;

    @NotNull
    private String tableName;

    @Min(1)
    @JsonProperty("nPeople")
    private int nPeople;

    public ReservationDto() {
        this(null, null, null, null, null, 10);
    }
}
