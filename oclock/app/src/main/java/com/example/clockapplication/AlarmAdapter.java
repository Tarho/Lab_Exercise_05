package com.example.clockapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder> {

    private List<AlarmItem> alarmList;

    public AlarmAdapter(List<AlarmItem> alarmList) {
        this.alarmList = alarmList;
    }

    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_item, parent, false);
        return new AlarmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {
        AlarmItem alarm = alarmList.get(position);
        holder.timeTextView.setText(alarm.getTime());
        holder.daysTextView.setText(alarm.getDays());
        holder.labelTextView.setText(alarm.getLabel());
    }

    @Override
    public int getItemCount() {
        return alarmList.size();
    }

    class AlarmViewHolder extends RecyclerView.ViewHolder {
        TextView timeTextView, daysTextView, labelTextView;

        public AlarmViewHolder(@NonNull View itemView) {
            super(itemView);
            timeTextView = itemView.findViewById(R.id.alarm_time);
            daysTextView = itemView.findViewById(R.id.alarm_days);
            labelTextView = itemView.findViewById(R.id.alarm_label);
        }
    }
}
