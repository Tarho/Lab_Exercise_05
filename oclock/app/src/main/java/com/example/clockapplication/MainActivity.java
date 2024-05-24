package com.example.clockapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TimePicker tp;
    private Button set_alarm;
    private TextView alarmTimeTextView, realTimeClockTextView;
    private EditText edtLabel;
    private Handler handler = new Handler();
    private Runnable runnable;
    private CheckBox checkboxMon, checkboxTue, checkboxWed, checkboxThu, checkboxFri, checkboxSat, checkboxSun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tp = findViewById(R.id.time);
        set_alarm = findViewById(R.id.set_alarm);
        alarmTimeTextView = findViewById(R.id.alarm_time);
        realTimeClockTextView = findViewById(R.id.real_time_clock);
        edtLabel = findViewById(R.id.edtLabel);

        checkboxMon = findViewById(R.id.checkboxMon);
        checkboxTue = findViewById(R.id.checkboxTue);
        checkboxWed = findViewById(R.id.checkboxWed);
        checkboxThu = findViewById(R.id.checkboxThu);
        checkboxFri = findViewById(R.id.checkboxFri);
        checkboxSat = findViewById(R.id.checkboxSat);
        checkboxSun = findViewById(R.id.checkboxSun);

        set_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY, tp.getHour());
                cal.set(Calendar.MINUTE, tp.getMinute());
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);
                if (cal.getTimeInMillis() <= System.currentTimeMillis()) {
                    cal.add(Calendar.DAY_OF_MONTH, 1);
                }

                StringBuilder selectedDays = new StringBuilder();
                if (checkboxMon.isChecked()) selectedDays.append("Mon ");
                if (checkboxTue.isChecked()) selectedDays.append("Tue ");
                if (checkboxWed.isChecked()) selectedDays.append("Wed ");
                if (checkboxThu.isChecked()) selectedDays.append("Thu ");
                if (checkboxFri.isChecked()) selectedDays.append("Fri ");
                if (checkboxSat.isChecked()) selectedDays.append("Sat ");
                if (checkboxSun.isChecked()) selectedDays.append("Sun ");

                String days = selectedDays.toString().trim();
                String label = edtLabel.getText().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
                String alarmTime = sdf.format(cal.getTime());

                setAlarm(cal.getTimeInMillis(), days, label);

                // Start MultipleAlarm activity and pass the alarm details
                Intent intent = new Intent(MainActivity.this, MultipleAlarm.class);
                intent.putExtra("time", alarmTime);
                intent.putExtra("days", days);
                intent.putExtra("label", label);
                startActivity(intent);

                // Update the TextView with the alarm time
                alarmTimeTextView.setText("Alarm set for: " + alarmTime + " on " + days + " with label: " + label);
            }

            private void setAlarm(long timeInMillis, String days, String label) {
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(MainActivity.this, Alarm.class);
                intent.putExtra("days", days);
                intent.putExtra("label", label);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);
                Toast.makeText(MainActivity.this, "Your alarm is set successfully", Toast.LENGTH_SHORT).show();
            }
        });

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
