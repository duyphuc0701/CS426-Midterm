package com.example.travelapplication.fragments;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.travelapplication.R;
import com.example.travelapplication.databinding.FragmentPersonalInfoBinding;
import com.example.travelapplication.utils.UserPreferences;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class PersonalInfoFragment extends Fragment {
    FragmentPersonalInfoBinding binding;
    private ActivityResultLauncher<String> mGetContent;
    String currentImagePath = null;
    public PersonalInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPersonalInfoBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        loadUserData();

        initBackButton();

        initSaveChangesButton();

        // Init camera
        initCameraGetContent();

        initCameraImageButton();

        return rootView;
    }

    private void initCameraGetContent() {
        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri o) {
                        if(o != null) {
                            try {
                                InputStream inputStream =
                                        getActivity().getContentResolver()
                                                .openInputStream(o);
                                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                String imagePath = saveToInternalStorage(bitmap);
                                binding.personalInfoAvatarImage.setImageBitmap(bitmap);
                                currentImagePath = imagePath;
                            } catch (IOException e) {
                                Log.e("IO error", "Cannot set image - get content");
                            }
                        }
                    }
                });
    }

    private String saveToInternalStorage(Bitmap bitmap) {
        ContextWrapper cw = new ContextWrapper(getContext());
        // Path to /data/data/your_app/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File myPath = new File(directory, "profile.jpg");

        FileOutputStream fos;
        try {
            fos = new FileOutputStream(myPath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            Log.e("IO Error", "Cannot save to internal storage");
        }
        return myPath.getAbsolutePath();
    }

    private void initCameraImageButton() {
        binding.cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Allow user choose image from gallery
                mGetContent.launch("image/*");
            }
        });
    }

    private void initSaveChangesButton() {
        binding.saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get current data to save
                FragmentActivity activity = requireActivity();
                String firstName = binding.firstNameEdit.getText().toString();
                String lastName = binding.lastNameEdit.getText().toString();
                String phone = binding.phoneEdit.getText().toString();
                String email = binding.emailEdit.getText().toString();
                // Save user data using SharedPreferences
                UserPreferences.saveUserData(activity, firstName,
                        lastName, phone, email, currentImagePath);
                // Display message for user
                Toast.makeText(activity, "Save changes successfully!", Toast.LENGTH_SHORT).show();
                // Go back to AccountFragment
                activity.getSupportFragmentManager().popBackStack();
            }
        });
    }

    private void loadUserData() {
        SharedPreferences preferences = requireActivity()
                .getSharedPreferences(UserPreferences.PREFS_NAME, Context.MODE_PRIVATE);

        binding.firstNameEdit.setText(preferences.getString(UserPreferences.KEY_FIRST_NAME, getResources().getString(R.string.victoria)));
        binding.lastNameEdit.setText(preferences.getString(UserPreferences.KEY_LAST_NAME, getResources().getString(R.string.yoker)));
        binding.phoneEdit.setText(preferences.getString(UserPreferences.KEY_PHONE, getResources().getString(R.string.samplePhoneNumber)));
        binding.emailEdit.setText(preferences.getString(UserPreferences.KEY_EMAIL, getResources().getString(R.string.sampleEmailAddress)));
        // Retrieve the saved image path and set it to the ImageView
        String imagePath = preferences.getString(UserPreferences.KEY_IMAGE_PATH, null);
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            binding.personalInfoAvatarImage.setImageBitmap(bitmap);
        }
    }

    private void initBackButton() {
        binding.personalInfoBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }
}