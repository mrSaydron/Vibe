package ru.mrak.vibe.service.database;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class FactoryServiceImpl implements FactoryService {

    private FirebaseDatabase database;

    private ValueEventListener vibeListener;
    private Map<String, Object> vibeListeners;

    public void addVibeListener(String key, Object listener) {

    }

    public void removeVibeListener(String key) {

    }

}
