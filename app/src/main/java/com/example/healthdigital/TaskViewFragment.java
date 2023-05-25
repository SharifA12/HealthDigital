package com.example.healthdigital;

import static android.content.Intent.getIntent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class TaskViewFragment extends Fragment {
    private boolean sortTimeA;
    private boolean sortLocationA;

    private List<ReminderEntry> list;

    private TaskViewAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.task_view, container, false);


        sortLocationA = true;
        sortTimeA = true;
        SetupSorting(root);
        SetupRecycleView(root);
        Log.d("Create", "TaksViewCreate");
        // Inflate the layout for this fragment
        return root;
    }

    void SetupSorting(View root){
        ImageView sortTime = root.findViewById(R.id.sortTime);
        ImageView sortLocation = root.findViewById(R.id.SortLocation);

        sortLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortLocationA = !sortLocationA;

                if (sortLocationA){
                    // sort by ascending;
                }else {
                    // sort by descending;
                }
            }
        });

        sortTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortTimeA = !sortTimeA;

                if (sortTimeA){
                    // sort by ascending;
                    Collections.sort(list, new Comparator<ReminderEntry>() {
                        @Override
                        public int compare(ReminderEntry t1, ReminderEntry t2) {
                            return t1.getDate().compareTo(t2.getDate());
                        }
                    });

                    adapter.notifyDataSetChanged();

                }else {

                    Collections.sort(list, new Comparator<ReminderEntry>() {
                        @Override
                        public int compare(ReminderEntry t1, ReminderEntry t2) {
                            return t1.getDate().compareTo(t2.getDate());
                        }
                    });
                    // sort by descending;
                    Collections.reverse(list);
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }

    void SetupRecycleView(View root){
        RecyclerView rvTasks = root.findViewById(R.id.rvTasks);

        list = new ArrayList<ReminderEntry>();
        adapter = new TaskViewAdapter(list, getContext());
        rvTasks.setAdapter(adapter);
        rvTasks.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.setOnItemClickListener(new TaskViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClickDelete(int position) {
                list.remove(position);
                adapter.notifyItemRemoved(position);
            }

            @Override
            public void onItemClickEdit(int position) {
                Intent intent = new Intent(getContext(), ReminderActivity.class);
                intent.putExtra("position", position);
                Log.d("position", "called" + position);
                intent.putExtra("reminderEntry", list.get(position));
                ActivityResultLauncherPosition.launch(intent);
            }
        });

        Button newReminderEntry = root.findViewById(R.id.SetReminder);

        newReminderEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ReminderActivity.class);
                someActivityResultLauncher.launch(intent);
            }
        });

    }


    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.d("ActivityResult", "called");
                    Intent data = result.getData();


                    if(result.getResultCode() == Activity.RESULT_OK){
                        list.add(data.getParcelableExtra("reminderEntry"));
                        adapter.notifyItemInserted(list.size() - 1);
                    }



                }
            });

    ActivityResultLauncher<Intent> ActivityResultLauncherPosition = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.d("ActivityResult", "called");
                    Intent data = result.getData();

                    if(result.getResultCode() == Activity.RESULT_OK){
                        int position = data.getIntExtra("position", 0);
                        Log.d("position", "called" + position);
                        list.remove(position);
                        adapter.notifyItemRemoved(position);
                        list.add(data.getParcelableExtra("reminderEntry"));
                        adapter.notifyItemInserted(list.size()-1);
                    }

                }
            });
}