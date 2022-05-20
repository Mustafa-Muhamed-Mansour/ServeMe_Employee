package com.serveme_employee.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.serveme_employee.R;
import com.serveme_employee.databinding.ItemRequestBinding;
import com.serveme_employee.model.RequestModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder>
{

    private ArrayList<RequestModel> requestModels;

    public RequestAdapter(ArrayList<RequestModel> requestModels)
    {
        this.requestModels = requestModels;
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new RequestViewHolder(ItemRequestBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position)
    {
        RequestModel model = requestModels.get(position);

        Picasso
                .get()
                .load(model.getUser_Image())
                .into(holder.binding.imgItemRequest);

        holder.binding.txtNameItemRequest.setText(model.getUser_Name());

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Bundle dataUser = new Bundle();
                dataUser.putString("User_Name", model.getUser_Name());
                dataUser.putString("User_Gender", model.getUser_Gender());
                dataUser.putString("User_Image", model.getUser_Image());
                dataUser.putString("User_Phone", model.getUser_Phone());
                dataUser.putString("User_Email", model.getUser_Email());
                Navigation.findNavController(view).navigate(R.id.userDetailFragment, dataUser);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return requestModels.size();
    }

    public class RequestViewHolder extends RecyclerView.ViewHolder
    {

        private ItemRequestBinding binding;

        public RequestViewHolder(@NonNull ItemRequestBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
