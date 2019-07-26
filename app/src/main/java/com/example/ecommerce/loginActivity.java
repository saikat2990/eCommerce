package com.example.ecommerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;

import com.example.ecommerce.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class loginActivity extends AppCompatActivity {

    private Button login;
    private EditText  phnnumber,password;
    private ProgressDialog loadingBar;
    private String parentRoot = "Users";
    private CheckBox checkBoxRememberMe;
    private TextView Admin,NotAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button)findViewById(R.id.main_login_btn);
        phnnumber = (EditText)findViewById(R.id.login_phone_number_input);
        password = (EditText)findViewById(R.id.login_password_input);
        loadingBar = new ProgressDialog(this);
        Admin = (TextView) findViewById(R.id.admin_panel_link);
        NotAdmin = (TextView) findViewById(R.id.not_admin_panel_link);

        checkBoxRememberMe = (CheckBox) findViewById(R.id.remmembe_me_chkb);
        Paper.init(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 createLogin();
            }
        });

        Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                login.setText("Admin Login");
                Admin.setVisibility(View.INVISIBLE);
                NotAdmin.setVisibility(View.VISIBLE);
                parentRoot="Admins";
            }
        });

        NotAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                login.setText("Login");
                Admin.setVisibility(View.VISIBLE);
                NotAdmin.setVisibility(View.INVISIBLE);
                parentRoot="Users";
            }
        });

    }


    private void createLogin() {


        String phn = phnnumber.getText().toString();
        String passd = password.getText().toString();



        if(TextUtils.isEmpty(phn)){
            Toast.makeText(this, "Please write your phone number....", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(passd)){
            Toast.makeText(this, "Please write your password....", Toast.LENGTH_SHORT).show();
        }else{

            loadingBar.setTitle("Log In Account");
            loadingBar.setMessage("Please wait we are checking");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowAcessToAccount(phn,passd);
        }
    }

    private void AllowAcessToAccount(final String phn,final String passd) {


        if(checkBoxRememberMe.isChecked()){

            Paper.book().write(Prevalent.UserPhoneKey,phn);
            Paper.book().write(Prevalent.UserPasswordKey,passd);
        }

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference().child(parentRoot).child(phn);

        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    String phnNumer = dataSnapshot.child("PhnNumber").getValue().toString();
                    String password = dataSnapshot.child("password").getValue().toString();


                    if (phnNumer.equals(phn)) {

                        if (password.equals(passd)) {

                            if(parentRoot.equals("Admins")){

                                Toast.makeText(loginActivity.this, "hello admin ,SuccessFully Log In", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(loginActivity.this, AdminAddNewProductActivity.class);
                                startActivity(intent);

                            }else{


                                Toast.makeText(loginActivity.this, "SuccessFully Log In", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(loginActivity.this, HomeActivity.class);
                                startActivity(intent);

                            }


                        }


                    } else {

                        loadingBar.dismiss();
                        Toast.makeText(loginActivity.this, "Password is incorrect.", Toast.LENGTH_SHORT).show();
                    }







            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}
