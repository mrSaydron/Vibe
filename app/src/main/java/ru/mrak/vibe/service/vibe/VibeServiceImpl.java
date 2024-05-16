package ru.mrak.vibe.service.vibe;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import ru.mrak.vibe.model.VibeDto;

public class VibeServiceImpl implements VideService {

    private final FirebaseDatabase database;
    private final List<Consumer<VibeDto>> newVibeListeners = new ArrayList<>();

    public VibeServiceImpl(FirebaseDatabase database) {
        this.database = database;
    }

    public void newVibeListener(Consumer<VibeDto> listener) {

    }

}
