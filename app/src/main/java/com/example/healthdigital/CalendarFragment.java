package com.example.healthdigital;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment {

    Intent intent;
    private ArrayList<ReminderEntry> list;

    private Map<ReminderEntry, String> documents;

    FirebaseFirestore db ;

    Date selectedDate;

    Date selectedDateDay;

    private CalendarAdapeter adapter;

    RecyclerView todaysTasks;

    private Button openButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("Create", "CalendarCreate");

        View root = inflater.inflate(R.layout.calendar, container, false);
        // Inflate the layout for this fragment

        db = FirebaseFirestore.getInstance();


        CalendarView calendarView = root.findViewById(R.id.calendarView);
        calendarView.setDate(System.currentTimeMillis(),false,true);

        selectedDate = new Date();
        selectedDateDay = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(selectedDateDay);
        c.add(Calendar.DATE, 1);
        selectedDateDay = c.getTime();


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                Log.e("DATE", "today: " + year + "/" + month + "/" + day);
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DATE, day);
                calendar.set(Calendar.MILLISECOND, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                selectedDate = calendar.getTime();
                Log.e("DATE", "selected date: " + selectedDate);
                calendar.add(Calendar.DATE, 1);
                selectedDateDay = calendar.getTime();
                Log.e("DATE", "selected date tomorrow:" + selectedDateDay);
                SetupRecycleView(root);

            }
        });




        return root;
    }

    private void SetupRecycleView(View root) {

        todaysTasks = root.findViewById(R.id.rvCalendar);
        todaysTasks.setHasFixedSize(true);
        todaysTasks.setLayoutManager(new LinearLayoutManager(getContext()));
        list = new ArrayList<ReminderEntry>();
        adapter = new CalendarAdapeter(getContext(), list);
        todaysTasks.setAdapter(adapter);


        Timestamp todayMorning = new Timestamp(selectedDate);
        Timestamp todayNight = new Timestamp(selectedDateDay);
//.whereGreaterThanOrEqualTo("date",todayMorning).whereLessThan("date",todayNight )
        db.collection("users").document("user1").collection("tasks").whereGreaterThanOrEqualTo("date",todayMorning).whereLessThan("date", todayNight).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                Log.e("executed", "executed onEvent");
                if (error != null){
                    Log.e("firestore error", error.getMessage());
                }

                for (DocumentChange dc : value.getDocumentChanges()) {
                    Log.e("new item", "item");
                    if (dc.getType() == DocumentChange.Type.ADDED){
                        ReminderEntry item = dc.getDocument().toObject(ReminderEntry.class);
                        list.add(item);
                    }

                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}