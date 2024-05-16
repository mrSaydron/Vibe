package ru.mrak.vibe.service.dataBus;

import android.content.Context;

import java.util.Optional;

import ru.mrak.vibe.util.DataKeys;

public interface DataBusService {
    void setData(Context ctx, DataKeys key, Object value);
    <T> Optional<T> getData(Context ctx, DataKeys key, Class<T> clazz);
}
