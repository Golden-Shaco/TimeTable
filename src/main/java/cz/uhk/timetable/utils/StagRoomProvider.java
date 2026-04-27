package cz.uhk.timetable.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cz.uhk.timetable.model.LocationRoom;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class StagRoomProvider{
    private static final String ROOM_URL = "https://stag-demo.uhk.cz/ws/services/rest2/mistnost/getMistnostiInfo?zkrBudovy=BU&pracoviste=%25&typ=U&outputFormat=JSON&cisloMistnosti=%25";
    private Gson gson;

    public StagRoomProvider(){
        gson = new GsonBuilder().create();
    }

    public LocationRoom readRooms(String building) {
        try {
            var url = new URL(ROOM_URL.replace("BU", building));
            var reader = new InputStreamReader(url.openStream());
            return gson.fromJson(reader, LocationRoom.class);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
