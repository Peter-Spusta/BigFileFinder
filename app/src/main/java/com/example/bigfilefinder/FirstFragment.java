package com.example.bigfilefinder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.bigfilefinder.databinding.FragmentFirstBinding;

import java.io.File;
import java.util.HashMap;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    FileService fs = new FileService();
    String actualPath;
    HashMap<String, File> choosedDirectories = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        actualPath = fs.getDirectoriesOnPath("./", choosedDirectories, binding);

        binding.addDirectoryBtn.setOnClickListener(click -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("directories", choosedDirectories);
            NavHostFragment.findNavController(FirstFragment.this)
                    .navigate(R.id.action_FirstFragment_to_SecondFragment, bundle);
        });

        binding.pathBackButton.setOnClickListener(click -> fs.getDirectoriesOnPath(actualPath.substring(0,actualPath.lastIndexOf("/")), choosedDirectories, binding));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}