package com.example.prm392_proj.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.prm392_proj.R;
import com.example.prm392_proj.model.Instruction;
import java.util.ArrayList;
import java.util.List;

public class InstructionAdapter extends RecyclerView.Adapter<InstructionAdapter.instructionViewHolder> {
    private List<Instruction> instructionList = new ArrayList<>();

    @NonNull
    @Override
    public instructionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_procedure_item, parent, false);
        return new instructionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull instructionViewHolder holder, int position) {
        Instruction instruction = instructionList.get(position);
        holder.index.setText("Step " + instruction.getIndex());
        holder.description.setText(instruction.getDescription());
    }

    @Override
    public int getItemCount() {
        return instructionList.size();
    }

    public void setInstructionList(List<Instruction> instructions) {
        this.instructionList = instructions;
        notifyDataSetChanged();
    }

    public static class instructionViewHolder extends RecyclerView.ViewHolder {
        private TextView index;
        private TextView description;
        ConstraintLayout linearLineout;

        @SuppressLint("WrongViewCast")
        public instructionViewHolder(@NonNull View itemView) {
            super(itemView);
            index = itemView.findViewById(R.id.indexText);
            description = itemView.findViewById(R.id.descriptionText);
            linearLineout = itemView.findViewById(R.id.recyclerView);
        }
    }
}
