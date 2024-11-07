package com.example.prm392_proj.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_proj.R;
import com.example.prm392_proj.model.Notification;
import com.example.prm392_proj.activity.NotificationActivity;

import java.text.SimpleDateFormat;
import java.util.List;

public class NotificationActivityAdapter extends RecyclerView.Adapter<NotificationActivityAdapter.NotificationViewHolder> {

    private Context context;
    private List<Notification> notificationList;
    private NotificationActivity activity;

    public NotificationActivityAdapter(Context context, List<Notification> notificationList, NotificationActivity activity) {
        this.context = context;
        this.notificationList = notificationList;
        this.activity = activity;
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the layout for the individual notification items
        View view = LayoutInflater.from(context).inflate(R.layout.notification_adapter_layout, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {
        Notification notification = notificationList.get(position);

        // Set notification details
        holder.title.setText(notification.getTitle());
        holder.content.setText(notification.getContent());

        // Format date for display
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        holder.date.setText(dateFormat.format(notification.getDate()));

        // Set background color based on read status
        if (notification.isRead()) {
            holder.itemView.setBackgroundColor(Color.parseColor("#E0E0E0")); // Grey for read
            holder.markAsReadButton.setImageResource(R.drawable.ic_checkmark); // Checkmark for read
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE); // White for unread
            holder.markAsReadButton.setImageResource(R.drawable.ic_uncheckmark); // "X" for unread
        }

        // Set click listener to toggle read/unread status
        holder.markAsReadButton.setOnClickListener(v -> {
            // Toggle the read status
            boolean currentStatus = notification.isRead();
            notification.setRead(!currentStatus);  // Toggle the status
            Log.d("NotificationAdapter", "Notification " + notification.getTitle() + " isRead: " + notification.isRead());

            // Update the activity's notification status
            activity.updateNotificationStatus(notification);  // Notify the activity of the change

            // Update the adapter view for this specific item
            notifyItemChanged(position);  // Rebind the specific item at this position
        });
    }

    @Override
    public int getItemCount() {
        return notificationList.size(); // Return the total number of notifications
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView title, content, date;
        ImageButton markAsReadButton;

        public NotificationViewHolder(View itemView) {
            super(itemView);
            // Initialize the views from the layout
            title = itemView.findViewById(R.id.notifyTitletextView);
            content = itemView.findViewById(R.id.notifyContenttextView);
            date = itemView.findViewById(R.id.notifyDatetextView);
            markAsReadButton = itemView.findViewById(R.id.markAsReadButton);
        }
    }
}
