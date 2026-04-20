package cz.uhk.timetable.utils;

import cz.uhk.timetable.model.Activity;
import cz.uhk.timetable.model.LocationTimetable;

import java.time.LocalTime;

public class MockTimetableProvider implements ITimetableProvider {
    @Override
    public LocationTimetable readTimetable(String building, String room) {
        var tt = new LocationTimetable("J", "J22");
        tt.getActivities().add(new Activity("PRO1", "Programování I", "Kozel", "Pondělí", "Cvičení", LocalTime.of(11,35), LocalTime.of(13, 5)));
        tt.getActivities().add(new Activity("PRO1", "Programování I", "Kozel", "Úterý", "Přednáška", LocalTime.of(9,5), LocalTime.of(10, 45)));
        return tt;
    }
}
