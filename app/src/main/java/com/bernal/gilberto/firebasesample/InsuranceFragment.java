package com.bernal.gilberto.firebasesample;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class InsuranceFragment extends Fragment {

    private View view;
    private FirebaseAuth firebaseAuth;
    private Button buttonLogout;
    private  Button buttonsavedata;
    private DatabaseReference databaseReference;
    private ValueEventListener mPostListener;
    private static final String TAG = "InsuranceFragment";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        firebaseAuth=FirebaseAuth.getInstance();

        view = inflater.inflate(R.layout.fragment_insurance, container, false);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        final FirebaseUser user = firebaseAuth.getCurrentUser();

        EditText etCompanyName = (EditText) view.findViewById(R.id.edittextCompanyName);
        EditText etType = (EditText) view.findViewById(R.id.editTextType);
        EditText etContact = (EditText) view.findViewById(R.id.editTextContact);
        EditText etContactPhone = (EditText) view.findViewById(R.id.editTextContactPhone);
        EditText etPolicyNumber = (EditText) view.findViewById(R.id.editTextPolicyNumber);
        EditText etIssueDate = (EditText) view.findViewById(R.id.editTextIssueDate);
        EditText etExpiryDate = (EditText) view.findViewById(R.id.editTextExpiryDate);
        EditText etFaceAmount = (EditText) view.findViewById(R.id.editTextFaceAmount);
        EditText etPremium = (EditText) view.findViewById(R.id.editTextPremium);
        EditText etComments = (EditText) view.findViewById(R.id.editTextComments);
        buttonsavedata = (Button) view.findViewById(R.id.buttonSaveData);
        buttonLogout = (Button) view.findViewById(R.id.buttonLogout);

        final String CompanyName = etCompanyName.getText().toString().trim();
        final String Type = etType.getText().toString().trim();
        final String Contact = etContact.getText().toString().trim();
        final String ContactPhone = etContactPhone.getText().toString().trim();
        final String PolicyNumber = etPolicyNumber.getText().toString().trim();
        final String IssueDate = etIssueDate.getText().toString().trim();
        final String ExpiryDate = etExpiryDate.getText().toString().trim();
        final String FaceAmount = etFaceAmount.getText().toString().trim();
        final String Premium = etPremium.getText().toString().trim();
        final String Comments = etComments.getText().toString().trim();
        buttonsavedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InsuranceData datauser = new InsuranceData(CompanyName, Type, Contact, ContactPhone, PolicyNumber, IssueDate, ExpiryDate, FaceAmount, Premium, Comments);
                databaseReference.child("Insurance").child(user.getUid()).setValue(datauser);
                Toast.makeText(getContext(), "Insurance information saved  .....", Toast.LENGTH_LONG).show();
            }
        });
       return view;
    }
}
