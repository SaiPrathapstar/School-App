package com.example.streamsschool.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.streamsschool.R;
import com.example.streamsschool.interfaces.studentRecyclerInterface;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.student_holder> {

    private final studentRecyclerInterface recyclerInterface;
    List<studentItem> students;

    public StudentAdapter(List<studentItem> students, studentRecyclerInterface recyclerInterface) {
        this.recyclerInterface = recyclerInterface;
        this.students = students;
    }

    public static class student_holder extends RecyclerView.ViewHolder {
        public TextView studentName, phoneNumber;

        public student_holder(@NonNull View itemView, studentRecyclerInterface recyclerInterface) {
            super(itemView);
            studentName = itemView.findViewById(R.id.student_name);
            phoneNumber = itemView.findViewById(R.id.number);
            itemView.setOnClickListener(view -> {
                if (recyclerInterface != null) {
                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {
                        recyclerInterface.onItemClick(pos);
                    }
                }
            });

        }
    }

    @NonNull
    @Override
    public student_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_item, parent, false);
        return new student_holder(view, recyclerInterface);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull student_holder holder, int position) {
        studentItem si = students.get(position);
        holder.studentName.setText("Name :" + si.getName());
        holder.phoneNumber.setText("Roll : " + si.getRoll());
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

}
