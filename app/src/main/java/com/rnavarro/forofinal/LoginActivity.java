package com.rnavarro.forofinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.messaging.Constants;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity
{
    private static final String DB_USER_TOKEN=" ";
    private static final String TAG="Login Activity";
    private FirebaseAuth mAuth;
    private TextInputEditText ET_Email;
    private TextInputEditText ET_Passsword;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        preferences= PreferenceManager.getDefaultSharedPreferences(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        mAuth = FirebaseAuth.getInstance();
        ET_Email = findViewById(R.id.TIET_Email);
        ET_Passsword = findViewById(R.id.TIET_Password);

        Button mLoginButton = findViewById(R.id.BT_Login);
        mLoginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                doLogin(ET_Email.getText().toString(), ET_Passsword.getText().toString());
            }
        });

        Button mRegisterButton = findViewById(R.id.BT_Register);
        mRegisterButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                createAccount(ET_Email.getText().toString(), ET_Passsword.getText().toString());
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null)
        {
            // De este modo si ya nos hemos logueado 1 vez ya no habria que pasar por esta pantalla al entrar en la App
            registrarToken(currentUser);
            startMain();

            //Con esta accion estamos haciendo que si ya estamos logueados, nos vuelva a pedir los datos, si quitamos el comentario de startMain(); entraria directo a MainActivity
            //mAuth.signOut();
        }

    }

    private boolean validateForm(String email, String password)
    {
        boolean valid = true;

        if (TextUtils.isEmpty(email))
        {
            ET_Email.setError("Email is required");
            valid = false;
        } else {
            ET_Email.setError(null);
        }

        if (TextUtils.isEmpty(password))
        {
            ET_Passsword.setError("Password is required");
            valid = false;
        } else {
            ET_Passsword.setError(null);
        }

        return valid;
    }

    private void doLogin(String email, String password)
    {
        if (!validateForm(email, password))
        {
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if (task.isSuccessful())
                {
                    FirebaseUser user = mAuth.getCurrentUser();

                    registrarToken(user);

                    startMain();

                } else {
                    Toast.makeText(LoginActivity.this, "Login error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void createAccount(String email, String password)
    {
        if (!validateForm(email, password))
        {
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {

                if (task.isSuccessful())
                {
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(email).build();
                    FirebaseUser user = mAuth.getCurrentUser();

                    if (user != null)
                        user.updateProfile(profileUpdates);
                    sendEmail();
                } else {
                    Toast.makeText(LoginActivity.this, "Registration error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void registrarToken(FirebaseUser user)
    {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<String> task) {
                if(!task.isSuccessful()){
                   Log.w(TAG,"fetching FCM registration token failed",task.getException());
                    return;
                }
                String token = task.getResult();
                SharedPreferences.Editor editor= preferences.edit();
                editor.putString(DB_USER_TOKEN,token);
                editor.apply();
            }
        });
    }

    private void sendEmail()
    {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null)
        {
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>()
            {
                @Override
                public void onComplete(@NonNull Task<Void> task)
                {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(LoginActivity.this,"A verification email has been sent to " + user.getEmail(),Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this,"Failed to send verification email", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void startMain()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}