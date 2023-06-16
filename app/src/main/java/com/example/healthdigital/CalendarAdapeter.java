package com.example.healthdigital;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;

public class CalendarAdapeter extends RecyclerView.Adapter<CalendarAdapeter.MyViewHolderCalendar> {

    Context context;
    ArrayList<ReminderEntry> reminderEntryArrayList;


    private static CalendarAdapeter.OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClickDelete(int position);
        void onItemClickEdit(int position);
    }

    public void setOnItemClickListener(CalendarAdapeter.OnItemClickListener clickListener){
        listener = clickListener;
    }
    public CalendarAdapeter(Context context, ArrayList<ReminderEntry> reminderEntryArrayList) {
        this.context = context;
        this.reminderEntryArrayList = reminderEntryArrayList;
    }

    @NonNull
    @Override
    public CalendarAdapeter.MyViewHolderCalendar onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.calendar_task, parent, false);

        return new MyViewHolderCalendar(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarAdapeter.MyViewHolderCalendar holder, int position) {

        ReminderEntry reminderEntry = reminderEntryArrayList.get(position);

        holder.create.setText(reminderEntry.toString());
        Date date = reminderEntry.getDate();   // given date
        Calendar calendar = Calendar.getInstance(); // creates a new calendar instance
        calendar.setTime(date);   // assigns calendar to given date
        int hour  = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
        int minutes = calendar.get(Calendar.MINUTE);
        String timeSting = hour + ":" + minutes;
        holder.time.setText(timeSting);

    }

    @Override
    public int getItemCount() {
        return reminderEntryArrayList.size();
    }

    public  static  class MyViewHolderCalendar extends RecyclerView.ViewHolder{

        TextView create;

        TextView time;

        public MyViewHolderCalendar(@NonNull View itemView) {
            super(itemView);

            create = itemView.findViewById(R.id.item_task_list_text);
            time = itemView.findViewById(R.id.timeContainer);
//            delete = itemView.findViewById(R.id.delete_id);
//            edit = itemView.findViewById(R.id.edit_id);
//
//            edit.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    listener.onItemClickEdit(getAdapterPosition());
//                }
//            });
//
//            delete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    listener.onItemClickDelete(getAdapterPosition());
//                }
//            });
        }
    }
}
