package com.serveme_employee.home;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.serveme_employee.R;
import com.serveme_employee.adapter.WorkAdapter;
import com.serveme_employee.databinding.FragmentHomeBinding;
import com.serveme_employee.model.WorkModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment
{

    private FragmentHomeBinding binding;
//    private NavController navController;

    private ArrayList<WorkModel> workModels;
    private WorkAdapter workAdapter;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference retriveRef;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);


        initViews(view);
        initDatabase();
        clickViews();
        retriveData();

    }

    private void initViews(View view)
    {
//        navController = Navigation.findNavController(view);

        workModels = new ArrayList<>();
        workAdapter = new WorkAdapter(workModels);
        binding.rVHome.setAdapter(workAdapter);
        binding.rVHome.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.rVHome.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        binding.rVHome.setLayoutManager(new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false));
        binding.rVHome.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
    }

    private void initDatabase()
    {
        firebaseAuth = FirebaseAuth.getInstance();
        retriveRef = FirebaseDatabase.getInstance().getReference();
    }

    private void clickViews()
    {

        binding.imgAddWork.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Navigation.findNavController(view).navigate(R.id.addWorkFragment);
            }
        });

        binding.imgMessage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Navigation.findNavController(view).navigate(R.id.messagesFragment);
            }
        });
    }

    private void retriveData()
    {
        binding.loadingHome.setVisibility(View.VISIBLE);
        retriveRef
                .child("Employees")
                .child(firebaseAuth.getUid())
                .child("Works")
                .addValueEventListener(new ValueEventListener()
                {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        binding.loadingHome.setVisibility(View.GONE);
                        workModels.clear();
                        for(DataSnapshot dataSnapshot : snapshot.getChildren())
                        {
                            WorkModel workModel = dataSnapshot.getValue(WorkModel.class);
                            workModels.add(workModel);
                        }
                        workAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error)
                    {
                        binding.loadingHome.setVisibility(View.GONE);
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}