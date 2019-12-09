package com.example.planningpokerprojectuser.Fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
    private TextView questionShow;
    private Button rJoin,one,two,three,four,five;
    private String ID,Password,username;
    private static final String USERNAME= "userName";
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private ArrayList<Question> listing;



    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_join_room,container, false);

        initialization(view);
        join(view);

        return view;
    }

    //csatlakozas a szobahoz
    private void join (final View view){
        rJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ID = rID.getText().toString();
                Password = rPassword.getText().toString();

                Log.d("alma1",ID);
                Log.d("alma2",Password);

                if(!ID.isEmpty() && !Password.isEmpty()){
                    checkID();
                    list();
                }
                else
                {
                    Toast.makeText(getContext(), "Room ID os Password is empty", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    //user elmentese
    @SuppressLint("CommitPrefEdits")
    private void joinQuestion(){

        myRef = FirebaseDatabase.getInstance().getReference();

        if (username != null) {
            myRef.child("Users").child(username).child(ID).setValue("");
        }
    }
    //Aktivalt kerdes kiirasa es lehetoseg a szavazasra
    private void list(){

        listing = new ArrayList<Question>();

        myRef = FirebaseDatabase.getInstance().getReference("Groups").child(ID);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot i: dataSnapshot.getChildren()) {
                    Log.d("listazas", String.valueOf(i));

                    if(i.getKey().equals("Question")){
                        Log.d("question",i.getKey());

                        for(DataSnapshot j: i.getChildren()) {

                                if(!i.getKey().equals(j.getKey())){
                                    final String quest=j.getKey();

                                    questionShow.setText(quest);

                                    Log.d("activated2","igen");
                                    for(final DataSnapshot k: j.getChildren()) {
                                        final String user=k.getValue().toString();
                                        if(k.getKey().equals("1")) {
    /*
                                        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatTime = new SimpleDateFormat("dd/MMM/yyyy HH:mm");
                                        Calendar c = Calendar.getInstance();
                                        try {
                                            Date expireDate = formatTime.parse(k.getValue().toString());
                                            Log.d("ido",expireDate.toString());
                                            String currentTime = formatTime.format(c.getTime());
                                            Log.d("ido",currentTime);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
    */
                                            one.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    String number = "1";

                                                    myRef.child("Groups").child(ID).child("Question").child(quest).child(number).setValue(user + ", " + username);
                                                    Toast.makeText(getContext(),"Your vote is: " + number +"Please dont vote again!!!", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                        if(k.getKey().equals("2")) {
                                            two.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    String number = "2";

                                                    myRef.child("Groups").child(ID).child("Question").child(quest).child(number).setValue(user + ", " + username);
                                                    Toast.makeText(getContext(),"Your vote is: " + number +"Please dont vote again!!!", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                        if(k.getKey().equals("3")) {
                                            three.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    String number = "2";

                                                    myRef.child("Groups").child(ID).child("Question").child(quest).child(number).setValue(user + ", " + username);
                                                    Toast.makeText(getContext(),"Your vote is: " + number +"Please dont vote again!!!", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                        if(k.getKey().equals("4")) {
                                            four.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    String number = "2";

                                                    myRef.child("Groups").child(ID).child("Question").child(quest).child(number).setValue(user + ", " + username);
                                                    Toast.makeText(getContext(),"Your vote is: " + number +"Please dont vote again!!!", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                        if(k.getKey().equals("5")) {
                                            five.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    String number = "2";

                                                    myRef.child("Groups").child(ID).child("Question").child(quest).child(number).setValue(user + ", " + username);
                                                    Toast.makeText(getContext(),"Your vote is: " + number +"Please dont vote again!!!", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    }
                                }
                                else{

                                }
                            }


                        }
                    }
                }




            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //szoba id es password ellenorzese
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

    //initializalas
    private void initialization(View view){
        rID = view.findViewById(R.id.roomID);
        rPassword = view.findViewById(R.id.roomPassword);
        rJoin = view.findViewById(R.id.buttonJoinRoom);
        one = view.findViewById((R.id.button1));
        two = view.findViewById((R.id.button2));
        three = view.findViewById((R.id.button3));
        four = view.findViewById((R.id.button4));
        five = view.findViewById((R.id.button5));
        questionShow = view.findViewById(R.id.questionLabel);

        if (getArguments() != null) {
            username = getArguments().getString(USERNAME);
        }

    }

    //username atvetele a login reszbol
    public static JoinRoomFragment newInstance(String text) {
        JoinRoomFragment fragment = new JoinRoomFragment();
        Bundle args = new Bundle();
        args.putString(USERNAME, text);
        fragment.setArguments(args);
        return fragment;
    }

}
