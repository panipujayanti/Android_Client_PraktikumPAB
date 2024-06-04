package com.praktikumpab.android_client;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
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

        userAdapter = new UserAdapter(userList, this);
        recyclerView.setAdapter(userAdapter);

        // Set the MainActivity reference in the adapter
        userAdapter.setMainActivity(this);

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

    private void updateUser(int id, String NIM, String name, String progstud, String email, String alamat, String tanggal_lahir, String jenis_kelamin) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        User user = new User(id, NIM, name, progstud, email, alamat, tanggal_lahir, jenis_kelamin);
        Call<Void> call = apiService.updateUser(user);
        Log.d("MainActivity", "Updating user: " + id + ", " + NIM + ", " + name + "," + progstud + ", " + email + ", " + alamat +", " +tanggal_lahir +"," + jenis_kelamin);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("MainActivity", "User updated successfully");
                    Toast.makeText(MainActivity.this, "User updated successfully", Toast.LENGTH_SHORT).show();
                    fetchUsers();
                } else {
                    Log.e("MainActivity", "Response error: " + response.errorBody().toString());
                    Toast.makeText(MainActivity.this, "Failed to update user: " + response.message(),
                            Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("MainActivity", "Fetch error: ", t);
                Toast.makeText(MainActivity.this, "Failed to update user: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showUpdateDialog(final User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update User");

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_update_user, (ViewGroup) findViewById(android.R.id.content), false);
        final EditText inputNIM = viewInflated.findViewById(R.id.editTextNIMUpdate);
        final EditText inputName = viewInflated.findViewById(R.id.editTextNameUpdate);
        final EditText inputProgstud = viewInflated.findViewById(R.id.editTextProgstudUpdate);
        final EditText inputEmail = viewInflated.findViewById(R.id.editTextEmailUpdate);
        final EditText inputAlamat = viewInflated.findViewById(R.id.editTextAlamatUpdate);
        final EditText inputTanggalLahir = viewInflated.findViewById(R.id.editTextTanggalLahirUpdate);
        final RadioGroup radioGroupJenisKelamin = viewInflated.findViewById(R.id.radioGroupJenisKelaminUpdate);
        final RadioButton radioButtonMale = viewInflated.findViewById(R.id.radioButtonPriaUpdate);
        final RadioButton radioButtonFemale = viewInflated.findViewById(R.id.radioButtonWanitaUpdate);
        final Calendar calendar = Calendar.getInstance();

        inputNIM.setText(user.getNIM());
        inputName.setText(user.getName());
        inputProgstud.setText(user.getProgstud());
        inputEmail.setText(user.getEmail());
        inputAlamat.setText(user.getAlamat());
        inputTanggalLahir.setText(user.getTanggalLahir());

        // Set RadioButton based on user's gender
        if (user.getJenisKelamin().equals("Pria")) {
            radioButtonMale.setChecked(true);
        } else {
            radioButtonFemale.setChecked(true);
        }

        // Dialog date picker saat klik pada inputTanggalLahir
        inputTanggalLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Parse tanggal lahir pengguna yang sudah ada
                String tanggalLahir = user.getTanggalLahir();
                String[] parts = tanggalLahir.split("-");
                int tahun = Integer.parseInt(parts[0]);
                int bulan = Integer.parseInt(parts[1]) - 1; // Kurangi 1 karena indeks bulan dimulai dari 0
                int tanggal = Integer.parseInt(parts[2]);

                // Buat DatePickerDialog dengan tanggal awal yang sesuai dengan tanggal lahir pengguna yang sudah ada
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // Set text pada inputTanggalLahir sesuai dengan tanggal yang dipilih
                                String selectedDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                inputTanggalLahir.setText(selectedDate);
                            }
                        }, tahun, bulan, tanggal);
                datePickerDialog.show();
            }
        });


        builder.setView(viewInflated);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                String NIM = inputNIM.getText().toString();
                String name = inputName.getText().toString();
                String progstud = inputProgstud.getText().toString();
                String email = inputEmail.getText().toString();
                String alamat = inputAlamat.getText().toString();
                String tanggal_lahir = inputTanggalLahir.getText().toString();
                int selectedId = radioGroupJenisKelamin.getCheckedRadioButtonId();
                String jenis_kelamin = (selectedId == R.id.radioButtonPriaUpdate) ? "Pria" : "Wanita"; // Mendapatkan jenis kelamin dari RadioButton yang dipilih
                updateUser(user.getId(), NIM, name, progstud, email, alamat, tanggal_lahir, jenis_kelamin);
            }
        });

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }


    public void refreshData() {
        fetchUsers();
    }

}
