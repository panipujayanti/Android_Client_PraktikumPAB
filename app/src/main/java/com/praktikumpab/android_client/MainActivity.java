package com.praktikumpab.android_client;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter(userList);
        recyclerView.setAdapter(userAdapter);

        findViewById(R.id.button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddUserDialog();
            }
        });

        fetchUsers();
    }

    private void showAddUserDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add User");
        View view = getLayoutInflater().inflate(R.layout.dialog_add_user, null);

        final EditText editTextNIM = view.findViewById(R.id.editTextNIM);
        final EditText editTextName = view.findViewById(R.id.editTextName);
        final EditText editTextProgstud = view.findViewById(R.id.editTextProgstud);
        final EditText editTextEmail = view.findViewById(R.id.editTextEmail);
        final EditText editTextAlamat = view.findViewById(R.id.editTextAlamat);
        final EditText editTextTanggalLahir = view.findViewById(R.id.editTextTanggalLahir);
        final RadioGroup radioGroupJenisKelamin = view.findViewById(R.id.radioGroupJenisKelamin);

        editTextTanggalLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(editTextTanggalLahir);
            }
        });

        builder.setView(view);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String NIM = editTextNIM.getText().toString();
                String name = editTextName.getText().toString();
                String progstud = editTextProgstud.getText().toString();
                String email = editTextEmail.getText().toString();
                String alamat = editTextAlamat.getText().toString();
                String tanggalLahir = editTextTanggalLahir.getText().toString();
                int selectedId = radioGroupJenisKelamin.getCheckedRadioButtonId();
                // Check if any field is empty
                // Check if any field is empty
                if (NIM.isEmpty() || name.isEmpty() || progstud.isEmpty() || email.isEmpty() || alamat.isEmpty() || tanggalLahir.isEmpty() || selectedId == -1) {
                    Toast.makeText(MainActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else {
                    RadioButton selectedRadioButton = view.findViewById(selectedId);
                    String jenisKelamin = selectedRadioButton.getText().toString();
                    addUser(NIM, name, progstud, email, alamat, tanggalLahir, jenisKelamin);
                }
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }

    private void showDatePickerDialog(final EditText editTextTanggalLahir) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, monthOfYear, dayOfMonth1) -> {
                    String selectedDate = year1 + "-" + (monthOfYear + 1) + "-" + dayOfMonth1;
                    editTextTanggalLahir.setText(selectedDate);
                }, year, month, dayOfMonth);
        datePickerDialog.show();
    }

    private void addUser(String NIM, String name, String progstud, String email, String alamat, String tanggalLahir, String jenisKelamin) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        User user = new User(NIM, name, progstud, email, alamat, tanggalLahir, jenisKelamin);
        Call<Void> call = apiService.insertUser(user);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "User added successfully", Toast.LENGTH_SHORT).show();
                    fetchUsers();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed to add user", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchUsers() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<User>> call = apiService.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    userList.clear();
                    userList.addAll(response.body());
                    userAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed to fetch users", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
