package rzepiszczak.damian.tripmaker.trip.management.application.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class DayInformation {
    private String note;
    private List<String> attractions;
}
