package com.example.bigfilefinder;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.bigfilefinder.databinding.FragmentSecondBinding;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.PriorityQueue;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private HashMap<String, File> directories;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.searchFilesBtn.setVisibility(View.INVISIBLE);
        directories = (HashMap<String, File>)getArguments().getSerializable("directories");

        binding.buttonSecond.setOnClickListener(view1 -> NavHostFragment.findNavController(SecondFragment.this)
                .navigate(R.id.action_SecondFragment_to_FirstFragment));

        binding.searchFilesBtn.setOnClickListener(click -> {

        });

        binding.reqFileCntInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(Integer.parseInt(binding.reqFileCntInput.getText().toString()) > 0)
                    binding.searchFilesBtn.setVisibility(View.VISIBLE);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public File[] findNBigestFiles(Integer n, HashMap<String, File> directories) {
        PriorityQueue<HeapFile> maxHeap = new PriorityQueue<HeapFile>(
                Collections.reverseOrder());

        //todo directories -> all file to HeapFile, add them to heap, get n biggest from heap
        directories.get("./config").length();
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}