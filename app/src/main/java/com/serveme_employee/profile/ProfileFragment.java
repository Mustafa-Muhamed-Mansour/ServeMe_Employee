package com.serveme_employee.profile;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.serveme_employee.R;
import com.serveme_employee.databinding.FragmentProfileBinding;
import com.serveme_employee.model.EmployeeModel;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment
{

    private NavController navController;
    private FragmentProfileBinding binding;

//    private ActivityResultLauncher<Intent> someActivityResultLauncher;
//    private FirebaseFirestore fireStore;
//    private String idRef;
//    private Uri profileResultURI;
    private FirebaseAuth firebaseAuth;
//    private StorageReference profileImageRef;
    private DatabaseReference retriveProfileRef;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        firebaseAuth = FirebaseAuth.getInstance();
//        idRef = FirebaseDatabase.getInstance().getReference().push().getKey();
//        profileImageRef = FirebaseStorage.getInstance().getReference().child("Images").child("Image_Employees").child(idRef);
        retriveProfileRef = FirebaseDatabase.getInstance().getReference();

        retriveProfileRef
                .child("Employees")
                .child(firebaseAuth.getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DataSnapshot>()
                {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot)
                    {
                        EmployeeModel employeeModel = dataSnapshot.getValue(EmployeeModel.class);
                        Picasso
                                .get()
                                .load(employeeModel.getImage())
                                .placeholder(R.drawable.ic_no_photo)
                                .into(binding.imgProfile);

                        if (employeeModel.getGender().equals("Male"))
                        {
                            binding.editGenderProfile.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_symbol_male, 0, 0, 0);
                            binding.editGenderProfile.setCompoundDrawablePadding(10);
                            binding.editGenderProfile.setText(employeeModel.getGender());
                        }
                        if (employeeModel.getGender().equals("Female"))
                        {
                            binding.editGenderProfile.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_symbol_woman, 0, 0, 0);
                            binding.editGenderProfile.setCompoundDrawablePadding(10);
                            binding.editGenderProfile.setText(employeeModel.getGender());
                        }

                        binding.editFnProfile.setText(employeeModel.getFirstName());
                        binding.editLnProfile.setText(employeeModel.getLastName());
                        binding.editPhoneProfile.setText(employeeModel.getPhoneNumber());
                        binding.editEmailProfile.setText(employeeModel.getEmail());
                        binding.editJobProfile.setText(employeeModel.getJob());
                    }
                }).addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnSaveProfile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                navController.navigate(R.id.action_profileFragment_to_homeFragment);
            }
        });

//        binding.imgProfile.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                OpenGallery();
//            }
//        });
//
//        binding.btnEditProfile.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                binding.editFnProfile.setCursorVisible(true);
//                binding.editFnProfile.setFocusableInTouchMode(true);
//                binding.editLnProfile.setCursorVisible(true);
//                binding.editLnProfile.setFocusableInTouchMode(true);
//                binding.editPhoneProfile.setCursorVisible(true);
//                binding.editPhoneProfile.setFocusableInTouchMode(true);
//
//                binding.editFnProfile.requestFocus();
//                return;
//            }
//        });


