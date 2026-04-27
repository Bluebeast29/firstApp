package com.example.firstapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
    private List<Student> studentList;

    public StudentAdapter(List<Student> studentList) {
        this.studentList = studentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.tvInfo.setText(String.format("%s (%s)", student.getName(), student.getEnroll()));
        holder.cbPresent.setChecked(student.isPresent());
        
        // High-polish UX: Dim the row if absent
        holder.itemView.setAlpha(student.isPresent() ? 1.0f : 0.6f);
        
        holder.itemView.setOnClickListener(v -> {
            student.setPresent(!student.isPresent());
            notifyItemChanged(position);
        });
        
        holder.cbPresent.setOnClickListener(v -> {
            student.setPresent(((CheckBox)v).isChecked());
            notifyItemChanged(position);
        });
    }

    public void selectAll(boolean present) {
        for (Student s : studentList) {
            s.setPresent(present);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvInfo;
        CheckBox cbPresent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvInfo = itemView.findViewById(R.id.tv_student_info);
            cbPresent = itemView.findViewById(R.id.cb_present);
        }
    }
}