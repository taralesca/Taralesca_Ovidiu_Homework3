package com.example.taralesca_ovidiu_homework3.fragments;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Camera;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;

import com.example.taralesca_ovidiu_homework3.R;
import com.example.taralesca_ovidiu_homework3.model.Todo;
import com.example.taralesca_ovidiu_homework3.util.AlarmReceiver;

import org.w3c.dom.Text;

import java.util.Calendar;

import static android.content.ContentValues.TAG;
import static android.content.Context.ALARM_SERVICE;

public class TodoDetailsFragment extends Fragment {
    private Todo todo;
    private int selectedHour;
    private int selectedMinute;
    private int selectedYear;
    private int selectedMonth;
    private int selectedDay;


    public TodoDetailsFragment(Todo todo) {
        this.todo = todo;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_todo_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView button1 = (TextView) view.findViewById(R.id.button1);
        TextView button2 = (TextView) view.findViewById(R.id.button2);

        TextView textView = (TextView) view.findViewById(R.id.time);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                selectedHour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                selectedMinute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int sh, int sm) {
                        textView.setText( selectedHour + ":" + selectedMinute);
                        selectedHour = sh;
                        selectedMinute = sm;
                    }
                }, selectedHour, selectedMinute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        TextView dateTextView = (TextView) view.findViewById(R.id.date);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                selectedYear = mcurrentTime.get(Calendar.YEAR);
                selectedMonth = mcurrentTime.get(Calendar.MONTH);
                selectedDay = mcurrentTime.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog mTimePicker;
                mTimePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateTextView.setText(dayOfMonth + "." + month + "." + year);
                    }
                }, selectedYear, selectedMonth, selectedDay);//Yes 24 hour time
                mTimePicker.setTitle("Select Date");
                mTimePicker.show();
            }
        });

        TextView button3 = (TextView) view.findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(ALARM_SERVICE);

                Intent intent = new Intent(getContext(), AlarmReceiver.class);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 101, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                Calendar calendar = Calendar.getInstance();
                calendar.set(selectedYear, selectedMonth, selectedDay);
                calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                calendar.set(Calendar.MINUTE, selectedMinute);
/*
Alarm will be triggered once exactly at 5:30
*/
                if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
                    Log.d(TAG, "onClick: YOU ARE WRONG");
                }
                alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);


            }
        });
    }


}
