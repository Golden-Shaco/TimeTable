package cz.uhk.timetable.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.*;

public class LocationRoom {
    @SerializedName("mistnostInfo")
    public ArrayList<Room> rooms = new ArrayList<>();

    public LocationRoom() {}

    public LocationRoom(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    public String[] getRoomNames() {
        String[] roomNames = new String[rooms.size()];
        for (int i = 0; i < rooms.size(); i++) {
            roomNames[i] = rooms.get(i).getCisloMistnosti();
        }
        try {
            Arrays.sort(roomNames, Comparator.comparingInt(s -> Integer.parseInt(s.replaceAll("\\D+", ""))));
        } catch (Exception e) {
            System.out.println(e);
        }
        return roomNames;
    }
}
