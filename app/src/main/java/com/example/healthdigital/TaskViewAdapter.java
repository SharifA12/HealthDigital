package com.example.healthdigital;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class TaskViewAdapter extends RecyclerView.Adapter<TaskViewAdapter.ViewHolder> {

    private List<ReminderEntry> localDataSet;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClickDelete(int position);
        void onItemClickEdit(int position);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener){
        listener = clickListener;
    }

    /**
     * Provide a reference to the type of views that you are using
     * This template comes with a TextView
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final ImageView delete;

        private final ImageView edit;

        public ViewHolder(View view, OnItemClickListener listener) {
            super(view);

            textView = view.findViewById(R.id.item_task_list_text); //error here should be expected, this is a template
            delete = view.findViewById(R.id.delete_id);
            edit = view.findViewById(R.id.edit_id);

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

        public TextView getTextView() {
            return textView;
        }
        public ImageView getButton() {
            return delete;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     *                by RecyclerView.
     */
    public TaskViewAdapter(List<ReminderEntry> dataSet, Context context) {
        localDataSet = dataSet;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_task_list, viewGroup, false); //error here should be expected, this is a template

        return new ViewHolder(view, listener);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTextView().setText(createTaskString(localDataSet.get(position)));
    }

    public String createTaskString(ReminderEntry reminderEntry){
        // create the string based on reminderEntry
        return reminderEntry.toString();
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}