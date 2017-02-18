package com.bernal.gilberto.firebasesample;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Menu;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private Button buttonLogout;
    private TextView textViewUserMail;
    private DatabaseReference databaseReference;
    private EditText editTextName, editTextAddress, editTextPhone;
    private Button buttonSaveData;
    private TextView textViewPersons;
    private ValueEventListener mPostListener;
    private static final String TAG = "ProfileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth=FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null)  // if current user already registered
        {
            //  profile will be here
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

       /* editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        textViewPersons = (TextView) findViewById(R.id.textViewPersons); */
        buttonSaveData = (Button) findViewById(R.id.buttonSaveData);

        FirebaseUser user = firebaseAuth.getCurrentUser();
        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        textViewUserMail = (TextView) findViewById(R.id.textViewUserEmail);
        textViewUserMail.setText(" Welcome "+ user.getEmail());
        buttonLogout.setOnClickListener(this);
     }

    //  added to include menu options

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.add_profile:
                callFragmentProfile();
                return true;
            case R.id.add_insurance:
                callFragmentInsurance();
                return true;
            case R.id.add_invest:
                saveUserData();
                return true;
            case R.id.logout:
                finish();
                startActivity(new Intent(this, MainActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveUserData() {

        String name = editTextName.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        UserData datauser = new UserData(name, address, phone);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child("CustomerData").child(user.getUid()).setValue(datauser);
        Toast.makeText(this, "Profile saved  .....", Toast.LENGTH_LONG).show();
        loadUserdata(user);
    }

    private void callFragmentInsurance()
    {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        InsuranceFragment insuranceFragment = new InsuranceFragment();
        transaction.replace(R.id.fragment_container,insuranceFragment,"Insurance");
        transaction.commit();
    }

    private void callFragmentProfile()
    {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        ProfileFragment profileFragment = new ProfileFragment();
        transaction.replace(R.id.fragment_container,profileFragment,"Customer Profile");
        transaction.commit();


    }

    private void loadUserdata(final FirebaseUser user){

        databaseReference.addValueEventListener(new ValueEventListener()
        {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                DataSnapshot customerData = dataSnapshot.child("CustomerData");
                for (DataSnapshot postSnapshot : customerData.getChildren()){
                    if (postSnapshot.getKey().equals (user.getUid())) {
                        UserData client = postSnapshot.getValue(UserData.class);
                       String string = "Name: " + client.getUsername() + "\nAddress: " + client.getUseraddress() + "\nTelephone : " + client.getUserPhone() + "\n\n";

                        //Displaying it on textview
                       // textViewPersons.setText(string);
                    }
                }}

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                //System.out.println("The read failed: " );
            }

        });


    }

    @Override
    public void onClick(View view) {
      if (view == buttonLogout){
          firebaseAuth.signOut();
          finish();
          startActivity(new Intent(this, LoginActivity.class));
      }

        if (view == buttonSaveData){
            saveUserData();
        }
    }
}
