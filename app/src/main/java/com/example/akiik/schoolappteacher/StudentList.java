package com.example.akiik.schoolappteacher;

import android.app.Activity;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class StudentList extends ArrayAdapter<Student> {
    private Activity context;
    private List<Student> studentList;

    public StudentList(Activity context, List<Student> studentList) {
        super(context,R.layout.list_layout, studentList);
        this.context =context;
        this.studentList = studentList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem=inflater.inflate(R.layout.list_layout,null,true);
        TextView textViewName=(TextView)listViewItem.findViewById(R.id.textViewName);
        TextView textViewClass=(TextView)listViewItem.findViewById(R.id.textViewClass);
        TextView textViewSection=(TextView)listViewItem.findViewById(R.id.textViewSection);
        //TextView textViewPemail=(TextView)listViewItem.findViewById(R.id.textViewAttendance) ;
        TextView textViewAttendance=(TextView)listViewItem.findViewById(R.id.textViewAttendance);

        Student student = studentList.get(position);

        textViewName.setText(student.getStudentName());
        textViewClass.setText(student.getStudentClass());
        textViewSection.setText(student.getStudentSection());
       // textViewPemail.setText(student.getPemail());
       textViewAttendance.setText(String.valueOf(student.getSutAttendance()));




        return listViewItem;
    }
}