//        binding.btnSaveProfile.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//
//                String genderProfile = binding.editGenderProfile.getText().toString();
//                String fnProfile = binding.editFnProfile.getText().toString();
//                String lnProfile = binding.editLnProfile.getText().toString();
//                String phoneProfile = binding.editPhoneProfile.getText().toString();
//                String emailProfile = binding.editEmailProfile.getText().toString();
//                String jobProfile = binding.editJobProfile.getText().toString();
//
//                if (profileResultURI == null)
//                {
//                    Toast.makeText(getActivity(), "Please enter your image again", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                if (TextUtils.isEmpty(fnProfile))
//                {
//                    Toast.makeText(getActivity(), "Please enter your first name", Toast.LENGTH_SHORT).show();
//                    binding.editFnProfile.requestFocus();
//                    return;
//                }
//
//                if (TextUtils.isEmpty(lnProfile))
//                {
//                    Toast.makeText(getActivity(), "Please enter your last name", Toast.LENGTH_SHORT).show();
//                    binding.editLnProfile.requestFocus();
//                    return;
//                }
//
//                if (TextUtils.isEmpty(phoneProfile))
//                {
//                    Toast.makeText(getActivity(), "Please enter your phone number", Toast.LENGTH_SHORT).show();
//                    binding.editPhoneProfile.requestFocus();
//                    return;
//                }
//
//                if (TextUtils.isEmpty(emailProfile))
//                {
//                    Toast.makeText(getActivity(), "Please enter your email", Toast.LENGTH_SHORT).show();
//                    binding.editEmailProfile.requestFocus();
//                    return;
//                }
//
//                if (TextUtils.isEmpty(jobProfile))
//                {
//                    Toast.makeText(getActivity(), "Please enter your job", Toast.LENGTH_SHORT).show();
//                    binding.editJobProfile.requestFocus();
//                    return;
//                }
//
//                else
//                {
//
//
//                    String randomKey = FirebaseDatabase.getInstance().getReference().push().getKey();
//
//                    profileImageRef
//                            .putFile(profileResultURI)
//                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
//                            {
//                                @Override
//                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
//                                {
//                                    profileImageRef
//                                            .getDownloadUrl()
//                                            .addOnSuccessListener(new OnSuccessListener<Uri>()
//                                            {
//                                                @Override
//                                                public void onSuccess(Uri uri)
//                                                {
//                                                    EmployeeModel employeeModel = new EmployeeModel(randomKey, firebaseAuth.getUid(), uri.toString(), fnProfile, lnProfile, phoneProfile, emailProfile, jobProfile, genderProfile);
//
//                                                    retriveProfileRef
//                                                            .child("Employees")
//                                                            .child(firebaseAuth.getUid())
//                                                            .setValue(employeeModel);
//
//                                                    retriveProfileRef
//                                                            .child("Jobs")
//                                                            .child(jobProfile)
//                                                            .child(randomKey)
//                                                            .setValue(employeeModel);
//
////                                                    WorkModel workModel = new WorkModel();
////
////                                                    retriveProfileRef
////                                                            .child("Employees")
////                                                            .child(firebaseAuth.getUid())
////                                                            .child("Works")
////                                                            .child(randomKey)
////                                                            .setValue(workModel);
//
//                                                    binding.imgProfile.setImageURI(null);
//
//                                                }
//                                            }).addOnFailureListener(new OnFailureListener()
//                                    {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e)
//                                        {
//                                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//                                }
//                            }).addOnFailureListener(new OnFailureListener()
//                    {
//                        @Override
//                        public void onFailure(@NonNull Exception e)
//                        {
//                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
////                    EmployeeModel employeeModel = new EmployeeModel(randomKey, firebaseAuth.getUid(), binding.imgProfile.toString(), fnProfile, lnProfile, phoneProfile, emailProfile, jobProfile, genderProfile);
////
////                    retriveProfileRef
////                            .child("Employees")
////                            .child(firebaseAuth.getUid())
////                            .setValue(employeeModel);
////
////                    retriveProfileRef
////                            .child("Jobs")
////                            .child(jobProfile)
////                            .child(randomKey)
////                            .setValue(employeeModel);
//                }
//            }
//
//            });

        binding.btnExitProfile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                firebaseAuth.signOut();
                navController.navigate(R.id.action_profileFragment_to_loginFragment);
            }
        });

//        someActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>()
//        {
//            @Override
//            public void onActivityResult(ActivityResult result)
//            {
//                Intent data = result.getData();
//
//                if (result.getResultCode() == Activity.RESULT_OK && data != null && data.getData() != null)
//                {
//                    profileResultURI = data.getData();
//                    binding.imgProfile.setImageURI(profileResultURI);
//                }
//            }
//        });

    }

//    private void OpenGallery()
//    {
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType("image/*");
////        intent.setAction(Intent.ACTION_GET_CONTENT);
//        someActivityResultLauncher.launch(intent);
////        CropImage
////                .activity()
////                .start(getContext(), this);
//    }
}