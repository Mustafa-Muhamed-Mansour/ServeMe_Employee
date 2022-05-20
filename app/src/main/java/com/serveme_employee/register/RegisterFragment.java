package com.serveme_employee.register;

import static com.serveme_employee.constant.VariableConstant.JOB;

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
    private String idRef, ID, job;
    private ArrayAdapter<String> arrayJob;
    private Uri resultURI;
    private FirebaseAuth firebaseAuth;
    private StorageReference imageRef;
    private DatabaseReference databaseRef;


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


        initViews(view);
        initDatabase();
        clickViews();
        backgroundProcess();

    }

    private void initViews(View view)
    {
        navController = Navigation.findNavController(view);

        arrayJob = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, JOB);
        arrayJob.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerJob.setAdapter(arrayJob);
    }

    private void initDatabase()
    {
        firebaseAuth = FirebaseAuth.getInstance();
        idRef = FirebaseDatabase.getInstance().getReference().push().getKey();
        imageRef = FirebaseStorage.getInstance().getReference().child("Images").child("Image_Employees").child(idRef);
        ID = firebaseAuth.getUid();
        databaseRef = FirebaseDatabase.getInstance().getReference();
    }

    private void clickViews()
    {
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

        binding.spinnerJob.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                if (JOB[i].equals("Select Job"))
                {
                    binding.textJobRegister.setText(null);
                }

                else
                {
                    job = adapterView.getItemAtPosition(i).toString();
                    binding.textJobRegister.setText(job);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
                Toast.makeText(getActivity(), "Nothing Selected " + adapterView.toString(), Toast.LENGTH_SHORT).show();
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
                String job = binding.textJobRegister.getText().toString();

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

                if (binding.spinnerJob.getSelectedItem().toString().equalsIgnoreCase("Job") || binding.spinnerJob.getSelectedItem().toString().equalsIgnoreCase("Select Job"))
                {
                    Snackbar.make(binding.parentNestedRegister, "Please enter your job", Snackbar.LENGTH_SHORT).show();
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
                                                                        EmployeeModel employeeModel = new EmployeeModel();

                                                                        databaseRef
                                                                                .child("Employees")
                                                                                .child(firebaseAuth.getUid())
                                                                                .setValue(employeeModel);

                                                                        databaseRef
                                                                                .child("Jobs")
                                                                                .child(job)
                                                                                .child(randomKey)
                                                                                .setValue(employeeModel);

                                                                        databaseRef
                                                                                .child("Search for Employees")
                                                                                .child(randomKey)
                                                                                .setValue(employeeModel);

                                                                        binding.imgAddPhoto.setImageURI(null);

                                                                        binding.loadingRegister.setVisibility(View.GONE);
                                                                        Toast.makeText(getActivity(), "Done", Toast.LENGTH_SHORT).show();
//                                                                        navController.navigate(R.id.action_registerFragment_to_homeFragment);
                                                                        Navigation.findNavController(view).navigate(R.id.homeFragment);
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
                }
            }
        });
    }

    private void backgroundProcess()
    {
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
        someActivityResultLauncher.launch(intent);
    }

}