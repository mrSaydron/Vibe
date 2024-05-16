package ru.mrak.vibe.util;

import ru.mrak.vibe.model.User;

public enum DataKeys {
    USER(true, User.class),
    ;

    private final boolean store;
    private final Class clazz;

    DataKeys(
            boolean store,
            Class clazz

    ) {
        this.store = store;
        this.clazz = clazz;
    }

    public boolean isStore() {
        return store;
    }

    public Class getClazz() {
        return clazz;
    }
}
