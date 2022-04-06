package com.serveme_employee.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.View;

import com.serveme_employee.R;
import com.serveme_employee.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity
{

    private ActivityMainBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        navController = Navigation.findNavController(MainActivity.this, R.id.nav_host);
        NavigationUI.setupWithNavController(binding.bottomNavView, navController);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener()
        {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments)
            {
                switch (destination.getId())
                {
                    case R.id.splashFragment:
                    case R.id.loginFragment:
                    case R.id.registerFragment:
                    case R.id.messagesFragment:
                    case R.id.addWorkFragment:
                    case R.id.userDetailFragment:
                        binding.bottomNavView.setVisibility(View.GONE);
                        break;
                    default:
                        binding.bottomNavView.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }
}