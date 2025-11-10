package com.example.buyandsell.data;

import androidx.room.TypeConverter;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class Converters {

    @TypeConverter
    public static String fromStringList(List<String> list) {
        if (list == null) return null;
        JSONArray array = new JSONArray(list);
        return array.toString();
    }

    @TypeConverter
    public static List<String> toStringList(String value) {
        if (value == null || value.isEmpty()) return new ArrayList<>();
        try {
            JSONArray array = new JSONArray(value);
            List<String> result = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                result.add(array.optString(i));
            }
            return result;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}


