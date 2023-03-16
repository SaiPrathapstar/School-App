package com.example.streamsschool.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.streamsschool.R;
import com.example.streamsschool.adapters.StudentAdapter;
import com.example.streamsschool.adapters.studentItem;
import com.example.streamsschool.databaseHelpers.ClassDatabase;
import com.example.streamsschool.databaseHelpers.SchoolDataBase;
import com.example.streamsschool.databinding.ActivityClassBinding;
import com.example.streamsschool.interfaces.studentRecyclerInterface;

import java.util.ArrayList;
import java.util.List;

public class ClassActivity extends AppCompatActivity implements studentRecyclerInterface {

    String className, className2, year, branch, section;
    studentItem s;
    int positionF;
    ActivityClassBinding activityClassBinding;
    RecyclerView.Adapter adapter;
    List<studentItem> students;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityClassBinding = ActivityClassBinding.inflate(getLayoutInflater());
        setContentView(activityClassBinding.getRoot());
        year = getIntent().getStringExtra("year");
        branch = getIntent().getStringExtra("branch");
        section = getIntent().getStringExtra("section");
        className2 = year + " "  + branch + " " + section;
        className = branch+section+year;

        activityClassBinding.tvClass.setText(className2);
        getData();
        setRecyclerView();
        addListener();

    }

    private void setRecyclerView() {
        activityClassBinding.studentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StudentAdapter(students, this);
        activityClassBinding.studentsRecyclerView.setAdapter(adapter);
    }

    private void addListener() {
        activityClassBinding.addStudent.setOnClickListener(view -> {

            AlertDialog.Builder popup = new AlertDialog.Builder(ClassActivity.this);
            popup.setTitle("Add Student Details");
            popup.setCancelable(true);

            LinearLayout l = new LinearLayout(this);
            l.setOrientation(LinearLayout.VERTICAL);
            final EditText roll, name, email, phone, address;
            roll = new EditText(this);
            name = new EditText(this);
            email = new EditText(this);
            phone = new EditText(this);
            address = new EditText(this);

            roll.setHint("Roll Number");
            name.setHint("Name");
            email.setHint("Email");
            phone.setHint("Phone Number");
            address.setHint("Address");

            l.addView(roll);
            l.addView(name);
            l.addView(email);
            l.addView(phone);
            l.addView(address);

            popup.setView(l);
            popup.setPositiveButton("Save", (dialogInterface, i) -> {
                ClassDatabase classDatabase = new ClassDatabase(getApplicationContext(), className);

                if (classDatabase.insert(roll.getText().toString(), name.getText().toString(), email.getText().toString(), phone.getText().toString(), address.getText().toString(), "P")) {
                    Toast.makeText(getApplicationContext(), "Inserted ", Toast.LENGTH_SHORT).show();
                    studentItem si = new studentItem(roll.getText().toString(), name.getText().toString(), email.getText().toString(), phone.getText().toString(), address.getText().toString(), "P");
                    students.add(si);
                    updateDataInserted();
                } else
                    Toast.makeText(getApplicationContext(), "Failed to insert", Toast.LENGTH_SHORT).show();
            });

            popup.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel());

            AlertDialog alertDialog = popup.create();
            alertDialog.show();
        });

        activityClassBinding.moreClass.setOnClickListener(view -> {
            PopupMenu p = new PopupMenu(ClassActivity.this, activityClassBinding.moreClass);
            p.getMenuInflater().inflate(R.menu.more_class, p.getMenu());
            p.setOnMenuItemClickListener(menuItem -> {
                SchoolDataBase s = new SchoolDataBase(getApplicationContext());
                s.deleteClass(year,branch,section);

                Intent intent =  new Intent();
                setResult(2, intent);
                ClassActivity.this.finish();
                return true;
            });
            p.show();
        });
    }


    private void getData() {
        ClassDatabase classDatabase = new ClassDatabase(getApplicationContext(), className);
        Cursor res = classDatabase.getStudents();
        students = new ArrayList<>();
        if (res.getCount() == 0)
            Toast.makeText(ClassActivity.this, "No data found", Toast.LENGTH_SHORT).show();
        else {
            while (res.moveToNext()) {
                studentItem si = new studentItem(res.getString(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5), res.getString(6));
                students.add(si);
            }
            res.close();
        }
    }


    @Override
    public void onItemClick(int position) {
        positionF = position;
        AlertDialog.Builder popup = new AlertDialog.Builder(ClassActivity.this);
        popup.setTitle("Student Details");
        popup.setCancelable(true);
        s = students.get(position);
        LinearLayout l = new LinearLayout(this);
        l.setOrientation(LinearLayout.VERTICAL);
        final TextView roll, name, email, phone, address;
        roll = new TextView(this);
        email = new TextView(this);
        phone = new TextView(this);
        address = new TextView(this);
        name = new TextView(this);
        final Button editButton = new Button(this);
//        editButton.setBackgroundResource(R.drawable.edit_button);
        editButton.setText(R.string.edit);


        roll.setText(s.getRoll());
        email.setText(s.getEmail());
        phone.setText(s.getPhone());
        address.setText(s.getAddress());
        name.setText(s.getName());

        l.addView(roll);
        l.addView(name);
        l.addView(email);
        l.addView(phone);
        l.addView(address);
        l.addView(editButton);
        popup.setView(l);

        AlertDialog alertDialog = popup.create();
        alertDialog.show();

        editButton.setOnClickListener(view -> {
            alertDialog.dismiss();
            AlertDialog.Builder popup1 = new AlertDialog.Builder(ClassActivity.this);
            popup.setTitle("Roll No : " + s.getRoll());
            popup.setCancelable(true);

            LinearLayout l1 = new LinearLayout(this);
            l1.setOrientation(LinearLayout.VERTICAL);
            final EditText name1, email1, phone1, address1;
            name1 = new EditText(this);
            email1 = new EditText(this);
            phone1 = new EditText(this);
            address1 = new EditText(this);

            name1.setHint("NAME");
            email1.setHint("EMAIL");
            phone1.setHint("Phone");
            address1.setHint("Address");

            l1.addView(name1);
            l1.addView(email1);
            l1.addView(phone1);
            l1.addView(address1);

            popup1.setView(l1);
            popup1.setPositiveButton("Save", (dialogInterface, i) -> {
                ClassDatabase classDatabase = new ClassDatabase(getApplicationContext(), className);
                String n, e, p, a;
                n = name1.getText().toString();
                e = email1.getText().toString();
                p = phone1.getText().toString();
                a = address1.getText().toString();

                if (n.equals(""))
                    n = s.getName();
                if (e.equals(""))
                    e = s.getEmail();
                if (p.equals(""))
                    p = s.getPhone();
                if (a.equals(""))
                    a = s.getAddress();
                classDatabase.update(className, s.getRoll(), n, e, p, a, "P");
                updateData(position, n, e, p, a, "P");

            });

            popup1.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel());

            AlertDialog alertDialog1 = popup1.create();
            alertDialog1.show();
        });
    }

    public void updateDataInserted() {
        adapter.notifyItemInserted(students.size());
    }

    public void updateData(int pos, String n, String e, String p, String a, String x) {
        s.setName(n);
        s.setAddress(a);
        s.setEmail(e);
        s.setPhone(p);
        s.setPresent(x);
        adapter.notifyItemChanged(pos);
    }

    public void updateItemRemoved(int pos){
        adapter.notifyItemRemoved(pos);
    }
}