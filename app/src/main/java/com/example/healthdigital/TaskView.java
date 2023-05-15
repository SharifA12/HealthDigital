package com.example.healthdigital;

import android.app.Activity;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TaskView extends Activity {
    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_view);

        SetupRecycleView();
    }

    void SetupRecycleView(){
        RecyclerView rvTasks = findViewById(R.id.rvTasks);

        String[] tasks = {"Task #1","Task #2","Task #3","Task #4"};

        TaskViewAdapter adapter = new TaskViewAdapter(tasks);
        rvTasks.setAdapter(adapter);
        rvTasks.setLayoutManager(new LinearLayoutManager(this));



    }
}
