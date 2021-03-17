package com.yearfour.mercyproject.e_covidapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by  Mercy Chebet
 * on 18/10/2020 10:53 2020
 */
public class MyLevelsFragents extends Fragment {
    Button myleves, myleaves_isolation,start;
    int levelminus1,levelminus2,levelminus3 =0;
    int level1,level2,level3 =0;
    TextView header,message;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mylevels_fragment, container, false);

        myleves = view.findViewById(R.id.mylevels_cv);
        myleaves_isolation = view.findViewById(R.id.mylevels_asolation);
        start = view.findViewById(R.id.mylevels_sh);


        header = view.findViewById(R.id.mylevels_sh1);
        message = view.findViewById(R.id.mylevels_sh2);

        start.setOnClickListener(v -> {

          getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container
          ,new FragmentQuestion1()).commit();

        });

        myleves.setOnClickListener(v -> {

            Intent intent = new Intent(getActivity().getBaseContext(),
                    MapsActivity.class);
            intent.putExtra("message", "circles");
            getActivity().startActivity(intent);

        });
        myleaves_isolation.setOnClickListener(v -> {

            Intent intent = new Intent(getActivity().getBaseContext(),
                    MapsActivity.class);
            intent.putExtra("message", "isolation");
            getActivity().startActivity(intent);

        });
        setData();
        return view;
    }

    private void setData() {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();;


        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Questions").child(Getimei.getIMEI(getActivity())).exists()) {
                    QuestionsModel questionsModel = dataSnapshot.child("Questions").child(Getimei.getIMEI(getActivity())).getValue(QuestionsModel.class);

                    if (questionsModel.getQuestion1().equals("yes") && questionsModel.getQuestion2().equals("yes") && questionsModel.getQuestion3().equals("yes") && questionsModel.getQuestion4().equals("yes")){


                        header.setText("You show signs of infection");
                        message.setText("please consult a doctor");
                        start.setText("Check Levels Again");


                    } else {
                        header.setText("Your levels are good");
                        message.setText("you do not have the symptoms ");
                        start.setText("Try Checking Again");




                    }



                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

