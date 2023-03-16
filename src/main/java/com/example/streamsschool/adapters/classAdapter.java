package com.example.streamsschool.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.streamsschool.R;
import com.example.streamsschool.interfaces.classRecyclerInterface;

import java.util.List;

public class classAdapter extends RecyclerView.Adapter<classAdapter.holder> {

    private final classRecyclerInterface recyclerInterface;
    List<classItem> classes;

    public classAdapter(List<classItem> classes, classRecyclerInterface classRecyclerInterface) {
        this.classes = classes;
        this.recyclerInterface = classRecyclerInterface;
    }

    public static class holder extends RecyclerView.ViewHolder{

        public TextView section, branch, year;

        public holder(@NonNull View itemView, classRecyclerInterface recyclerInterface) {
            super(itemView);
            section = itemView.findViewById(R.id.class_name);
            branch = itemView.findViewById(R.id.presenties);
            year = itemView.findViewById(R.id.total);
            itemView.setOnClickListener(view -> {
                if(recyclerInterface != null){
                    int pos = getAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION){
                        recyclerInterface.onItemClick(pos);
                    }
                }
            });
        }
    }


    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.class_item, parent, false);

        return new holder(view, recyclerInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {

       classItem ci =classes.get(position);
       String c = ci.getSection() + " " +  ci.getBranch() + " " + ci.getYear();
       holder.section.setText(c);
//       holder.branch.setText(ci.getBranch());
//       holder.year.setText(ci.getYear());
    }

    @Override
    public int getItemCount() {
        return classes.size();
    }

}
