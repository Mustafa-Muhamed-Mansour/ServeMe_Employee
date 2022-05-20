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

        initViews(view);
        initDatabase();
        retriveData();
        clickViews();


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


    private void initViews(View view)
    {
        navController = Navigation.findNavController(view);
    }

    private void initDatabase()
    {
        firebaseAuth = FirebaseAuth.getInstance();
        retriveProfileRef = FirebaseDatabase.getInstance().getReference();
    }

    private void retriveData()
    {
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
    }

    private void clickViews()
    {
        binding.btnSaveProfile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                navController.navigate(R.id.action_profileFragment_to_homeFragment);
            }
        });

        binding.btnExitProfile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                firebaseAuth.signOut();
                navController.navigate(R.id.action_profileFragment_to_loginFragment);
            }
        });
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