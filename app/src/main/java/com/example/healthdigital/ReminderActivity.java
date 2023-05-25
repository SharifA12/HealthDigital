package com.example.healthdigital;

import static android.content.Intent.getIntent;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;

public class ReminderActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView mDisplayDate;

    private TextView title;

    private TextView notes;

    private int position;

    private Date date;

    private ReminderEntry reminderEntry;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reminder);



        title = (TextView) findViewById(R.id.title);
        notes = (TextView) findViewById(R.id.notes);
//
        mDisplayDate = (TextView) findViewById(R.id.selectDate);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ReminderActivity.this, // idk
                        android.R.style.Theme_DeviceDefault_Dialog_Alert,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
                date = new Date(year,month, day);
                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }
        };

        Intent intent = getIntent();

        if (intent.hasExtra("position")){
            position = intent.getIntExtra("position", 0);
            Log.d("position", "called" + position);
            reminderEntry = intent.getParcelableExtra("reminderEntry");
            title.setText(reminderEntry.getTitle());
            notes.setText(reminderEntry.getNotes());
            mDisplayDate.setText(reminderEntry.getDate().toString());
        }

        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View MainActivity) {

                Intent resultIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, resultIntent);
                finish();
            }
        });

        Button saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ReminderEntry reminderEntry = new ReminderEntry(title.getText().toString(), notes.getText().toString());
                Intent intent = new Intent();
                intent.putExtra("reminderEntry", reminderEntry);
                Log.d("position", "called" + position);
                intent.putExtra("position", position);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });



    }
}
