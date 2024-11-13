package com.example.fasttechassist;

import android.app.AlertDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class FacultyActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private FloatingActionButton fabAddTicket;
    private FacultyPagerAdapter facultyPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faculty_activity);

        // Initialize views
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        fabAddTicket = findViewById(R.id.fab_add_ticket);

        // Set up adapter and ViewPager
        facultyPagerAdapter = new FacultyPagerAdapter(this);
        viewPager.setAdapter(facultyPagerAdapter);

        // Attach TabLayout with ViewPager
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Main");
                    tab.setIcon(R.drawable.local_activity_24dp_e8eaed_fill0_wght400_grad0_opsz24);
                    break;
                case 1:
                    tab.setText("Processing");
                    tab.setIcon(R.drawable.notifications_unread_24dp_e8eaed_fill0_wght400_grad0_opsz24);
                    break;
                case 2:
                    tab.setText("Settings");
                    tab.setIcon(R.drawable.baseline_settings_24);
                    break;
            }
        }).attach();

        // FAB click action
        fabAddTicket.setOnClickListener(v -> showAddTicketDialog());
    }

    // Show dialog for adding a ticket
    private void showAddTicketDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Add Ticket")
                .setMessage("Are you sure you want to add a new ticket?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // Logic for adding ticket
                    showAlertDialog("Success", "Ticket added successfully!");
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    // Show general alert dialog
    private void showAlertDialog(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }
}
