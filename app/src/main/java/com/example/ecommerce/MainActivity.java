package com.example.ecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.ecommerce.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private Button joinNowButton, loginButton;
    private CheckBox checkBoxRememberMe;
    private String parentRoot = "Users";
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadingBar = new ProgressDialog(this);

        Paper.init(this);
        joinNowButton = (Button) findViewById(R.id.main_join_now_btn);
        loginButton = (Button) findViewById(R.id.main_login_btn);

         loginButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 Intent intent = new Intent(MainActivity.this,loginActivity.class);
                 startActivity(intent);

             }
         });

        joinNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,registrationActivity.class);
                startActivity(intent);

            }
        });


        String phnNumber = Paper.book().read(Prevalent.UserPhoneKey);
        String password = Paper.book().read(Prevalent.UserPasswordKey);

        if(phnNumber!="" && password!=""){

            if(!TextUtils.isEmpty(phnNumber) && !TextUtils.isEmpty(password)){

                AllowAccess(phnNumber,password);

                loadingBar.setTitle("Already Logged In");
                loadingBar.setMessage("Please wait we are checking");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
            }
        }

    }

    private void AllowAccess(final String phn,final String passd) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference().child(parentRoot).child(phn);

        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String phnNumer = dataSnapshot.child("PhnNumber").getValue().toString();
                String password = dataSnapshot.child("password").getValue().toString();


                if (phnNumer.equals(phn)) {

                    if (password.equals(passd)) {

                        Toast.makeText(MainActivity.this, "SuccessFully Log In", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();

                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);

                    }


                } else {

                    loadingBar.dismiss();
                    Toast.makeText(MainActivity.this, "Password is incorrect.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
