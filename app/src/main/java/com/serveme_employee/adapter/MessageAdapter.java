package com.serveme_employee.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.serveme_employee.databinding.ItemMessageBinding;
import com.serveme_employee.model.MessageModel;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>
{

    private ArrayList<MessageModel> messageModels;

    public MessageAdapter(ArrayList<MessageModel> messageModels)
    {
        this.messageModels = messageModels;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new MessageViewHolder(ItemMessageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position)
    {
        MessageModel model = messageModels.get(position);
    }

    @Override
    public int getItemCount()
    {
        return messageModels.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder
    {

        private ItemMessageBinding binding;

        public MessageViewHolder(@NonNull ItemMessageBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
