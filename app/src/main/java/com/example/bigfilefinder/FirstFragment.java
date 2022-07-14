package com.example.bigfilefinder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.bigfilefinder.databinding.FragmentFirstBinding;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.HashMap;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    File[] directories;
    String actualPath;
    HashMap<String, File> choosedDirectories = new HashMap<String, File>();

    public void getDirectoriesOnPath(String path) {
        if(path.length() > 2) {
            binding.pathBackButton.setVisibility(View.VISIBLE);
        } else {
            binding.pathBackButton.setVisibility(View.INVISIBLE);
        }

        actualPath = path;
        binding.linearLayout.removeAllViews();

        File myDirectory = new File(path);

        directories = myDirectory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });

        choosedDirectories.put(directories[2].getPath(), directories[2]);
        for(File dir : directories) {
            TextView text = new TextView(binding.linearLayout.getContext());
//            text.setLayoutParams(binding.linearLayout.getLayoutParams());
            text.setText(dir.getName());
            text.setOnClickListener(click -> getDirectoriesOnPath(dir.getPath()));
            binding.linearLayout.addView(text);

            CheckBox dirIsPicked = new CheckBox(binding.linearLayout.getContext());
            dirIsPicked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View checkbox) {
                    boolean checked = ((CheckBox) checkbox).isChecked();
                    if (checked){
                        choosedDirectories.put(dir.getPath(), dir);
                    }
                    else{
                        choosedDirectories.remove(dir.getPath());
                    }
                }
            });
//            dirIsPicked.isChecked(choosedDirectories.put(dir.getPath(), dir));
            if(choosedDirectories.containsKey(dir.getPath()))
                dirIsPicked.setChecked(true);
            else
                dirIsPicked.setChecked(false);
            binding.linearLayout.addView(dirIsPicked);
        }

    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDirectoriesOnPath("./");
        binding.addDirectoryBtn.setOnClickListener(click -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("directories", choosedDirectories);
            NavHostFragment.findNavController(FirstFragment.this)
                    .navigate(R.id.action_FirstFragment_to_SecondFragment, bundle);
        });
        binding.pathBackButton.setOnClickListener(click -> getDirectoriesOnPath(actualPath.substring(0,actualPath.lastIndexOf("/"))));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}