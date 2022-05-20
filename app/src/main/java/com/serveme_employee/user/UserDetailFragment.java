package com.serveme_employee.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.serveme_employee.R;
import com.serveme_employee.databinding.FragmentUserDetailBinding;
import com.squareup.picasso.Picasso;

public class UserDetailFragment extends Fragment
{

    private NavController navController;
    private FragmentUserDetailBinding binding;

    private String id, nameUser, gender, image, phoneNumber, email;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentUserDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);


        initViews(view);
        retriveBundle();

    }

    private void initViews(View view)
    {
        navController = Navigation.findNavController(view);
    }

    private void retriveBundle()
    {
        nameUser = getArguments().getString("User_Name");
        binding.txtUserName.setText(nameUser);

        gender = getArguments().getString("User_Gender");
        if (gender.equals("Male"))
        {
            binding.txtUserGender.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_symbol_male, 0, 0, 0);
            binding.txtUserGender.setCompoundDrawablePadding(10);
            binding.txtUserGender.setText(gender);
        }
        if (gender.equals("Female"))
        {
            binding.txtUserGender.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_symbol_woman, 0, 0, 0);
            binding.txtUserGender.setCompoundDrawablePadding(10);
            binding.txtUserGender.setText(gender);
        }
//        gender = getArguments().getString("User_Gender");
//        binding.txtUserGender.setText(gender);

        image = getArguments().getString("User_Image");
        Picasso
                .get()
                .load(image)
                .into(binding.imgUserDetail);

        phoneNumber = getArguments().getString("User_Phone");
        binding.fabPhoneNumber.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().startActivity(intent);
            }
        });

//        email = getArguments().getString("User_Email");
//        binding.fabEmail.setTextAlignment(email);
    }
}