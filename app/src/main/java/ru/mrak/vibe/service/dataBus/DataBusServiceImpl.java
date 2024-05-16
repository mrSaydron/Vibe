package ru.mrak.vibe.service.dataBus;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import ru.mrak.vibe.util.DataKeys;

public class DataBusServiceImpl implements DataBusService {

    public static final String TAG = "DataBusServiceImpl";

    private final Map<DataKeys, Object> data = new HashMap<>();
    private final ObjectMapper objectMapper;

    public DataBusServiceImpl(ObjectMapper objectMapper) {
        Log.d(TAG, "constructor");
        this.objectMapper = objectMapper;
    }

    @Override
    public void setData(Context ctx, DataKeys key, Object value) {
        Log.d(TAG, "setData, key: " + key.name() + "value: " + (value != null ? value.toString() : "null"));
        data.put(key, value);

        if (key.isStore()) {
            try {
                SharedPreferences sharedPreferences = ctx.getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(key.name(), value != null ? objectMapper.writeValueAsString(value) : null);
                editor.apply();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public <T> Optional<T> getData(Context ctx, DataKeys key, Class<T> clazz) {
        Log.d(TAG, "getData, key: " + key.name());

        Object o = data.get(key);

        if (o == null && !key.isStore()) {
            return Optional.empty();
        }

        if (o != null) {
            if (clazz.isAssignableFrom(o.getClass())) {
                return Optional.of(clazz.cast(o));
            }  else {
                throw new RuntimeException();
            }
        }

        SharedPreferences sharedPreferences = ctx.getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);
        String stringResult = sharedPreferences.getString(key.name(), null);

        if (stringResult == null) {
            return Optional.empty();
        }

        T result;
        try {
            result = objectMapper.readValue(stringResult, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        data.put(key, result);
        return Optional.of(result);
    }
}
