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

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ProgressDialog;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class TaskViewFragmentEdit extends Fragment {
    private boolean sortTimeA;
    private boolean sortLocationA;

    Intent intent;
    ArrayList<ReminderEntry> list;

    Map<ReminderEntry, String> documents;

    FirebaseFirestore db ;

    TaskViewAdapterEdit adapter;

    RecyclerView rvTasks;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.task_view, container, false);
        db = FirebaseFirestore.getInstance();
        Log.e("Entered", "Entered the task class");
//        sortLocationA = true;
//        sortTimeA = true;
//        SetupSorting(root);
        SetupRecycleView(root);

        return root;
    }

//

    void SetupRecycleView(View root){

        rvTasks = root.findViewById(R.id.rvTasks);
        rvTasks.setHasFixedSize(true);
        rvTasks.setLayoutManager(new LinearLayoutManager(getContext()));
        list = new ArrayList<ReminderEntry>();
        documents = new HashMap<>();
        adapter = new TaskViewAdapterEdit(getContext(), list);
        rvTasks.setAdapter(adapter);

        EventChangeListiner(root);

        adapter.setOnItemClickListener(new TaskViewAdapterEdit.OnItemClickListener() {
            @Override
            public void onItemClickDelete(int position) {
                Log.e("s", "position: " + position);
                Log.e("s", "list: " + list.toString());
                Log.e("s", "document list: " + documents.toString());

                db.collection("users").document("user1")
                        .collection("tasks").document(documents.get(list.get(position))).delete();
                documents.remove(list.get(position));
                list.remove(position);
                adapter.notifyItemRemoved(position);
            }

            @Override
            public void onItemClickEdit(int position) {
                String name = documents.get(list.get(position));
                Intent intent = new Intent(getContext(), ReminderActivity.class);
                intent.putExtra("name", name);
                Log.d("position", "called" + name);
//                intent.putExtra("reminderEntry", list.get(position));
                startActivity(intent);
            }
        });
//
        Button newReminderEntry = root.findViewById(R.id.SetReminder);

        newReminderEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ReminderActivity.class);
                startActivity(intent);
            }
        });

        SetupSorting(root);

    }

    private void EventChangeListiner(View root) {
        db.collection("users").document("user1").collection("tasks").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null){
                    Log.e("firestore error", error.getMessage());
                }

                for (DocumentChange dc : value.getDocumentChanges()) {
                    if (dc.getType() == DocumentChange.Type.ADDED){
                        ReminderEntry item = dc.getDocument().toObject(ReminderEntry.class);
                        list.add(item);
                        dc.getDocument().getId();
                        documents.put(item, dc.getDocument().getId());
                    }

                    if (dc.getType() == DocumentChange.Type.MODIFIED){
                        Log.e("M", "Modified item Losers");
                        ReminderEntry item = dc.getDocument().toObject(ReminderEntry.class);
                        // change list tiem and notify change data
                        list.remove(item);
                        dc.getDocument().getId();
                        documents.put(item, dc.getDocument().getId());
                    }

                    adapter.notifyDataSetChanged();
                }
            }
        });
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
                    Collections.sort(list, new Comparator<ReminderEntry>() {
                        @Override
                        public int compare(ReminderEntry t1, ReminderEntry t2) {
                            return t1.getLocation().compareTo(t2.getLocation());
                        }
                    });

                    adapter.notifyDataSetChanged();
                }else {
                    Collections.sort(list, new Comparator<ReminderEntry>() {
                        @Override
                        public int compare(ReminderEntry t1, ReminderEntry t2) {
                            return t1.getLocation().compareTo(t2.getLocation());
                        }
                    });
                    // sort by descending;
                    Collections.reverse(list);
                    adapter.notifyDataSetChanged();
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
}