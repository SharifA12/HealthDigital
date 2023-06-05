package com.example.healthdigital;

import static android.content.Intent.getIntent;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.internal.TextWatcherAdapter;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReminderActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView mDisplayDate;

    FirebaseFirestore db;

    private TextView title;

    private TextView notes;

    private int position;

    private Date date;

    private ReminderEntry reminderEntry;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private boolean update;

    private Intent intent;

    public LatLng latLng = null;

    private Button saveButton;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reminder);
        db = FirebaseFirestore.getInstance();
        update = false;

        title = (TextView) findViewById(R.id.title);
        notes = (TextView) findViewById(R.id.notes);
//
        mDisplayDate = (TextView) findViewById(R.id.selectDate);

        title.addTextChangedListener(savingTextWatcher);
        mDisplayDate.addTextChangedListener(savingTextWatcher);
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

        intent = getIntent();

        if (intent.hasExtra("name")){

            update = true;

            // get reminder entry
            db.collection("users").document("user1").collection("tasks").document(intent.getStringExtra("name")).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    title.setText(documentSnapshot.getData().get("title").toString());
                    notes.setText(documentSnapshot.getData().get("notes").toString());
                    Timestamp timestamp = (Timestamp) documentSnapshot.getData().get("date");
                    Date date1 = timestamp.toDate();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date1);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    int year = cal.get(Calendar.YEAR);

                    String mDisplayText = year + "/" + month + "/" + day;
                    mDisplayDate.setText(mDisplayText);
                    GeoPoint geoPoint = (GeoPoint) documentSnapshot.getData().get("location");
                    latLng = new LatLng(geoPoint.getLatitude(),geoPoint.getLongitude());
                }
            });
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

        saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                ReminderEntry reminderEntry = new ReminderEntry(title.getText().toString(), notes.getText().toString());

                // set reminder entry

                DocumentReference task;
                Map<String, Object> data = new HashMap<>();
                data.put("title", title.getText().toString());
                data.put("notes", notes.getText().toString());
                try {
                    Date date = new SimpleDateFormat("MM/dd/yyyy").parse(mDisplayDate.getText().toString());
                    data.put("date", new Timestamp(date));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                data.put("location", new GeoPoint(latLng.latitude, latLng.longitude));

                if (update){
                    db.collection("users").document("user1").collection("tasks").document(intent.getStringExtra("name")).update(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Intent intent = new Intent(ReminderActivity.this, MainActivity.class);
                            intent.putExtra("tasks", "tasks");
                            startActivity(intent);
                        }
                    });

                }else {
                    db.collection("users").document("user1").collection("tasks").add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Intent intent = new Intent(ReminderActivity.this, MainActivity.class);
                            intent.putExtra("tasks", "tasks");
                            startActivity(intent);
                        }
                    });
                }


            }
        });

        replaceFragment(new MapFragment());


    }

    private TextWatcher savingTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String userTitle = title.getText().toString().trim();
            String userDate = mDisplayDate.getText().toString().trim();

            saveButton.setEnabled(!userTitle.isEmpty() && !userDate.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();

    }
}
