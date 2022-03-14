package com.serveme_employee.login;

import android.annotation.SuppressLint;
import android.os.Bundle;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.serveme_employee.R;
import com.serveme_employee.databinding.FragmentLoginBinding;


public class LoginFragment extends Fragment
{

    private NavController navController;
    private FragmentLoginBinding binding;
    private FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        firebaseAuth = FirebaseAuth.getInstance();

        binding.btnCreateNewAccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                navController.navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @SuppressLint("ShowToast")
            @Override
            public void onClick(View view)
            {
                String email = binding.editEmailLogin.getText().toString();
                String password = binding.editPasswordLogin.getText().toString();

                if (TextUtils.isEmpty(email))
                {
                    Snackbar.make(binding.parentNestedLogin, "Please enter your email", Snackbar.LENGTH_SHORT).show();
                    binding.editEmailLogin.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(password))
                {
                    Snackbar.make(binding.parentNestedLogin, "Please enter your password", Snackbar.LENGTH_SHORT).show();
                    binding.editPasswordLogin.requestFocus();
                    return;
                }

                else
                {
                    firebaseAuth
                            .signInWithEmailAndPassword(email, password)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>()
                            {
                                @Override
                                public void onSuccess(AuthResult authResult)
                                {
                                    navController.navigate(R.id.action_loginFragment_to_homeFragment);
                                    Toast.makeText(getActivity(), "Welcome to Home", Toast.LENGTH_SHORT).show();
                                    // Snackbar.make(binding.parentRelativeLogin, "lolololole", Snackbar.LENGTH_SHORT).isShown();
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
            }
        });


    }
}