package com.example.ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class registrationActivity extends AppCompatActivity {


    private Button createAccount;
    private EditText userName,phnNumber,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        createAccount = (Button)findViewById(R.id.main_register_btn);
        userName = (EditText) findViewById(R.id.register_userName_input);
        phnNumber = (EditText)findViewById(R.id.register_phone_number_input);
        password = (EditText)findViewById(R.id.register_password_input);
    }
}
