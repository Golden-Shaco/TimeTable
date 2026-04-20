package cz.uhk.timetable.utils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalTime;

public class LocalTimeAdapter extends TypeAdapter<LocalTime> {
    @Override
    public void write(JsonWriter jsonWriter, LocalTime localTime) throws IOException {

    }

    @Override
    public LocalTime read(JsonReader jsonReader) throws IOException {
        jsonReader.beginObject();
        jsonReader.nextName();
        var timeString = jsonReader.nextString();
        jsonReader.endObject();
        return LocalTime.parse(timeString);
    }
}
