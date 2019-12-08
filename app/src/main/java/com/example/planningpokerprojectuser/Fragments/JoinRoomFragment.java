package com.example.planningpokerprojectuser.Fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.planningpokerprojectuser.Objects.MyAdapter;
import com.example.planningpokerprojectuser.Objects.Question;
import com.example.planningpokerprojectuser.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class JoinRoomFragment extends Fragment {

    private EditText rID,rPassword;
    private Button rCreate;
    private String ID,Password,username;
    private static final String USERNAME= "userName";
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private RecyclerView recyclerView;
    private ArrayList<Question> listing;
    private MyAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_join_room,container, false);


        initialization(view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        listing = new ArrayList<Question>();
int i=1;
        myRef = FirebaseDatabase.getInstance().getReference("Admins").child("roland");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot i: dataSnapshot.getChildren()){
                    Question  newQestion = null;
                    Question question = new Question();
                    if(!i.getKey().equals("Password")) {
                        question.setQuestionID(i.getKey());

                    }
                    for(DataSnapshot j: i.getChildren()) {
                        if(!j.getKey().equals("Question")){
                            question.setQuestionPASS(j.getValue().toString());
                            newQestion.setQuestionPASS(j.getValue().toString());
                        }
                        for (DataSnapshot k : j.getChildren()) {

                            question.setQuestion(k.getKey());
                            newQestion =new Question(question.getQuestionID(),question.getQuestion());
                            listing.add(newQestion);
                        }
                    }
                }
                adapter = new MyAdapter(view.getContext(),listing);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        rCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ID = rID.getText().toString();
                Password = rPassword.getText().toString();

                Log.d("alma1",ID);
                Log.d("alma2",Password);

                checkID();

            }
        });



        return view;
    }

    @SuppressLint("CommitPrefEdits")
    private void joinQuestion(){

        myRef = FirebaseDatabase.getInstance().getReference();

        if (username != null) {
            myRef.child("Users").child(username).child(ID).setValue("");
        }
    }

    private void checkID(){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Groups");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                String id =  dataSnapshot.child(ID).getValue().toString();

                if(!id.equals(ID)){
                    joinQuestion();

                }
                else{
                    Toast.makeText(getContext(),"Wrong room id", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled( DatabaseError databaseError) {

            }
        });

    }



    private void initialization(View view){
        rID = view.findViewById(R.id.roomID);
        rPassword = view.findViewById(R.id.roomPassword);
        rCreate = view.findViewById(R.id.buttonRoomCreate);
       // list = view.findViewById(R.id.room_list);
        recyclerView = view.findViewById(R.id.room_list);


        if (getArguments() != null) {
            username = getArguments().getString(USERNAME);
        }

    }

    public static JoinRoomFragment newInstance(String text) {
        JoinRoomFragment fragment = new JoinRoomFragment();
        Bundle args = new Bundle();
        args.putString(USERNAME, text);
        fragment.setArguments(args);
        return fragment;
    }

}
