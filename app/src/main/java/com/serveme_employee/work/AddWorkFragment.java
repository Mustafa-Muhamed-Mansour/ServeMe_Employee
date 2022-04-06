package com.serveme_employee.work;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.serveme_employee.R;
import com.serveme_employee.databinding.FragmentAddWorkBinding;
import com.serveme_employee.model.WorkModel;

public class AddWorkFragment extends Fragment
{

    private NavController navController;
    private FragmentAddWorkBinding binding;

    private ActivityResultLauncher<Intent> someActivityResultLauncher;

    private String idRef, ID;
    private Uri workResultURI;
    private FirebaseAuth firebaseAuth;
    private StorageReference workImageRef;
    private DatabaseReference databaseRef;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentAddWorkBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        firebaseAuth = FirebaseAuth.getInstance();
        idRef = FirebaseDatabase.getInstance().getReference().push().getKey();
        workImageRef = FirebaseStorage.getInstance().getReference().child("Images").child("Employee_Work_Image").child(idRef);
        ID = firebaseAuth.getUid();
        databaseRef = FirebaseDatabase.getInstance().getReference();

        someActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>()
        {
            @Override
            public void onActivityResult(ActivityResult result)
            {
                Intent data = result.getData();

                if (result.getResultCode() == Activity.RESULT_OK && data != null && data.getData() != null)
                {
                    workResultURI = data.getData();
                    binding.imgWork.setImageURI(workResultURI);
                }
            }
        });

        binding.imgBtnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                navController.navigate(R.id.action_addWorkFragment_to_homeFragment);
            }
        });

        binding.imgWork.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                OpenGallery();
            }
        });

        binding.btnAddWork.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String title = binding.editTitleWork.getText().toString();

                if (workImageRef == null)
                {
                    Snackbar.make(binding.parentRelativeAddWork, "Please enter work image", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(title))
                {
                    Snackbar.make(binding.parentRelativeAddWork, "Please enter work title", Snackbar.LENGTH_SHORT).show();
                    binding.editTitleWork.requestFocus();
                    return;
                }

                else
                {
                    String randomKey = FirebaseDatabase.getInstance().getReference().push().getKey();

                    binding.loadingAddWork.setVisibility(View.VISIBLE);
                    binding.btnAddWork.setVisibility(View.GONE);

                    workImageRef
                            .putFile(workResultURI)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
                            {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    workImageRef
                                            .getDownloadUrl()
                                            .addOnSuccessListener(new OnSuccessListener<Uri>()
                                            {
                                                @Override
                                                public void onSuccess(Uri uri)
                                                {
                                                    WorkModel workModel = new WorkModel(randomKey, firebaseAuth.getUid(), uri.toString(), title);

                                                    databaseRef
                                                            .child("Employees")
                                                            .child(firebaseAuth.getUid())
                                                            .child("Works")
                                                            .child(randomKey)
                                                            .setValue(workModel);

                                                    databaseRef
                                                            .child("Works")
                                                            .child(randomKey)
                                                            .setValue(workModel);

                                                    binding.imgWork.setImageURI(null);

                                                    binding.loadingAddWork.setVisibility(View.GONE);
                                                    Toast.makeText(getActivity(), "Done is Sucessfully", Toast.LENGTH_SHORT).show();
                                                    navController.navigate(R.id.action_addWorkFragment_to_homeFragment);
                                                }
                                            }).addOnFailureListener(new OnFailureListener()
                                    {
                                        @Override
                                        public void onFailure(@NonNull Exception e)
                                        {
                                            binding.loadingAddWork.setVisibility(View.GONE);
                                            binding.btnAddWork.setVisibility(View.VISIBLE);
                                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener()
                    {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            binding.loadingAddWork.setVisibility(View.GONE);
                            binding.btnAddWork.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void OpenGallery()
    {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
        someActivityResultLauncher.launch(intent);
//        CropImage
//                .activity()
//                .start(getContext(), this);
    }
}