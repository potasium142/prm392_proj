package com.example.prm392_proj.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_proj.R;
import com.example.prm392_proj.frament.InstructionListEditableFragment;
import com.example.prm392_proj.model.Instruction;

import java.util.ArrayList;
import java.util.List;

public class InstructionListEditableAdapter extends RecyclerView.Adapter<InstructionListEditableAdapter.ViewHolder> {
    private InstructionListEditableFragment instructionListEditableFragment;
    private List<Instruction> instructions = new ArrayList<>();

    public InstructionListEditableAdapter() {}

    public InstructionListEditableAdapter(InstructionListEditableFragment instructionListEditableFragment) {
        this.instructionListEditableFragment = instructionListEditableFragment;
    }

    public void setInstructions(List<Instruction> instructions) {
        this.instructions = instructions;
        notifyDataSetChanged();
    }

    @Override
    public InstructionListEditableAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.instruction_item_editable, parent, false);
        return new InstructionListEditableAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Instruction current = instructions.get(position);
        holder.description.setText(current.getDescription());
        holder.editButton.setOnClickListener(v -> {
            instructionListEditableFragment.onEditButtonClick(current);
        });
        holder.deleteButton.setOnClickListener(v -> {
            instructionListEditableFragment.onDeleteButtonClick(position);
        });
        holder.stepNumber.setText(String.valueOf(position + 1));
    }

    @Override
    public int getItemCount() {
        return instructions.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView description, stepNumber;
        ImageView editButton, deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.description);
            stepNumber = itemView.findViewById(R.id.stepNumber);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
