package com.example.buyandsell.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.buyandsell.R;
import com.example.buyandsell.data.AppDatabase;
import com.example.buyandsell.data.CustomerDao;
import com.example.buyandsell.data.CustomerEntity;

public class SignupActivity extends AppCompatActivity {

    private EditText etId;
    private EditText etName;
    private EditText etEmail;
    private EditText etMobile;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etId = findViewById(R.id.et_id);
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etMobile = findViewById(R.id.et_mobile);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        btnSubmit = findViewById(R.id.btn_submit);

        etEmail.setEnabled(false);

        etId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String id = s.toString().trim();
                if (!id.isEmpty()) {
                    etEmail.setText(id + "@uohyd.ac.in");
                } else {
                    etEmail.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitSignup();
            }
        });
    }

    private void submitSignup() {
        String id = etId.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String mobile = etMobile.getText().toString().trim();
        String password = etPassword.getText().toString();
        String confirm = etConfirmPassword.getText().toString();

        if (id.isEmpty() || name.isEmpty() || email.isEmpty() || mobile.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!email.endsWith("@uohyd.ac.in") || !email.startsWith(id)) {
            Toast.makeText(this, "Email must be auto-generated from ID", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(confirm)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        final CustomerEntity entity = new CustomerEntity();
        entity.id = id;
        entity.name = name;
        entity.email = email;
        entity.mobileNumber = mobile;
        entity.password = password;
        entity.isAdmin = false;

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    AppDatabase db = AppDatabase.getInstance(SignupActivity.this);
                    CustomerDao dao = db.customerDao();
                    dao.insert(entity);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SignupActivity.this, "Signup successful! Please login.", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });
                } catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SignupActivity.this, "Failed: ID or Email already exists", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
}


