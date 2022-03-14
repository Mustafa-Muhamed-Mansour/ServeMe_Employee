package com.serveme_employee.adapter;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.serveme_employee.R;
import com.serveme_employee.databinding.ItemWorkBinding;
import com.serveme_employee.model.WorkModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class WorkAdapter extends RecyclerView.Adapter<WorkAdapter.WorkViewHolder>
{

    private ArrayList<WorkModel> workModels;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference deleteRef;

    public WorkAdapter(ArrayList<WorkModel> workModels)
    {
        this.workModels = workModels;
    }

    @NonNull
    @Override
    public WorkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        firebaseAuth = FirebaseAuth.getInstance();
        deleteRef = FirebaseDatabase.getInstance().getReference();
        return new WorkViewHolder(ItemWorkBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WorkViewHolder holder, int position)
    {
        WorkModel model = workModels.get(position);

        Picasso
                .get()
                .load(model.getImage())
                .into(holder.binding.itemImgWork);

        holder.binding.itemImgBtnMore.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                PopupMenu deleteMenu = new PopupMenu(holder.itemView.getContext(), view);
                deleteMenu.getMenuInflater().inflate(R.menu.delete_menu, deleteMenu.getMenu());
                deleteMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
                {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem)
                    {
                        switch (menuItem.getItemId())
                        {
                            case R.id.delete:
                                deleteRef
                                        .child("Employees")
                                        .child(firebaseAuth.getUid())
                                        .child("Works")
                                        .child(model.getRandomKey())
                                        .removeValue();
                                Toast.makeText(holder.itemView.getContext(), "Done is Sucessfully", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(holder.itemView.getContext(), "Error", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return true;
                    }
                });
                deleteMenu.show();
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return workModels.size();
    }

    public class WorkViewHolder extends RecyclerView.ViewHolder
    {

        private ItemWorkBinding binding;

        public WorkViewHolder(@NonNull ItemWorkBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
