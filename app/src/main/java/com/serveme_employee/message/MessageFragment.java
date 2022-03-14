package com.serveme_employee.message;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.serveme_employee.R;
import com.serveme_employee.adapter.MessageAdapter;
import com.serveme_employee.databinding.FragmentMessageBinding;
import com.serveme_employee.model.MessageModel;

import java.util.ArrayList;

public class MessageFragment extends Fragment
{

    private NavController navController;
    private FragmentMessageBinding binding;

    private PopupMenu popupMenu;

    private ActivityResultLauncher<Intent> someActivityResultLauncher;

    private ArrayList<MessageModel> messageModels;
    private MessageAdapter messageAdapter;

    private String idRef;
    private Uri imageResultURI, videoResultURI;
    private FirebaseAuth firebaseAuth;
    private StorageReference imageRef, videoRef;
    private DatabaseReference retriveRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentMessageBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        messageModels = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageModels);
        binding.rVMessage.setAdapter(messageAdapter);
        binding.rVMessage.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.rVMessage.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        firebaseAuth = FirebaseAuth.getInstance();
        idRef = FirebaseDatabase.getInstance().getReference().push().getKey();
        imageRef = FirebaseStorage.getInstance().getReference().child("Images").child("Messages").child(idRef);
        videoRef = FirebaseStorage.getInstance().getReference().child("Images").child("Video").child(idRef);
        retriveRef = FirebaseDatabase.getInstance().getReference();


//        retriveRef
//                .child("Employees")
//                .child(firebaseAuth.getUid())
//                .child("Works")
//                .addValueEventListener(new ValueEventListener()
//                {
//                    @SuppressLint("NotifyDataSetChanged")
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot)
//                    {
//                        binding.loadingRequest.setVisibility(View.GONE);
//                        requestModels.clear();
//                        for(DataSnapshot dataSnapshot : snapshot.getChildren())
//                        {
//                            MessageModel messageModel = dataSnapshot.getValue(MessageModel.class);
//                            messageModels.add(messageModel);
//                        }
//                        messageAdapter.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error)
//                    {
//                        binding.loadingRequest.setVisibility(View.GONE);
//                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });

        binding.imgBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                navController.navigate(R.id.action_messagesFragment_to_homeFragment);
            }
        });

        binding.imgBtnMore.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                popupMenu = new PopupMenu(getContext(), view);
                popupMenu.getMenuInflater().inflate(R.menu.message_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
                {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem)
                    {
                        switch (menuItem.getItemId())
                        {
                            case R.id.send_message_menu:
                                binding.editMessage.setVisibility(View.VISIBLE);
                                binding.sendImg.setVisibility(View.GONE);
                                binding.sendVideo.setVisibility(View.GONE);
                                break;
                            case R.id.send_image_menu:
                                binding.sendImg.setVisibility(View.VISIBLE);
                                binding.editMessage.setVisibility(View.GONE);
                                binding.sendVideo.setVisibility(View.GONE);
                                break;
                            case R.id.send_video_menu:
                                binding.sendVideo.setVisibility(View.VISIBLE);
                                binding.sendImg.setVisibility(View.GONE);
                                binding.editMessage.setVisibility(View.GONE);
                                break;
                            default:
                                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        binding.fabSend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String sendMessage = binding.editMessage.getText().toString();

                if (popupMenu.getMenu().getItem(0).getItemId() == R.id.send_message_menu)
                {
                    if (TextUtils.isEmpty(sendMessage))
                    {
                        Snackbar.make(binding.parentRelativeMessage, "Please enter your message", Snackbar.LENGTH_SHORT).show();
                        binding.editMessage.requestFocus();
                        return;
                    }
                }

                if (popupMenu.getMenu().getItem(0).getItemId() == R.id.send_image_menu)
                {
                    if (imageResultURI == null)
                    {
                        Snackbar.make(binding.parentRelativeMessage, "Please enter your image", Snackbar.LENGTH_SHORT).show();
                        return;
                    }
                }

                if (popupMenu.getMenu().getItem(0).getItemId() == R.id.send_video_menu)
                {
                    if (videoResultURI == null)
                    {
                        Snackbar.make(binding.parentRelativeMessage, "Please enter your video", Snackbar.LENGTH_SHORT).show();
                        return;
                    }
                }

                else
                {
                    String randomKey = FirebaseDatabase.getInstance().getReference().push().getKey();


                }
            }
        });

        binding.sendImg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                OpenGallery();
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