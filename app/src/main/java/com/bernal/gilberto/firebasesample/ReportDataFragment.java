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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ReportDataFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private View view;
    private Button buttonLogout;
    private static final String TAG = "ReportDataFragment";
    private  FirebaseUser user;
    private TextView tvsalida;
    private ListView lv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        userInfoList = new ArrayList();
        view = inflater.inflate(R.layout.fragment_report_data, container, false);
        final ArrayAdapter<String> clientAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1);
        firebaseAuth= FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        user = firebaseAuth.getCurrentUser();
        TextView tv3 = (TextView) getActivity().findViewById(R.id.tv3);
        tv3.setVisibility(View.GONE);
        tvsalida = (TextView) view.findViewById(R.id.tvsalida);
        lv = (ListView) view.findViewById(R.id.lv);
        buttonLogout = (Button) view.findViewById(R.id.buttonLogout);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener()
        {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI

                DataSnapshot customerData = dataSnapshot.child("Insurance");
                for (DataSnapshot postSnapshot : customerData.getChildren()){
                    if (postSnapshot.getKey().equals (user.getUid())) {
                        for (DataSnapshot snapshot : postSnapshot.getChildren()) {
                            InsuranceData client = snapshot.getValue(InsuranceData.class);
                            String clientString = client.getCompany_name() + "     "+ client.gettype() +"    "+ client.getPolicy_number() +"    "+ client.getFace_amount()+ "\n\n";
                            clientAdapter.add(clientString);
                            //tvsalida.append(string);

                        }
                        lv.setAdapter(clientAdapter);


                    }
                }}

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                //System.out.println("The read failed: " );
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
