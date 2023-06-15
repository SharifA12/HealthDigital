package com.example.healthdigital;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskViewAdapterEdit extends RecyclerView.Adapter<TaskViewAdapterEdit.MyViewHolder> {

    Context context;
    ArrayList<ReminderEntry> reminderEntryArrayList;

    static ImageView delete;

    static ImageView edit;

    private static TaskViewAdapterEdit.OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClickDelete(int position);
        void onItemClickEdit(int position);
    }

    public void setOnItemClickListener(TaskViewAdapterEdit.OnItemClickListener clickListener){
        listener = clickListener;
    }
    public TaskViewAdapterEdit(Context context, ArrayList<ReminderEntry> reminderEntryArrayList) {
        this.context = context;
        this.reminderEntryArrayList = reminderEntryArrayList;
    }

    @NonNull
    @Override
    public TaskViewAdapterEdit.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_task_list, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewAdapterEdit.MyViewHolder holder, int position) {

        ReminderEntry reminderEntry = reminderEntryArrayList.get(position);

        holder.create.setText(reminderEntry.toString());

    }

    @Override
    public int getItemCount() {
        return reminderEntryArrayList.size();
    }

    public  static  class MyViewHolder extends RecyclerView.ViewHolder{

        TextView create;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            create = itemView.findViewById(R.id.item_task_list_text);
            delete = itemView.findViewById(R.id.delete_id);
            edit = itemView.findViewById(R.id.edit_id);

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClickEdit(getAdapterPosition());
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClickDelete(getAdapterPosition());
                }
            });
        }
    }
}
