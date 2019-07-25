package com.example.ecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.ProgressDialog;
import java.util.HashMap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class registrationActivity extends AppCompatActivity {


    private Button createAccount;
    private EditText userName;
    private EditText phnNumber;
    private EditText password;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        createAccount = (Button)findViewById(R.id.main_register_btn);
        userName = (EditText) findViewById(R.id.register_userName_input);
        phnNumber = (EditText)findViewById(R.id.register_phone_number_input);
        password = (EditText)findViewById(R.id.register_password_input);
        loadingBar = new ProgressDialog(this);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
            }

        });

    }


    private void CreateAccount() {

        String name = userName.getText().toString();
        String phn = phnNumber.getText().toString();
        String passd = password.getText().toString();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Please write your name....", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(phn)){
            Toast.makeText(this, "Please write your phone number....", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(passd)){
            Toast.makeText(this, "Please write your password....", Toast.LENGTH_SHORT).show();
        }else{

            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait we are checking");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidatephoneNumber(name,phn,passd);
        }


    }

    private void ValidatephoneNumber(final String name, final String phn, final String passd) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!(dataSnapshot.child("Users").child(phn).exists())){


                    HashMap<String, Object> userDataMap = new HashMap<>();
                    userDataMap.put("PhnNumber",phn);
                    userDataMap.put("password",passd);
                    userDataMap.put("Name",name);

                    RootRef.child("Users").child(phn).updateChildren(userDataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(registrationActivity.this, "Congratulation, your account successfully create", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                        Intent intent = new Intent(registrationActivity.this,loginActivity.class);
                                        startActivity(intent);

                                    }else{
                                        loadingBar.dismiss();
                                        Toast.makeText(registrationActivity.this, "Network error please try again after a few minutes later", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }else{

                    Toast.makeText(registrationActivity.this,"This"+phn+"is already exists",Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(registrationActivity.this,"please try another phn number",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(registrationActivity.this,MainActivity.class);
                    startActivity(intent);


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
