package com.example.taralesca_ovidiu_homework3.fragments;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.taralesca_ovidiu_homework3.R;
import com.example.taralesca_ovidiu_homework3.model.Todo;
import com.example.taralesca_ovidiu_homework3.util.AlarmReceiver;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static android.app.AlarmManager.RTC;
import static android.content.Context.ALARM_SERVICE;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

public class TodoDetailsFragment extends Fragment {
    private Todo todo;
    private int selectedHour;
    private int selectedMinute;
    private int selectedYear;
    private int selectedMonth;
    private int selectedDay;

    TodoDetailsFragment(Todo todo) {
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

        final TextView setTimeButton = view.findViewById(R.id.set_time_button);
        final TextView setDateButton = view.findViewById(R.id.set_date_button);
        final TextView setNotificationButton = view.findViewById(R.id.set_notification_button);
        final TextView textView = view.findViewById(R.id.time);
        final TextView dateTextView = view.findViewById(R.id.date);

        setTimeButton.setOnClickListener(v -> launchTimePicker(textView));
        setDateButton.setOnClickListener(v -> launchDatePicker(dateTextView));
        setNotificationButton.setOnClickListener(v -> setAlarmForNotification());
    }

    private void setAlarmForNotification() {
        final AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(ALARM_SERVICE);
        final Intent intent = new Intent(getContext(), AlarmReceiver.class);
        intent.putExtra("message", todo.getTitle());
        final PendingIntent pendingIntent = PendingIntent
                .getBroadcast(getContext(), 101, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        final Calendar calendar = Calendar.getInstance();
        calendar.set(selectedYear, selectedMonth, selectedDay);
        calendar.set(HOUR_OF_DAY, selectedHour);
        calendar.set(MINUTE, selectedMinute);

        alarmManager.set(RTC, calendar.getTimeInMillis(), pendingIntent);

        Toast.makeText(getContext(), R.string.successful_notification_message, Toast.LENGTH_LONG).show();
    }

    private void launchDatePicker(TextView dateTextView) {
        final Calendar selectedDate = Calendar.getInstance();
        selectedYear = selectedDate.get(YEAR);
        selectedMonth = selectedDate.get(MONTH);
        selectedDay = selectedDate.get(DAY_OF_MONTH);

        final SimpleDateFormat dateFormat = new SimpleDateFormat(getString(R.string.date_format), Locale.US);

        final DatePickerDialog datePicker =
                new DatePickerDialog(getContext(),
                        (view, pickedYear, pickedMonth, pickedDay) -> {
                            this.selectedDay = pickedDay;
                            this.selectedMonth = pickedMonth;
                            this.selectedYear = pickedYear;
                            selectedDate.set(pickedYear, pickedMonth, pickedDay);
                            dateTextView.setText(dateFormat.format(selectedDate.getTime()));
                        },
                        selectedYear, selectedMonth, selectedDay);
        datePicker.setTitle(getString(R.string.set_date_button));
        datePicker.show();
    }

    private void launchTimePicker(TextView timeTextView) {
        final Calendar selectedTime = Calendar.getInstance();
        selectedHour = selectedTime.get(HOUR_OF_DAY);
        selectedMinute = selectedTime.get(MINUTE);

        final SimpleDateFormat timeFormat = new SimpleDateFormat(getString(R.string.time_format), Locale.US);

        final TimePickerDialog timePicker =
                new TimePickerDialog(getContext(),
                        (view, pickedHour, pickedMinute) -> {
                            this.selectedHour = pickedHour;
                            this.selectedMinute = pickedMinute;
                            selectedTime.set(HOUR_OF_DAY, pickedHour);
                            selectedTime.set(MINUTE, pickedMinute);
                            timeTextView.setText(timeFormat.format(selectedTime.getTime()));
                        },
                        selectedHour, selectedMinute, true);
        timePicker.setTitle(getString(R.string.set_time_button));
        timePicker.show();
    }


}
