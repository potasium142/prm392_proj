package com.example.prm392_proj.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_proj.R;
import com.example.prm392_proj.adapter.NotificationActivityAdapter;
import com.example.prm392_proj.model.Notification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {
    private Button allButton, readButton, unreadButton;
    private RecyclerView recyclerView;
    private NotificationActivityAdapter notificationAdapter;
    private List<Notification> notificationList;
    private List<Notification> filteredNotifications; // List to hold filtered notifications
    private String currentFilter = "all";  // Track the current filter state

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notification);

        // Adjust for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set up the back button
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        // Initialize buttons
        allButton = findViewById(R.id.all_button);
        readButton = findViewById(R.id.read_button);
        unreadButton = findViewById(R.id.unread_button);

        // Initialize RecyclerView and Adapter
        recyclerView = findViewById(R.id.instructionsRecyclerView);
        notificationList = new ArrayList<>();
        filteredNotifications = new ArrayList<>();
        notificationAdapter = new NotificationActivityAdapter(this, filteredNotifications, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(notificationAdapter);

        // Load temporary list of notifications
        loadNotifications();

        // Set click listeners for buttons
        allButton.setOnClickListener(v -> {
            resetButtonStyles(); // Reset all buttons' styles
            v.setBackgroundColor(Color.parseColor("#FFBB86FC")); // Highlight selected button
            ((Button) v).setTextColor(Color.WHITE); // Change text color to white
            currentFilter = "all"; // Set filter to "all"
            filterNotifications(); // Re-filter the list
        });

        readButton.setOnClickListener(v -> {
            resetButtonStyles(); // Reset all buttons' styles
            v.setBackgroundColor(Color.parseColor("#FFBB86FC")); // Highlight selected button
            ((Button) v).setTextColor(Color.WHITE); // Change text color to white
            currentFilter = "read"; // Set filter to "read"
            filterNotifications(); // Re-filter the list
        });

        unreadButton.setOnClickListener(v -> {
            resetButtonStyles(); // Reset all buttons' styles
            v.setBackgroundColor(Color.parseColor("#FFBB86FC")); // Highlight selected button
            ((Button) v).setTextColor(Color.WHITE); // Change text color to white
            currentFilter = "unread"; // Set filter to "unread"
            filterNotifications(); // Re-filter the list
        });

        // Select "All" button by default
        allButton.performClick();
    }

    private void resetButtonStyles() {
        allButton.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        allButton.setTextColor(getResources().getColor(android.R.color.black));

        readButton.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        readButton.setTextColor(getResources().getColor(android.R.color.black));

        unreadButton.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        unreadButton.setTextColor(getResources().getColor(android.R.color.black));
    }

    private void loadNotifications() {
        // This is a temporary list for testing. You will likely fetch from your own data source.
        notificationList.add(new Notification(1, 1, "Sucessfully created account", "Welcome to FOOD RECIPE", new Date(), false, 1001));
        notificationList.add(new Notification(2, 1, "News", "Follow creator new recipe will appear here", new Date(), false, 1002));
        notificationList.add(new Notification(3, 1, "New Food from admin just posted!", "Mediterranean Baked Cod with Lemone", new Date(), false, 1003));
        notificationList.add(new Notification(4, 2, "New Food from admin2 just posted!", "Pro Mediterranean Baked Cod with Lemone", new Date(), false, 1004));
        // Add more notifications here

        // Initially load all notifications
        filterNotifications();
    }

    // Filter notifications based on read/unread status
    private void filterNotifications() {
        filteredNotifications.clear();

        if ("all".equals(currentFilter)) {
            filteredNotifications.addAll(notificationList); // Show all notifications
        } else if ("read".equals(currentFilter)) {
            for (Notification notification : notificationList) {
                if (notification.isRead()) {
                    filteredNotifications.add(notification); // Add read notifications
                }
            }
        } else if ("unread".equals(currentFilter)) {
            for (Notification notification : notificationList) {
                if (!notification.isRead()) {
                    filteredNotifications.add(notification); // Add unread notifications
                }
            }
        }

        // Notify the adapter about the changes to the filtered list
        notificationAdapter.notifyDataSetChanged();
    }

    // Method to update notification status and its background
    public void updateNotificationStatus(Notification notification) {
        // Toggle the read status
        boolean newStatus = !notification.isRead();
        notification.setRead(true); // Update notification's read status

        // Update the status locally in the list
        int index = notificationList.indexOf(notification);
        if (index >= 0) {
            notificationList.set(index, notification); // Update the notification in the list

            // Filter the list again to update the displayed notifications based on the new status
            filterNotifications();

            // Notify the adapter of the change (specific item change or full list change)
            notificationAdapter.notifyItemChanged(index); // Only notify the changed item
        }
    }
}
