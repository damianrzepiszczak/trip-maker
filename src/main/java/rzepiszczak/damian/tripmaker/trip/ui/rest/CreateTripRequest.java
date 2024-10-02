package rzepiszczak.damian.tripmaker.trip.ui.rest;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
class CreateTripRequest {
    private String travelerId;
    private String destination;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate from;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate to;
}
