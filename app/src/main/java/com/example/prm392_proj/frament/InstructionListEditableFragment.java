package com.example.prm392_proj.frament;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_proj.R;
import com.example.prm392_proj.adapter.InstructionListEditableAdapter;
import com.example.prm392_proj.dialog.InputDialog;
import com.example.prm392_proj.model.Instruction;
import com.example.prm392_proj.util.InputValidation;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class InstructionListEditableFragment extends Fragment {
    List<Instruction> instructions;
    InstructionListEditableAdapter adapter;
    TextView totalInstruction;

    public InstructionListEditableFragment(List<Instruction> instructions) {
        this.instructions = instructions;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_instruction_list_editable, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.instructionsRecyclerView);
        adapter = new InstructionListEditableAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setInstructions(instructions);

        totalInstruction = view.findViewById(R.id.totalInstruction);
        totalInstruction.setText(String.valueOf(instructions.size()));


        Button addMoreButton = view.findViewById(R.id.addMoreButton);
        addMoreButton.setOnClickListener(v -> onAddButtonClick());
    }

    public void onEditButtonClick(Instruction instruction) {
        InputDialog inputDialog = new InputDialog(this.getContext(), instruction.getDescription());
        inputDialog.setOnEnterListener((input) -> {
            instruction.setDescription(input);
            adapter.notifyDataSetChanged();

            var sharedPreferences = this.getContext().getSharedPreferences("edit_recipe", Context.MODE_PRIVATE);
            sharedPreferences.edit().putString("changed", "changed").apply();
        });

        InputValidation inputValidation = new InputValidation("Name");
        inputValidation.isRequired = true;

        inputDialog.setValidation(inputValidation);

        inputDialog.show();
    }

    public void onDeleteButtonClick(int position) {
        Instruction instruction = instructions.get(position);
        new AlertDialog.Builder(this.getContext()).setTitle("Delete instruction")
                .setMessage("Are you sure you want to delete this instruction?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    instructions.remove(instruction);
                    totalInstruction.setText(String.valueOf(instructions.size()));
                    adapter.notifyDataSetChanged();

                    Snackbar.make(getView(), "Instruction deleted", Snackbar.LENGTH_LONG)
                            .setAction("Undo", v -> {
                                instructions.add(position, instruction);
                                totalInstruction.setText(String.valueOf(instructions.size()));
                                adapter.notifyDataSetChanged();
                            })
                            .setDuration(5000)
                            .show();

                    var sharedPreferences = this.getContext().getSharedPreferences("edit_recipe", Context.MODE_PRIVATE);
                    sharedPreferences.edit().putString("changed", "changed").apply();
                })
                .setNegativeButton("No", null)
                .show();

    }

    int getMaxIndex(List<Instruction> instructions) {
        int maxIndex = 0;
        for (Instruction instruction : instructions) {
            if (instruction.getIndex() > maxIndex) {
                maxIndex = instruction.getIndex();
            }
        }
        return maxIndex;
    }

    public void onAddButtonClick() {
        Instruction instruction = new Instruction();
        InputDialog inputDialog = new InputDialog(this.getContext(), instruction.getDescription());
        inputDialog.setOnEnterListener((input) -> {
            instruction.setDescription(input);
            instruction.setIndex(getMaxIndex(instructions) + 1);
            instructions.add(instruction);
            totalInstruction.setText(String.valueOf(instructions.size()));
            adapter.notifyDataSetChanged();

            var sharedPreferences = this.getContext().getSharedPreferences("edit_recipe", Context.MODE_PRIVATE);
            sharedPreferences.edit().putString("changed", "changed").apply();
        });

        InputValidation inputValidation = new InputValidation("Instruction");
        inputValidation.isRequired = true;

        inputDialog.setValidation(inputValidation);

        inputDialog.show();
    }
}