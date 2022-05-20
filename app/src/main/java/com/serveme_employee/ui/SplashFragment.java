package com.serveme_employee.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.serveme_employee.R;
import com.serveme_employee.databinding.FragmentSplashBinding;
import com.serveme_employee.login.LoginFragment;


public class SplashFragment extends Fragment
{

    private FragmentSplashBinding binding;
    private NavController navController;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentSplashBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        initPostDelay(view);

    }

    private void initViews(View view)
    {
        navController = Navigation.findNavController(view);
    }

    private void initPostDelay(View view)
    {
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
//                navController.navigate(R.id.homeFragment);
//                navController.navigate(R.id.action_splashFragment_to_loginFragment);
                Navigation.findNavController(view).navigate(R.id.loginFragment);
            }
        }, 3000); /* or 2300 */
    }
}