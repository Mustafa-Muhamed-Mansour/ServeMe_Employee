package com.serveme_employee.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.serveme_employee.databinding.ItemRequestBinding;
import com.serveme_employee.model.RequestModel;

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
