package com.example.ecommerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import android.os.Bundle;
import com.example.ecommerce.Model.Users;

public class loginActivity extends AppCompatActivity {

    private Button login;
    private EditText  phnnumber,password;
    private ProgressDialog loadingBar;
    private String parentRoot = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button)findViewById(R.id.main_login_btn);
        phnnumber = (EditText)findViewById(R.id.login_phone_number_input);
        password = (EditText)findViewById(R.id.login_password_input);
        loadingBar = new ProgressDialog(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 createLogin();
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

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference().child(parentRoot).child(phn);

        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                if((dataSnapshot.child(parentRoot).child(phn).exists())){

                    String phnNumer = dataSnapshot.child("PhnNumber").getValue().toString();
                    String password = dataSnapshot.child("password").getValue().toString();


                    if (phnNumer.equals(phn)) {

                        if (password.equals(passd)) {

                            Toast.makeText(loginActivity.this, "SuccessFully Log In", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            Intent intent = new Intent(loginActivity.this, User.class);
                            startActivity(intent);

                        }

                    } else {

                        loadingBar.dismiss();
                        Toast.makeText(loginActivity.this, "Password is incorrect.", Toast.LENGTH_SHORT).show();
                    }





                }else{

                    Toast.makeText(loginActivity.this, "This not your valid phnNumber or password", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        /*

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               if((dataSnapshot.child(parentRoot).child(phn).exists())){

                    for(DataSnapshot userSnapshot: dataSnapshot.getChildren()) {

                        Users usersData = userSnapshot.getValue(Users.class);

                        Log.d("myTag", usersData.getPassword());

                       if (usersData.getPhnNumber().equals(phn)) {

                           if (usersData.getPassword().equals(passd)) {

                               Toast.makeText(loginActivity.this, "SuccessFully Log In", Toast.LENGTH_SHORT).show();
                               loadingBar.dismiss();

                               Intent intent = new Intent(loginActivity.this, User.class);
                               startActivity(intent);
                               break;
                           }

                       } else {

                           loadingBar.dismiss();
                           Toast.makeText(loginActivity.this, "Password is incorrect.", Toast.LENGTH_SHORT).show();
                       }

                   }


                }else{

                    Toast.makeText(loginActivity.this, "This not your valid phnNumber or password", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        */

    }

}
