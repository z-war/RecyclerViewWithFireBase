package com.example.recyclerviewwithfirebase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewwithfirebase.R;
import com.example.recyclerviewwithfirebase.UserClass;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class AdapterViewClass extends FirestoreRecyclerAdapter<UserClass,AdapterViewClass.appViewHolderClass> {

    public AdapterViewClass(@NonNull FirestoreRecyclerOptions<UserClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final appViewHolderClass appViewHolderclass, int i, @NonNull UserClass userClass) {
            appViewHolderclass.usernameTV.setText(userClass.getName());
            appViewHolderclass.userstatusTV.setText(userClass.getStatus());
            appViewHolderclass.iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(appViewHolderclass.iv.getContext(), "Image is clicked", Toast.LENGTH_SHORT).show();
                    
                }
            });
    }

    @NonNull
    @Override
    public appViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View varView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item, parent,false);

        appViewHolderClass objappviewholder = new appViewHolderClass(varView);

        return objappviewholder;

    }

    class appViewHolderClass extends RecyclerView.ViewHolder{

         TextView usernameTV,userstatusTV;
         ImageView iv;

         public appViewHolderClass(@NonNull View itemView) {
             super(itemView);
             usernameTV = itemView.findViewById(R.id.nameTV);
             userstatusTV = itemView.findViewById(R.id.statusTV);
             iv = itemView.findViewById(R.id.imgview);
         }
     }
}
