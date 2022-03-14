package com.serveme_employee.register;

import android.annotation.SuppressLint;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.serveme_employee.R;
import com.serveme_employee.databinding.FragmentRegisterBinding;
import com.serveme_employee.model.EmployeeModel;

public class RegisterFragment extends Fragment
{

    private NavController navController;
    private FragmentRegisterBinding binding;

    private ActivityResultLauncher<Intent> someActivityResultLauncher;

    private RadioButton radioButton;
    private String idRef, image, ID;
    private Uri resultURI;
    private FirebaseAuth firebaseAuth;
    private StorageReference imageRef;
    private DatabaseReference databaseRef;
//    private FirebaseFirestore databaseRef;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("ResourceType")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);


        navController = Navigation.findNavController(view);

        firebaseAuth = FirebaseAuth.getInstance();
        idRef = FirebaseDatabase.getInstance().getReference().push().getKey();
        imageRef = FirebaseStorage.getInstance().getReference().child("Images").child("Image_Employees").child(idRef);
        ID = firebaseAuth.getUid();
        databaseRef = FirebaseDatabase.getInstance().getReference();
//        databaseRef = FirebaseFirestore.getInstance();

        binding.imgAddPhoto.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                OpenGallery();
            }
        });

        binding.radioBtnMale.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int i = binding.radioGroupGender.getCheckedRadioButtonId();
                radioButton = view.findViewById(i);
            }
        });

        binding.radioBtnCustom.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int i = binding.radioGroupGender.getCheckedRadioButtonId();
                radioButton = view.findViewById(i);
            }
        });

        binding.radioBtnFemale.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int i = binding.radioGroupGender.getCheckedRadioButtonId();
                radioButton = view.findViewById(i);
            }
        });

        binding.btnCreateAccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                String firstName = binding.editFnRegister.getText().toString();
                String lastName = binding.editLnRegister.getText().toString();
                String phoneNumber = binding.editPhoneRegister.getText().toString();
                String email = binding.editEmailRegister.getText().toString();
                String password = binding.editPasswordRegister.getText().toString();
                String job = binding.editJobRegister.getText().toString();

                if (resultURI == null)
                {
                    Snackbar.make(binding.parentNestedRegister, "Please enter your image", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(firstName))
                {
                    Snackbar.make(binding.parentNestedRegister, "Please enter your first name", Snackbar.LENGTH_SHORT).show();
                    binding.editFnRegister.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(lastName))
                {
                    Snackbar.make(binding.parentNestedRegister, "Please enter your last name", Snackbar.LENGTH_SHORT).show();
                    binding.editLnRegister.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(phoneNumber))
                {
                    Snackbar.make(binding.parentNestedRegister, "Please enter your phone number", Snackbar.LENGTH_SHORT).show();
                    binding.editPhoneRegister.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(email))
                {
                    Snackbar.make(binding.parentNestedRegister, "Please enter your email", Snackbar.LENGTH_SHORT).show();
                    binding.editEmailRegister.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(password))
                {
                    Snackbar.make(binding.parentNestedRegister, "Please enter your password", Snackbar.LENGTH_SHORT).show();
                    binding.editPasswordRegister.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(job))
                {
                    Snackbar.make(binding.parentNestedRegister, "Please enter your job", Snackbar.LENGTH_SHORT).show();
                    binding.editJobRegister.requestFocus();
                    return;
                }

                else
                {

                    String randomKey = FirebaseDatabase.getInstance().getReference().push().getKey();

                    binding.loadingRegister.setVisibility(View.VISIBLE);
                    binding.btnCreateAccount.setVisibility(View.GONE);

                    firebaseAuth
                            .createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                            {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        imageRef
                                                .putFile(resultURI)
                                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
                                                {
                                                    @Override
                                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                                                    {
                                                        imageRef
                                                                .getDownloadUrl()
                                                                .addOnSuccessListener(new OnSuccessListener<Uri>()
                                                                {
                                                                    @Override
                                                                    public void onSuccess(Uri uri)
                                                                    {
                                                                        EmployeeModel employeeModel = new EmployeeModel(randomKey, firebaseAuth.getUid(), uri.toString(), firstName, lastName, phoneNumber, email, job, radioButton.getText().toString());

                                                                        databaseRef
                                                                                .child("Employees")
                                                                                .child(firebaseAuth.getUid())
                                                                                .setValue(employeeModel);

                                                                        databaseRef
                                                                                .child("Jobs")
                                                                                .child(job)
                                                                                .child(randomKey)
                                                                                .setValue(employeeModel);
//                                                                    databaseRef
//                                                                            .child("Employees")
//                                                                            .child(ID)
//                                                                            .setValue(employeeModel);
//                                                                        databaseRef
//                                                                                .collection("Employees")
//                                                                                .document(ID)
//                                                                                .set(employeeModel);

//                                                                        databaseRef
//                                                                                .collection("Jobs")
//                                                                                .document(job)
//                                                                                .collection(randomKey)
//                                                                                .document()
//                                                                                .set(employeeModel);

                                                                        binding.imgAddPhoto.setImageURI(null);

                                                                        binding.loadingRegister.setVisibility(View.GONE);
                                                                        Toast.makeText(getActivity(), "Done", Toast.LENGTH_SHORT).show();
                                                                        navController.navigate(R.id.action_registerFragment_to_homeFragment);
                                                                    }
                                                                }).addOnFailureListener(new OnFailureListener()
                                                        {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e)
                                                            {
                                                                binding.loadingRegister.setVisibility(View.GONE);
                                                                binding.btnCreateAccount.setVisibility(View.VISIBLE);
                                                                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
//                                                        EmployeeModel employeeModel = new EmployeeModel(randomKey, ID, resultURI.toString(), firstName, lastName, phoneNumber, email, job, radioButton.getText().toString());
////                                                                    databaseRef
////                                                                            .child("Employees")
////                                                                            .child(ID)
////                                                                            .setValue(employeeModel);
//                                                        databaseRef
//                                                                .collection("Employees")
//                                                                .document(ID)
//                                                                .set(employeeModel);
//
//                                                        binding.imgAddPhoto.setImageURI(null);
//
//                                                        binding.loadingRegister.setVisibility(View.GONE);
//                                                        Toast.makeText(getActivity(), "Done", Toast.LENGTH_SHORT).show();
//                                                        navController.navigate(R.id.action_registerFragment_to_homeFragment);
                                                    }
                                                }).addOnFailureListener(new OnFailureListener()
                                        {
                                            @Override
                                            public void onFailure(@NonNull Exception e)
                                            {
                                                binding.loadingRegister.setVisibility(View.GONE);
                                                binding.btnCreateAccount.setVisibility(View.VISIBLE);
                                                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }

                                    else
                                    {
                                        Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
//                            .addOnSuccessListener(new OnSuccessListener<AuthResult>()
//                            {
//                                @Override
//                                public void onSuccess(AuthResult authResult)
//                                {
//                                    imageRef
//                                            .putFile(resultURI)
//                                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
//                                            {
//                                                @Override
//                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
//                                                {
//                                                    imageRef
//                                                            .getDownloadUrl()
//                                                            .addOnSuccessListener(new OnSuccessListener<Uri>()
//                                                            {
//                                                                @Override
//                                                                public void onSuccess(Uri uri)
//                                                                {
//                                                                    EmployeeModel employeeModel = new EmployeeModel(randomKey, ID, uri.toString(), firstName, lastName, phoneNumber, email, job, radioButton.getText().toString());
////                                                                    databaseRef
////                                                                            .child("Employees")
////                                                                            .child(ID)
////                                                                            .setValue(employeeModel);
//                                                                    databaseRef
//                                                                            .collection("Employees")
//                                                                            .document(ID)
//                                                                            .set(employeeModel);
//
//                                                                    binding.loadingRegister.setVisibility(View.GONE);
//                                                                    Toast.makeText(getActivity(), "Done", Toast.LENGTH_SHORT).show();
//                                                                    navController.navigate(R.id.action_registerFragment_to_homeFragment);
//                                                                }
//                                                            }).addOnFailureListener(new OnFailureListener()
//                                                    {
//                                                        @Override
//                                                        public void onFailure(@NonNull Exception e)
//                                                        {
//                                                            binding.loadingRegister.setVisibility(View.GONE);
//                                                            binding.btnCreateAccount.setVisibility(View.VISIBLE);
//                                                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                                                        }
//                                                    });
//                                                }
//                                            }).addOnFailureListener(new OnFailureListener()
//                                    {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e)
//                                        {
//                                            binding.loadingRegister.setVisibility(View.GONE);
//                                            binding.btnCreateAccount.setVisibility(View.VISIBLE);
//                                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//
////                                    EmployeeModel employeeModel = new EmployeeModel(randomKey, ID, "uri.toString()", firstName, lastName, phoneNumber, email, job, radioButton.getText().toString());
////                                    databaseRef
////                                            .collection("Employees")
////                                            .document(ID)
////                                            .set(employeeModel);
////
////                                    binding.loadingRegister.setVisibility(View.GONE);
////                                    Toast.makeText(getActivity(), "Done", Toast.LENGTH_SHORT).show();
////                                    navController.navigate(R.id.action_registerFragment_to_homeFragment);
//
//                                }
//                            }).addOnFailureListener(new OnFailureListener()
//                    {
//                        @Override
//                        public void onFailure(@NonNull Exception e)
//                        {
//                            binding.loadingRegister.setVisibility(View.GONE);
//                            binding.btnCreateAccount.setVisibility(View.VISIBLE);
//                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
                }
            }
        });

        someActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>()
        {
            @Override
            public void onActivityResult(ActivityResult result)
            {
                Intent data = result.getData();

                if (result.getResultCode() == Activity.RESULT_OK && data != null && data.getData() != null)
                {
                    resultURI = data.getData();
                    binding.imgAddPhoto.setImageURI(resultURI);
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

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
//    {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == 100 && data != null && data.getData() != null)
//        {
//            resultURI = data.getData();
//            binding.imgAddPhoto.setImageURI(resultURI);
//        }
//
//    }
}