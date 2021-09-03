package com.example.sonaai_11;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class signUp extends AppCompatActivity {
    TextView h1,tnc;
    EditText mfullname,memail,mpassword,mcpassword;
    Button signin,signup;
    FirebaseAuth auth;
    DatabaseReference databaseReference;
     private String TAG="authen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();


        //adding textview
        h1=(TextView)findViewById(R.id.h1);
        tnc=(TextView)findViewById(R.id.tnc);

        mfullname=(EditText)findViewById(R.id.fullname);
        memail=(EditText)findViewById(R.id.email);
        mpassword=(EditText)findViewById(R.id.password);
        mcpassword=(EditText)findViewById(R.id.cpassword);
        signin= findViewById(R.id.sign_in);
        signup= findViewById(R.id.sign_up);
        auth=FirebaseAuth.getInstance();

        h1.setText("Create Account");
        tnc.setText("By creating an account you agree to our\nTerms of Service and Privacy Policy");

        //database realtime add user into the database and authentication
        databaseReference=FirebaseDatabase.getInstance().getReference("User");
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fullname = mfullname.getText().toString().trim();
                final String email = memail.getText().toString().trim();
                String password = mpassword.getText().toString().trim();
                String cpassword = mcpassword.getText().toString().trim();

                if (TextUtils.isEmpty(fullname)) {
                    Toast.makeText(getApplicationContext(), "Enter Fullname", Toast.LENGTH_SHORT).show();
                    return;

                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter Email", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter Password", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (TextUtils.isEmpty(cpassword)) {
                    Toast.makeText(getApplicationContext(), "Enter Confirm Password", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short", Toast.LENGTH_SHORT).show();
                    return;


                }
                if (password.equals(cpassword)) {
                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        users info = new users(fullname, email);
                                        FirebaseDatabase.getInstance().getReference("User")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_SHORT).show();

                                                Intent intent = new Intent(getApplicationContext(), signIn.class);
                                                startActivity(intent);
                                            }
                                        });
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Registration unsuccessful", Toast.LENGTH_SHORT).show();

                                    }

                                }
                            });


                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Password doesnt match", Toast.LENGTH_SHORT).show();

                }
            }
        });




        //onclick buttons for sign in

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getApplicationContext(),signIn.class);
                startActivity(intent);

            }
        });



//oncreate over
    }



//over
}
