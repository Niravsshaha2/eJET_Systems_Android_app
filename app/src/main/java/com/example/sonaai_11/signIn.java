package com.example.sonaai_11;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class signIn extends AppCompatActivity {

    TextView h1,fpassword;
    EditText memail,mpassword;
    Button signin,signup,google_signin,loginButton;
    private FirebaseAuth auth;
    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN=123;
    GoogleSignInAccount account;
    private CallbackManager mCallbackManager;
    private static final String EMAIL = "email";

    //on start

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=auth.getCurrentUser();
        if (user!=null)
        {
            Intent intent=new Intent(getApplicationContext(),chatbot_list.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().hide();


        //adding textview
        h1=(TextView)findViewById(R.id.h1);
        h1.setText("Login");

        //retrive values from layout
        memail=(EditText)findViewById(R.id.email);
        mpassword=(EditText)findViewById(R.id.password);
        fpassword=(TextView) findViewById(R.id.forgot_password);
        signin= findViewById(R.id.sign_in);
        signup= findViewById(R.id.sign_up);
        auth=FirebaseAuth.getInstance();
        google_signin= findViewById(R.id.google_signin);
         mCallbackManager = CallbackManager.Factory.create();
        //facebook login again



        loginButton = (Button) findViewById(R.id.fb_signin);
/*
        ((LoginButton) loginButton).setReadPermissions(Arrays.asList(EMAIL));
*/
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(signIn.this, Arrays.asList("public_profile", "user_friends"));

            }
        });


        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult)
            {
                handleFacebookAccessToken(loginResult.getAccessToken());
           }
            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });



        //google login
        createrequest();
        google_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });



        //Signup button(register)
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getApplicationContext(),signUp.class);
                startActivity(intent);


            }
        });

        //LOGIN button (continue)
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Toast.makeText(getApplicationContext(), "button clicked", Toast.LENGTH_SHORT).show();

                final String email=memail.getText().toString().trim();
                final String password=mpassword.getText().toString().trim();

                if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(getApplicationContext(),"Enter Email",Toast.LENGTH_SHORT).show();
                    return;

                }

                if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(getApplicationContext(),"Enter Email",Toast.LENGTH_SHORT).show();
                    return;

                }
                if(TextUtils.isEmpty(password))
                {
                    Toast.makeText(getApplicationContext(),"Enter Password",Toast.LENGTH_SHORT).show();
                    return;

                }





                auth.fetchSignInMethodsForEmail(memail.getText().toString()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task)
                    {
                        if (task.getResult().getSignInMethods().size() == 0)
                        {
                            Toast.makeText(getApplicationContext(), "Email Doesn't Exist",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getApplicationContext(),signUp.class);
                            startActivity(intent);

                        }
                        else
                        {
                            auth.signInWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(signIn.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful())
                                            {
                                                Toast.makeText(signIn.this, "Authentication success.",Toast.LENGTH_SHORT).show();
                                                Intent intent=new Intent(getApplicationContext(),chatbot_list.class);
                                                startActivity(intent);

                                            } else {
                                                 Toast.makeText(signIn.this, "Authentication failed.",Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    });
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                    }
                });

            }
            //login donr
        });



//oncreate
    }
    //facebook
    private void handleFacebookAccessToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(getApplicationContext(), "Authentication success."+task.getException(),Toast.LENGTH_SHORT).show();

                            FirebaseUser user = auth.getCurrentUser();
                            String fullname=user.getDisplayName();
                            String email=user.getEmail();
                            users info=new users(fullname,email);

                            FirebaseDatabase.getInstance().getReference("User")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(info) ;




                             startActivity(new Intent(getApplicationContext(),chatbot_list.class));
                         } else {
                            // If sign in fails, display a message to the user.
                             Toast.makeText(getApplicationContext(), "Authentication failed."+task.getException(),Toast.LENGTH_SHORT).show();
                         }

                        // ...
                    }
                });
    }




//google sign in functions
    private void createrequest()
    {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    //facebook

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

/*
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
*/


        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                 account = task.getResult(ApiException.class);
                 firebaseAuthWithGoogle(account.getIdToken());

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                 // ...
                Toast.makeText(signIn.this, e.getMessage(),Toast.LENGTH_SHORT).show();

            }
        }
        else{
            //If not request code is RC_SIGN_IN it must be facebook
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void firebaseAuthWithGoogle(String idToken)
    {

        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
         auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                             FirebaseUser user = auth.getCurrentUser();
                            String fullname=account.getDisplayName();
                            String email=account.getEmail();
                            users info=new users(fullname,email);

                            FirebaseDatabase.getInstance().getReference("User")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(info) ;
                            Intent intent=new Intent(getApplicationContext(),chatbot_list.class);
                            startActivity(intent);

                         } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(signIn.this, "Authentication failed",Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }
    //done google signin


    //class
}
