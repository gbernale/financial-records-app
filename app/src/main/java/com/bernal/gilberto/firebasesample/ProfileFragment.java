package com.bernal.gilberto.firebasesample;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileFragment extends Fragment {

    private FirebaseAuth firebaseAuth;
    private Button buttonLogout;
    private TextView textViewUserMail;
    private DatabaseReference databaseReference;
    private EditText editTextName, editTextAddress, editTextPhone;
    private Button buttonSaveData;
    private TextView textViewPersons;
    private ValueEventListener mPostListener;
    private View view;
    private static final String TAG = "ProfileFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

       firebaseAuth=FirebaseAuth.getInstance();
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        final FirebaseUser user = firebaseAuth.getCurrentUser();

        editTextAddress = (EditText) view.findViewById(R.id.editTextAddress);
        editTextName = (EditText) view.findViewById(R.id.editTextName);
        editTextPhone = (EditText) view.findViewById(R.id.editTextPhone);
        textViewPersons = (TextView) view.findViewById(R.id.textViewPersons);
        buttonSaveData = (Button) view.findViewById(R.id.buttonSaveData);

        buttonLogout = (Button) view.findViewById(R.id.buttonLogout);
        textViewUserMail = (TextView) view.findViewById(R.id.textViewUserEmail);
        textViewUserMail.setText(" Welcome "+ user.getEmail());

        loadUserdata(user);

        buttonSaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = editTextName.getText().toString().trim();
                String address = editTextAddress.getText().toString().trim();
                String phone = editTextPhone.getText().toString().trim();
                UserData datauser = new UserData(name, address, phone);
                FirebaseUser user = firebaseAuth.getCurrentUser();
                databaseReference.child("CustomerData").child(user.getUid()).setValue(datauser);
                Toast.makeText(getContext(), "Profile saved  .....", Toast.LENGTH_LONG).show();
                loadUserdata(user);
            }
        });


        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        return inflater.inflate(R.layout.fragment_profile, container, false);
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
                        textViewPersons.setText(string);
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

}
