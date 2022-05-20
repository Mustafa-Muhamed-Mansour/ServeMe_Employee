package com.serveme_employee.request;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.serveme_employee.adapter.RequestAdapter;
import com.serveme_employee.adapter.WorkAdapter;
import com.serveme_employee.databinding.FragmentRequestBinding;
import com.serveme_employee.model.RequestModel;
import com.serveme_employee.model.WorkModel;

import java.util.ArrayList;


public class RequestFragment extends Fragment
{

    private NavController navController;
    private FragmentRequestBinding binding;

    private ArrayList<RequestModel> requestModels;
    private RequestAdapter requestAdapter;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference retriveRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentRequestBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);


        initViews(view);
        initDatabase();
        retriveData();

    }

    private void initViews(View view)
    {
        navController = Navigation.findNavController(view);

        requestModels = new ArrayList<>();
        requestAdapter = new RequestAdapter(requestModels);
        binding.rVRequest.setAdapter(requestAdapter);
        binding.rVRequest.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.rVRequest.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    private void initDatabase()
    {
        firebaseAuth = FirebaseAuth.getInstance();
        retriveRef = FirebaseDatabase.getInstance().getReference();
    }

    private void retriveData()
    {
        binding.loadingRequest.setVisibility(View.VISIBLE);
        retriveRef
                .child("Employees")
                .child(firebaseAuth.getUid())
                .child("Request")
                .addValueEventListener(new ValueEventListener()
                {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        binding.loadingRequest.setVisibility(View.GONE);
                        requestModels.clear();

                        for(DataSnapshot dataSnapshot : snapshot.getChildren())
                        {
                            RequestModel requestModel = dataSnapshot.getValue(RequestModel.class);
                            requestModels.add(requestModel);
                        }
                        requestAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error)
                    {
                        binding.loadingRequest.setVisibility(View.GONE);
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}