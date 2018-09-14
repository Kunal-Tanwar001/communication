package com.example.prate.communication.LoginActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.prate.communication.DetailActivity.DetailActivity;
import com.example.prate.communication.MainActivity;
import com.google.firebase.database.ChildEventListener;

import com.example.prate.communication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreatingAccount extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference users;
    EditText username,password,useremail;
    Button register;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_account);
        //Firebase

        database=FirebaseDatabase.getInstance();
        users=database.getReference("Users");  // here users is the firebase table name and user is the object of class User
                                                  // this user is help to store a person details as an object ... so use
                                                  // it to retrive that person data ...

        useremail=findViewById(R.id.email);
        username=findViewById(R.id.usernamenewregister);
        password=findViewById(R.id.passwordR);
        register=findViewById(R.id.register);



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String useremaidnew= MainActivity.EncodeString(useremail.getText().toString());
                final User user=new User(username.getText().toString(),password.getText().toString(),useremaidnew);
                // final so no new can be added in it...

                users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(user.getUseremail()).exists()) {
                            Toast.makeText(getApplicationContext(), "This Username Already Exist !!", Toast.LENGTH_SHORT).show();
                        } else {
                            users.child(user.getUseremail()).setValue(user);
                            Toast.makeText(CreatingAccount.this, "Successful Registered", Toast.LENGTH_SHORT).show();
                            intent=new Intent(CreatingAccount.this, DetailActivity.class);
                            startActivity(intent);
                            finish();

                            // here users is the table where child is the rows in which we check whether data is present or not if yes then toast geneerated
                            // using datasnapshot
                            // else we take the useremail as the primary key to insrert user(object)  in its child of users(table). ehich is setvalue                      }
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    // add your custom code.
                    }
                });
            }
        });


    }
}
