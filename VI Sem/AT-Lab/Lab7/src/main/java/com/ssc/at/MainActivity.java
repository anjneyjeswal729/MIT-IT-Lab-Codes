package com.ssc.at;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText etRollNumber, etName, etMarks;
    private Button btnAdd, btnView;
    private ListView listViewStudents;
    private DatabaseHelper dbHelper;
    private List<Map<String, String>> studentList;
    private SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize database helper
        dbHelper = new DatabaseHelper(this);

        // Initialize UI components
        etRollNumber = findViewById(R.id.etRollNumber);
        etName = findViewById(R.id.etName);
        etMarks = findViewById(R.id.etMarks);
        btnAdd = findViewById(R.id.btnAdd);
        btnView = findViewById(R.id.btnView);
        listViewStudents = findViewById(R.id.listViewStudents);

        // Set up adapter for ListView
        studentList = new ArrayList<>();
        adapter = new SimpleAdapter(
                this,
                studentList,
                R.layout.student_list_item,
                new String[]{"rollNumber", "name", "marks"},
                new int[]{R.id.tvRollNumber, R.id.tvName, R.id.tvMarks}
        );
        listViewStudents.setAdapter(adapter);

        // Add button click listener
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStudent();
            }
        });

        // View button click listener
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadStudents();
            }
        });

        // Set item click listener for ListView - for update/delete operations
        listViewStudents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showActionDialog(position);
            }
        });

        // Load students when app starts
        loadStudents();
    }

    private void addStudent() {
        String rollNumber = etRollNumber.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String marks = etMarks.getText().toString().trim();

        // Validate inputs
        if (rollNumber.isEmpty() || name.isEmpty() || marks.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if roll number already exists
        if (dbHelper.checkRollNumberExists(rollNumber)) {
            Toast.makeText(this, "Roll Number already exists!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Add student to database
        boolean isInserted = dbHelper.insertStudent(rollNumber, name, marks);
        if (isInserted) {
            Toast.makeText(this, "Student added successfully!", Toast.LENGTH_SHORT).show();
            clearInputFields();
            loadStudents();
        } else {
            Toast.makeText(this, "Failed to add student", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadStudents() {
        studentList.clear();
        ArrayList<HashMap<String, String>> dbStudentList = dbHelper.getAllStudents();
        studentList.addAll(dbStudentList);
        adapter.notifyDataSetChanged();

        if (studentList.isEmpty()) {
            Toast.makeText(this, "No students found", Toast.LENGTH_SHORT).show();
        }
    }

    private void showActionDialog(final int position) {
        final Map<String, String> selectedStudent = studentList.get(position);
        final String rollNumber = selectedStudent.get("rollNumber");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Action");
        builder.setItems(new String[]{"Update", "Delete"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    // Update
                    showUpdateDialog(selectedStudent);
                } else {
                    // Delete
                    showDeleteConfirmationDialog(rollNumber);
                }
            }
        });
        builder.show();
    }

    private void showUpdateDialog(final Map<String, String> student) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_update_student, null);
        builder.setView(dialogView);

        final EditText dialogEtName = dialogView.findViewById(R.id.dialogEtName);
        final EditText dialogEtMarks = dialogView.findViewById(R.id.dialogEtMarks);
        final String rollNumber = student.get("rollNumber");

        // Pre-fill with current values
        dialogEtName.setText(student.get("name"));
        dialogEtMarks.setText(student.get("marks"));

        builder.setTitle("Update Student");
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newName = dialogEtName.getText().toString().trim();
                String newMarks = dialogEtMarks.getText().toString().trim();

                if (newName.isEmpty() || newMarks.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean isUpdated = dbHelper.updateStudent(rollNumber, newName, newMarks);
                if (isUpdated) {
                    Toast.makeText(MainActivity.this, "Student updated successfully!", Toast.LENGTH_SHORT).show();
                    loadStudents();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to update student", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void showDeleteConfirmationDialog(final String rollNumber) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Deletion");
        builder.setMessage("Are you sure you want to delete this student?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean isDeleted = dbHelper.deleteStudent(rollNumber);
                if (isDeleted) {
                    Toast.makeText(MainActivity.this, "Student deleted successfully!", Toast.LENGTH_SHORT).show();
                    loadStudents();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to delete student", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }

    private void clearInputFields() {
        etRollNumber.setText("");
        etName.setText("");
        etMarks.setText("");
        etRollNumber.requestFocus();
    }
}