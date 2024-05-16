package ru.mrak.vibe.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import ru.mrak.vibe.R;
import ru.mrak.vibe.model.VibeDto;

public class VibeListActivity extends AppCompatActivity {

    private Button buttonVibeAdd;

    private List<VibeDto> vibeList;
    private RecyclerView recyclerViewVibeList;
    private VibeAdapter vibeAdapter;
    private RecyclerView.LayoutManager vibeLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vibe_list_activity);

        buttonVibeAdd = findViewById(R.id.buttonVibeAdd);
        buttonVibeAdd.setOnClickListener(l -> {
            Intent intent = new Intent(VibeListActivity.this, VibeCreateActivity.class);
            startActivity(intent);
        });

        vibeList = new ArrayList<>();
        vibeList.add(new VibeDto(
                "Тупое предположение",
                "Очень длинное предположение ни о чем конткретном не говорящее, и которое совсем не обязательно должно сбываться",
                Instant.now(),
                Instant.now(),
                Instant.now(),
                "LID2ZD5ko4TsuQfqoYa3G7U9mod2",
                "Олег"
        ));
        vibeList.add(new VibeDto(
                "Наступление",
                "Какое-то описание предположения",
                Instant.now(),
                Instant.now(),
                Instant.now(),
                "LID2ZD5ko4TsuQfqoYa3G7U9mod2",
                "мудрый кот"
        ));
        vibeList.add(new VibeDto(
                "Еще одно предположение",
                "Думаю, что это предположение не отобразиться",
                Instant.now(),
                Instant.now(),
                Instant.now(),
                "LID2ZD5ko4TsuQfqoYa3G7U9mod2",
                "тупой программист"
        ));

        recyclerViewVibeList = findViewById(R.id.recyclerViewVibeList);
        recyclerViewVibeList.setHasFixedSize(true);
        recyclerViewVibeList.addItemDecoration(new DividerItemDecoration(
                recyclerViewVibeList.getContext(),
                DividerItemDecoration.VERTICAL
        ));
        vibeLayoutManager = new LinearLayoutManager(this);
        vibeAdapter = new VibeAdapter(vibeList);

        recyclerViewVibeList.setLayoutManager(vibeLayoutManager);
        recyclerViewVibeList.setAdapter(vibeAdapter);
    }
}