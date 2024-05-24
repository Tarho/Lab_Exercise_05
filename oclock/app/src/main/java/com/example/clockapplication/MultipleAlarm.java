package com.example.clockapplication;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MultipleAlarm extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AlarmAdapter alarmAdapter;
    private List<AlarmItem> alarmList;
    private TextView realTimeClockTextView;
    private Handler handler = new Handler();
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_multiple_alarm);

        realTimeClockTextView = findViewById(R.id.real_time_clock);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        alarmList = new ArrayList<>();

        // Get the alarm details from the intent
        if (getIntent() != null && getIntent().hasExtra("time") && getIntent().hasExtra("days") && getIntent().hasExtra("label")) {
            String time = getIntent().getStringExtra("time");
            String days = getIntent().getStringExtra("days");
            String label = getIntent().getStringExtra("label");
            alarmList.add(new AlarmItem(time, days, label));
        }

        alarmAdapter = new AlarmAdapter(alarmList);
        recyclerView.setAdapter(alarmAdapter);

        // Initialize the real-time clock update
        runnable = new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a", Locale.getDefault());
                String currentTime = sdf.format(Calendar.getInstance().getTime());
                realTimeClockTextView.setText(currentTime);
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(runnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}
