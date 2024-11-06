package com.example.prm392_proj.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.prm392_proj.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class FilterBottomSheetDialog extends BottomSheetDialogFragment {

    // Define the interface
    public interface FilterListener {
        void onFilterApplied(String filterChoice, String starRating); // Modified to accept both filter and rating
    }

    private FilterListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (FilterListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement FilterListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_filter_layout, container, false);

        // Get the radio groups
        RadioGroup filterTypeGroup = view.findViewById(R.id.filterTypeGroup);
        RadioGroup starRatingGroup = view.findViewById(R.id.starRatingGroup);

        // Set up listeners to capture the selected radio buttons
        Button applyButton = view.findViewById(R.id.buttonFilter);
        applyButton.setOnClickListener(v -> {
            int selectedFilterId = filterTypeGroup.getCheckedRadioButtonId();
            int selectedStarId = starRatingGroup.getCheckedRadioButtonId();

            String filterChoice = "";
            String starRating = "";

            // Get selected filter type
            if (selectedFilterId != -1) {
                RadioButton selectedFilter = view.findViewById(selectedFilterId);
                filterChoice = selectedFilter.getText().toString();
            }

            // Get selected star rating
            if (selectedStarId != -1) {
                RadioButton selectedStar = view.findViewById(selectedStarId);
                starRating = selectedStar.getText().toString();
            }

            // Apply filter
            if (listener != null) {
                listener.onFilterApplied(filterChoice, starRating); // Call the method with both parameters
            }
            dismiss();
        });

        return view;
    }
}
