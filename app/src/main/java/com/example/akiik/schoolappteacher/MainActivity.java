package com.example.akiik.schoolappteacher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText editTextName,editTextPemail,editTextAttendance,editTextTotal;
    Button buttonAdd;
    Spinner spinnerClass,spinnerSection;
    DatabaseReference databaseStudent;
    ListView listViewStudents;
    List<Student> studentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseStudent= FirebaseDatabase.getInstance().getReference("Students");
        editTextName = (EditText) findViewById(R.id.editTextname);
        editTextPemail=(EditText)findViewById(R.id.pemail);
        spinnerClass = (Spinner) findViewById(R.id.spinnerclass);//spinnersection
        spinnerSection = (Spinner) findViewById(R.id.spinnersection);
        editTextAttendance=(EditText)findViewById(R.id.editAttendance);                 //attendance-total
        editTextTotal=(EditText)findViewById(R.id.edittotaldays);


        buttonAdd = (Button) findViewById(R.id.buttonAddstudent);

        listViewStudents = (ListView) findViewById(R.id.listViewStudents);

        studentList = new ArrayList<>();
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStudent();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseStudent.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    studentList.clear();
                for (DataSnapshot studentSnapshot : dataSnapshot.getChildren()) {
                    Student student = studentSnapshot.getValue(Student.class);
                    studentList.add(student);

                }
                StudentList adapter=new StudentList(MainActivity.this, studentList);
                listViewStudents.setAdapter(adapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addStudent() {
        String name = editTextName.getText().toString().trim();
        String stuclass = spinnerClass.getSelectedItem().toString();
        String stusection=spinnerSection.getSelectedItem().toString();
        String pemail=editTextPemail.getText().toString().trim();
        int attendance= Integer.parseInt(editTextAttendance.getText().toString());//VVI parsing
        int total= Integer.parseInt(editTextTotal.getText().toString());
        if (!TextUtils.isEmpty(name)) {

            String id = databaseStudent.push().getKey();
            Student student = new Student(id, name, stuclass,stusection,pemail,attendance,total);
            databaseStudent.child(id).setValue(student);
            Toast.makeText(this, "Student added", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Enter a name", Toast.LENGTH_LONG).show();
        }
    }
}
