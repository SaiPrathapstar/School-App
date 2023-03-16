package com.example.streamsschool.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.streamsschool.adapters.classAdapter;
import com.example.streamsschool.adapters.classItem;
import com.example.streamsschool.databaseHelpers.SchoolDataBase;
import com.example.streamsschool.databinding.ActivityHomeBinding;
import com.example.streamsschool.interfaces.classRecyclerInterface;

import java.util.ArrayList;
import java.util.List;

public class ActivityHome extends AppCompatActivity implements classRecyclerInterface {

    ActivityHomeBinding activityHomeBinding;
    RecyclerView.Adapter adapter;
    List<classItem> classes;
    int positionF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityHomeBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(activityHomeBinding.getRoot());

        getData();
        setRecyclerView();
        addListeners();
    }

    private void setRecyclerView(){
        activityHomeBinding.classesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new classAdapter(classes, this);
        activityHomeBinding.classesRecyclerView.setAdapter(adapter);

    }
    private void addListeners(){
        activityHomeBinding.addSchool.setOnClickListener(view -> {

            AlertDialog.Builder popup = new AlertDialog.Builder(ActivityHome.this);
            popup.setTitle("Add Class Details");
            popup.setCancelable(true);

            LinearLayout l = new LinearLayout(this);
            l.setOrientation(LinearLayout.VERTICAL);
            final EditText year, branch, section;
            year = new EditText(this);
            branch = new EditText(this);
            section = new EditText(this);

            year.setHint("Enter Year (I/II/III/IV)");
            branch.setHint("Enter Which Branch");
            section.setHint("Enter Which Section");

            l.addView(year);
            l.addView(branch);
            l.addView(section);

            popup.setView(l);
            popup.setPositiveButton("Save", (dialogInterface, i) -> {
                SchoolDataBase schoolDataBase = new SchoolDataBase(getApplicationContext());

                if( schoolDataBase.insert(year.getText().toString(),branch.getText().toString(), section.getText().toString() ) )
                {
                    Toast.makeText(getApplicationContext(),"Inserted " , Toast.LENGTH_SHORT).show();
                    classItem ci = new classItem(year.getText().toString(),branch.getText().toString(), section.getText().toString() );
                    classes.add(ci);
                    updateData();
                }
                else
                    Toast.makeText(getApplicationContext(),"Failed to insert" , Toast.LENGTH_SHORT).show();

            });

            popup.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel());

            AlertDialog alertDialog = popup.create();
            alertDialog.show();


        });

    }

    private void getData(){
        SchoolDataBase schoolDataBase = new SchoolDataBase(getApplicationContext());
        Cursor res = schoolDataBase.getClasses();
        classes = new ArrayList<>();
        if(res.getCount() == 0)
            Toast.makeText(ActivityHome.this, "No data found", Toast.LENGTH_SHORT).show();
        else {
            while (res.moveToNext()) {
                classItem ci = new classItem( res.getString(1), res.getString(2),res.getString(3) );
                classes.add(ci);
            }
            res.close();
        }
    }

    public void updateData(){
        adapter.notifyItemInserted(classes.size());
    }

    @Override
    public void onItemClick(int position) {
        positionF = position;

        Intent intent = new Intent(ActivityHome.this, ClassActivity.class);
//        intent.putExtra("class_name", classes.get(position).getBranch() + classes.get(position).getYear()+ classes.get(position).getSection() );
        intent.putExtra("year",classes.get(position).getSection());
        intent.putExtra("branch", classes.get(position).getBranch() );
        intent.putExtra("section",classes.get(position).getYear() );
        startActivityForResult(intent,2);
//        startActivity(intent);
//        Toast.makeText(this, " " + classes.get(position).getClassName() , Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2){
            updateItemRemoved(positionF);
        }
    }

    public void updateItemRemoved(int pos){
        adapter.notifyItemRemoved(pos);
    }
}