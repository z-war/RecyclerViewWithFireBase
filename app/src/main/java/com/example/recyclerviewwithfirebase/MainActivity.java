package com.example.recyclerviewwithfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    RecyclerView objectRecyclerView;
    private AdapterViewClass objadapterclass;
    EditText name , status;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Create a new user with a first and last name

        initialize();
    }

    protected void initialize() {
        try {
            db = FirebaseFirestore.getInstance();
            objectRecyclerView = findViewById(R.id.RV);
            name = findViewById(R.id.nameET);
            status = findViewById(R.id.statusET);
            addvaluestoRV();
        } catch (Throwable e) {
            Toast.makeText(this, "Error connecting" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void addvaluestoRV() {
        try {

            Query objectquery = db.collection("Records");
            FirestoreRecyclerOptions<UserClass> options;

            options = new FirestoreRecyclerOptions.Builder<UserClass>().setQuery(objectquery, UserClass.class).build();
            objadapterclass = new AdapterViewClass(options);
            objectRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            objectRecyclerView.setAdapter(objadapterclass);
        } catch (Throwable e) {
            Toast.makeText(this, "Error connecting" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        objadapterclass.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        objadapterclass.stopListening();
    }

    public void adddata(View v)
    {
        try {
            if(!name.getText().toString().isEmpty()&&!status.getText().toString().isEmpty())
            {
                Map<String,Object> mydatamap = new HashMap<>();
                mydatamap.put("name" , name.getText().toString());
                mydatamap.put("status",status.getText().toString());
                db.collection("Records").add(mydatamap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(MainActivity.this, "Status Added Succesfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Status not added"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }else
            {
                Toast.makeText(this, "Please Enter Name and Status", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e)
        {
            Toast.makeText(this, "Data not added"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
