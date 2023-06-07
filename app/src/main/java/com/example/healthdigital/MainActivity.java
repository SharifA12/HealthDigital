package com.example.healthdigital;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.healthdigital.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    Button swapToTaskView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        Log.d("Entered", "onCreateMain");
        binding.navViewItem.setOnItemSelectedListener(item -> {
            Log.d("Entered", "OnItemSelected");
            if (item.getItemId() == R.id.tasks){
                replaceFragment(new TaskViewFragmentEdit());
            } else if (item.getItemId() == R.id.calendar) {
                replaceFragment(new CalendarFragment());
            }else if (item.getItemId() == R.id.home) {
                replaceFragment(new HomeFragment());
            }
            return true;

        });

        Intent intent = getIntent();
        if (intent.hasExtra("tasks")){
            binding.navViewItem.setSelectedItemId(R.id.tasks);
        }


    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

}