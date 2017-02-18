package com.bernal.gilberto.firebasesample;

import android.content.Context;
import android.content.Intent;
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

    private View view = null;
    private FirebaseAuth firebaseAuth;
    private Button buttonLogout;
    private  Button buttonSaveData;
    private DatabaseReference databaseReference;
    private ValueEventListener mPostListener;
    private static final String TAG = "InsuranceFragment";
    EditText etCompanyName;
    EditText etType;
    EditText etContact;
    EditText etContactPhone, etPolicyNumber, etIssueDate,etExpiryDate;
    EditText etFaceAmount, etPremium, etComments;
    String CompanyName, Type, Contact,ContactPhone,PolicyNumber;
    String IssueDate, ExpiryDate, FaceAmount, Premium, Comments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_insurance, container, false);

        firebaseAuth=FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null)  // if current user already registered
        {
            /*  profile will be here
            Intent intent = new Intent();
            intent.setClass(getActivity(), ProfileActivity.class);
            startActivity(intent);  */
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        buttonSaveData = (Button) view.findViewById(R.id.buttonSaveData);
        buttonLogout = (Button) view.findViewById(R.id.buttonLogout);

        buttonSaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                etCompanyName = (EditText) view.findViewById(R.id.edittextCompanyName);
                etType = (EditText) view.findViewById(R.id.editTextType);
                etContact = (EditText) view.findViewById(R.id.editTextContact);
                etContactPhone = (EditText) view.findViewById(R.id.editTextContactPhone);
                etPolicyNumber = (EditText) view.findViewById(R.id.editTextPolicyNumber);
                etIssueDate = (EditText) view.findViewById(R.id.editTextIssueDate);
                etExpiryDate = (EditText) view.findViewById(R.id.editTextExpiryDate);
                etFaceAmount = (EditText) view.findViewById(R.id.editTextFaceAmount);
                etPremium = (EditText) view.findViewById(R.id.editTextPremium);
                etComments = (EditText) view.findViewById(R.id.editTextComments);

                CompanyName = etCompanyName.getText().toString().trim();
                Type = etType.getText().toString().trim();
                Contact = etContact.getText().toString().trim();
                ContactPhone = etContactPhone.getText().toString().trim();
                PolicyNumber = etPolicyNumber.getText().toString().trim();
                IssueDate = etIssueDate.getText().toString().trim();
                ExpiryDate = etExpiryDate.getText().toString().trim();
                FaceAmount = etFaceAmount.getText().toString().trim();
                Premium = etPremium.getText().toString().trim();
                Comments = etComments.getText().toString().trim();


                FirebaseUser user = firebaseAuth.getCurrentUser();
                InsuranceData datauser = new InsuranceData(CompanyName, Type, Contact, ContactPhone, PolicyNumber, IssueDate, ExpiryDate, FaceAmount, Premium, Comments);
                DatabaseReference insurance = databaseReference.child("Insurance").child(user.getUid()).push();
                insurance.setValue(datauser);
                Toast.makeText(getContext(), "Insurance information saved  .....", Toast.LENGTH_LONG).show();
            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             getActivity().finish();
            Intent intent = new Intent();
            intent.setClass(getActivity(), ProfileActivity.class);
            startActivity(intent);
            }
        });


        return view;
    }
}
