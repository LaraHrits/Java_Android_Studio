package com.example.lw_5_1;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText idEt;
    private EditText firstNameEt;
    private EditText lastNameEt;
    private EditText emailEt;
    private EditText addressEt;
    private EditText phoneEt;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);
        setupViews();
    }

    private void setupViews() {
        idEt = findViewById(R.id.idEt);
        firstNameEt = findViewById(R.id.firstNameEt);
        lastNameEt = findViewById(R.id.lastNameEt);
        emailEt = findViewById(R.id.emailEt);
        addressEt = findViewById(R.id.addressEt);
        phoneEt = findViewById(R.id.phoneEt);

        Button addBtn = findViewById(R.id.addBtn);
        Button updateBtn = findViewById(R.id.updateBtn);
        Button readBtn = findViewById(R.id.readBtn);
        Button clearBtn = findViewById(R.id.clearBtn);
        Button deleteBtn = findViewById(R.id.deleteBtn);

        addBtn.setOnClickListener(v -> addUser());
        updateBtn.setOnClickListener(v -> updateUser());
        deleteBtn.setOnClickListener(v -> deleteUser());
        clearBtn.setOnClickListener(v -> clearDB());
        readBtn.setOnClickListener(v -> exportAllUsersToFile());

        RadioGroup radioGroup = findViewById(R.id.sortRadioGroup);
        Button sortButton = findViewById(R.id.sortButton);

        sortButton.setOnClickListener(v -> {
            int selectedId = radioGroup.getCheckedRadioButtonId();

            if (selectedId != -1) {
                RadioButton radioButton = findViewById(selectedId);
                String selectedField = radioButton.getText().toString();

                sortRecords(selectedField);
            } else {
            }
        });
    }

    private void exportAllUsersToFile() {
        dbHelper.saveAllToSdCard(dbHelper.getAllUsers());
        showMessage(R.string.saved_to_card);
    }

    private void clearDB() {
        dbHelper.deleteAll();
        showMessage(R.string.db_was_cleared);
    }

    private void deleteUser() {
        if (!idEt.getText().toString().isEmpty()) {
            int id = Integer.parseInt(idEt.getText().toString());
            dbHelper.deleteUser(id);
            clearAllFields();
            showMessage(R.string.user_deleted);
        } else {
            showMessage(R.string.enter_id);
        }
    }

    private void updateUser() {
        String firstName = firstNameEt.getText().toString();
        String lastName = lastNameEt.getText().toString();
        String email = emailEt.getText().toString();
        String address = addressEt.getText().toString();
        String phone = phoneEt.getText().toString();

        if (!idEt.getText().toString().isEmpty() &&
                !firstName.isEmpty() &&
                !lastName.isEmpty() &&
                !email.isEmpty() &&
                !address.isEmpty() &&
                !phone.isEmpty()
        ) {
            int id = Integer.parseInt(idEt.getText().toString());
            dbHelper.updateUser(id, firstName, lastName, email, address, phone);
            clearAllFields();
            showMessage(R.string.user_updated);
        } else {
            showMessage(R.string.empty_fields);
        }
    }

    private void addUser() {
        String firstName = firstNameEt.getText().toString();
        String lastName = lastNameEt.getText().toString();
        String email = emailEt.getText().toString();
        String address = addressEt.getText().toString();
        String phone = phoneEt.getText().toString();

        if (!firstName.isEmpty() &&
                !lastName.isEmpty() &&
                !email.isEmpty() &&
                !address.isEmpty() &&
                !phone.isEmpty()
        ) {
            dbHelper.insertUser(firstName, lastName, email, address, phone);
            clearAllFields();
            showMessage(R.string.user_added);
        } else {
            showMessage(R.string.empty_fields);
        }
    }

    private void clearAllFields() {
        idEt.getText().clear();
        firstNameEt.getText().clear();
        lastNameEt.getText().clear();
        emailEt.getText().clear();
        addressEt.getText().clear();
        phoneEt.getText().clear();
    }

    private void showMessage(int stringId) {
        Toast.makeText(this, getString(stringId), Toast.LENGTH_LONG).show();
    }

    private void sortRecords(String field) {

        List<User> userList = dbHelper.getAllUsers();

        switch (field) {
            case "Name":
                Collections.sort(userList, new Comparator<User>() {
                    @Override
                    public int compare(User u1, User u2) {
                        return u1.getFirstName().compareToIgnoreCase(u2.getFirstName());
                    }
                });
                break;
            case "LastName":
                Collections.sort(userList, new Comparator<User>() {
                    @Override
                    public int compare(User u1, User u2) {
                        return u1.getSecondName().compareToIgnoreCase(u2.getSecondName());
                    }
                });
                break;
            case "Email":
                Collections.sort(userList, new Comparator<User>() {
                    @Override
                    public int compare(User u1, User u2) {
                        return u1.getEmail().compareToIgnoreCase(u2.getEmail());
                    }
                });
                break;
            default:
                break;

        }
    }
}