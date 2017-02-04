package com.bernal.gilberto.firebasesample;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.bernal.gilberto.firebasesample.R.id.textViewSignUp;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private Button buttonResetPassword;
    private TextView textViewUserMail;
    private TextView textViewBack;
    private DatabaseReference databaseReference;
    private EditText  editTextEmail;
    private ValueEventListener mPostListener;
    private static final String TAG = "ProfileActivity";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        buttonResetPassword = (Button) findViewById(R.id.buttonResetPassword);
        textViewBack = (TextView) findViewById(R.id.textViewBack);
        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        buttonResetPassword.setOnClickListener(this);
        textViewBack.setOnClickListener(this);



    }

    private  void resetPassword(){

        String email = editTextEmail.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            //  empty email
            Toast.makeText(this, " Please Enter your email ", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage(" Registering  Please wait ...");
        progressDialog.show();
        firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ResetPasswordActivity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ResetPasswordActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                        }

                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    public void onClick(View view) {

        if(view == buttonResetPassword){
            resetPassword();
        }

        if ( view == textViewBack){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

    }
}
