package com.example.healthdigital;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class TaskViewFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.task_view, container, false);
        SetupRecycleView(root);
        Log.d("Create", "TaksViewCreate");
        // Inflate the layout for this fragment
        return root;
    }

    void SetupRecycleView(View root){
        RecyclerView rvTasks = root.findViewById(R.id.rvTasks);

        String[] tasks = {"Task #1","Task #2","Task #3","Task #4","Task #1","Task #2","Task #3",
                "Task #4","Task #1","Task #2","Task #3","Task #4","Task #1","Task #2","Task #3",
                "Task #4"};
        List<String> list = new ArrayList<String>(Arrays.asList(tasks));
        TaskViewAdapter adapter = new TaskViewAdapter(list, getContext());
        rvTasks.setAdapter(adapter);
        rvTasks.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.setOnItemClickListener(new TaskViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                list.remove(position);
                adapter.notifyItemRemoved(position);
            }
        });



    }
}