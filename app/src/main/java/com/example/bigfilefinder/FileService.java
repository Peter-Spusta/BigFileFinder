package com.example.bigfilefinder;

import android.os.Build;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.bigfilefinder.databinding.FragmentFirstBinding;
import com.example.bigfilefinder.databinding.FragmentSecondBinding;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class FileService {

    File[] directories;

    /**
     * find all directories on given path and write list of them on screen
     * @param path path of searched directory
     * @param choosedDirectories HashMap of selected directories
     * @param binding
     * @return
     */
    public String getDirectoriesOnPath(String path, HashMap<String, File> choosedDirectories, FragmentFirstBinding binding) {
        setBackBtnVisibility(path, binding);

        binding.actualPathView.setText("Location: " + path);
        binding.linearLayout.removeAllViews();

        directories = getDirectoriesFromPath(path);

        if(directories != null && directories.length > 0) {
            for (File dir : directories) {
                addDirectoryToView(dir, choosedDirectories, binding);
                addCheckBoxToDirectory(choosedDirectories, dir, binding);
            }
        }
        return path;
    }

    /**
     * set back button visibility if there is folder to get back to
     * @param path path of actual directory
     * @param binding
     */
    public void setBackBtnVisibility(String path, FragmentFirstBinding binding) {
        if(path.length() > 2) {
            binding.pathBackButton.setVisibility(View.VISIBLE);
        } else {
            binding.pathBackButton.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * get list of directories on given path
     * @param path path where directories are searched
     * @return
     */
    public File[] getDirectoriesFromPath(String path) {
        File myDirectory = new File(path);
        return myDirectory.listFiles(File::isDirectory);
    }

    /**
     * add directory to view
     * @param dir
     * @param choosedDirectories HashMap of selected directories
     * @param binding
     */
    public void addDirectoryToView(File dir, HashMap<String, File> choosedDirectories, FragmentFirstBinding binding) {
        TextView text = new TextView(binding.linearLayout.getContext());
        text.setText(dir.getName());
        text.setOnClickListener(click -> getDirectoriesOnPath(dir.getPath(), choosedDirectories, binding));
        binding.linearLayout.addView(text);
    }

    /**
     * add checkbox to view and handle check
     * @param choosedDirectories HashMap of selected directories
     * @param dir directory that belong to given checkbox
     * @param binding
     */
    public void addCheckBoxToDirectory(HashMap<String, File> choosedDirectories, File dir, FragmentFirstBinding binding) {
        CheckBox dirIsPicked = new CheckBox(binding.linearLayout.getContext());
        dirIsPicked.setOnClickListener(checkbox -> {
            boolean checked = ((CheckBox) checkbox).isChecked();
            if (checked) {
                choosedDirectories.put(dir.getPath(), dir);
            } else {
                choosedDirectories.remove(dir.getPath());
            }
        });

        dirIsPicked.setChecked(choosedDirectories.containsKey(dir.getPath()));

        binding.linearLayout.addView(dirIsPicked);
    }

    /**
     * find N biggest file via maxheap
     * @param directories HashMap of selected directories
     * @param binding
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void findNBigestFiles(HashMap<String, File> directories, FragmentSecondBinding binding) {

        binding.linearLayout.removeAllViews();

        int n = Integer.parseInt(binding.reqFileCntInput.getText().toString());

        PriorityQueue<File> maxHeap = new PriorityQueue<>(
                Integer.parseInt(binding.reqFileCntInput.getText().toString()),
                (heapFile, t1) -> Long.compare(t1.length(), heapFile.length()));

        for (Map.Entry<String, File> dir : directories.entrySet()) {
            addFilesFromDirToHeap(dir, maxHeap);
        }

        if (maxHeap.isEmpty()) {
            addTextToView("0 Files in selected directories.", binding);
            return;
        }

        for (; n > 0; n--) {
            if (maxHeap.isEmpty()) {
                break;
            }
            addTextToView(createTextForViewFromFile(maxHeap.poll()), binding);
        }
    }

    /**
     * add text to view
     * @param content text that will be shown on view
     * @param binding
     */
    public void addTextToView(String content, FragmentSecondBinding binding) {
        TextView text = new TextView(binding.linearLayout.getContext());
        text.setText(content);
        binding.linearLayout.addView(text);
    }

    /**
     * get text for file view
     * @param file
     * @return
     */
    public String createTextForViewFromFile(File file) {
        return "Name: " + file.getName() + "\nPath: " + file.getPath() +"\nSize: " + file.length() + "\n";
    }

    /**
     * add files to maxheap
     * @param dir
     * @param maxHeap
     */
    public void addFilesFromDirToHeap(Map.Entry<String, File> dir, PriorityQueue<File> maxHeap) {
        File[] files = dir.getValue().listFiles(File::isFile);

        if(files != null && files.length > 0) {
            maxHeap.addAll(Arrays.asList(files));
        }
    }
}
