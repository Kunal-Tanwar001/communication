package com.example.prate.communication.LoginActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.prate.communication.DetailActivity.DetailActivity;
import com.example.prate.communication.MainActivity;
import com.example.prate.communication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    Intent intent;
    Button newaccount, enter;
    EditText useremail, password;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        users = firebaseDatabase.getReference("Users");


        newaccount = findViewById(R.id.newaccount);
        enter = findViewById(R.id.enter); // enter is the login button which give the confirmation that success login is done.
        useremail = findViewById(R.id.useremail);
        password = findViewById(R.id.password);

        final String useremaidnew= MainActivity.EncodeString(useremail.getText().toString());



        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              final String useremaidnew = MainActivity.EncodeString(useremail.getText().toString());
                signIn(useremaidnew, password.getText().toString());
            }


        });


        newaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(LoginActivity.this, CreatingAccount.class);
                startActivity(intent);
            }
        });




    }

    private void signIn(final String username_entered, final String password_entered) {

        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(username_entered).exists()){
                    if(!username_entered.isEmpty()){
                         User login=dataSnapshot.child(username_entered).getValue(User.class);
                        if (login.getPassword().equals(password_entered)) {
                            Toast.makeText(LoginActivity.this,"SucessFul Login",Toast.LENGTH_SHORT).show();
                            intent=new Intent(LoginActivity.this, DetailActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(LoginActivity.this,"Wrong password!!",Toast.LENGTH_SHORT).show();
                        }

                    }
                    else{
                        Toast.makeText(LoginActivity.this,"Username is not registered",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
