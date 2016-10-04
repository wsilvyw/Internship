package com.endava.internship.silviarudicov.comparepackages;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by srudicov on 10/4/2016.
 */
public class CompareDirectories {

    static String firstDirectory;
    static String secondDirectory;

    public static void main(String[] args) {
        List<String> filesFirstDirectory = new ArrayList<>();
        List<String> directoriesFirstDirectory = new ArrayList<>();
        firstDirectory = "D:\\Internship\\root1\\";
        listFiles(firstDirectory, filesFirstDirectory, directoriesFirstDirectory, firstDirectory);

        List<String> filesSecondDirectory = new ArrayList<>();
        List<String> directoriesSecondDirectory = new ArrayList<>();
        secondDirectory = "D:\\Internship\\root2\\";
        listFiles(secondDirectory, filesSecondDirectory, directoriesSecondDirectory, secondDirectory);

        System.out.println("\nDiff [" + firstDirectory + "] [" + secondDirectory + "]");
        findDiffs(filesFirstDirectory, directoriesFirstDirectory, filesSecondDirectory, directoriesSecondDirectory);


        System.out.println("\nDiff [" + secondDirectory + "] [" + firstDirectory + "]");
        findDiffs(filesSecondDirectory, directoriesSecondDirectory, filesFirstDirectory, directoriesFirstDirectory);
    }


    // gets all the files and subdirectories from a directory
    private static void listFiles(String directoryName, List<String> files, List<String> directories, String mainDirectory) {
        File directory = new File(directoryName);
        File[] fList = directory.listFiles();
        for (File file : fList) {
            if (file.isFile()) {
                files.add(file.getAbsolutePath().replace(mainDirectory,""));
            } else if (file.isDirectory()) {
                listFiles(file.getAbsolutePath(), files, directories, mainDirectory);
                directories.add(file.getAbsolutePath().replace(mainDirectory,""));
            }
        }
    }

    private static void findDiffs(List<String> filesDir1, List<String> directoriesDir1, List<String> filesDir2, List<String> directoriesDir2){
        try {
            for (String directory : directoriesDir1) {
                if (directoriesDir2.indexOf(directory) < 0) {
                    System.out.println(directory);
                }
            }

            for (String file : filesDir1) {
                if (filesDir2.indexOf(file) < 0) {
                    System.out.println(file);
                } else {
                    File file1 = new File(firstDirectory + file);
                    FileReader fileReeader1 = new FileReader(file1);
                    File file2 = new File(secondDirectory + file);
                    FileReader fileReeader2 = new FileReader(file2);

                    if(file1.length() == file2.length()){

                        BufferedReader br1 = null;
                        BufferedReader br2 = null;
                        String sCurrentLine1 = null;
                        String sCurrentLine2 = null;
                        List<String> list1 = new ArrayList<>();
                        List<String> list2 = new ArrayList<>();

                        br1 = new BufferedReader(fileReeader1);
                        br2 = new BufferedReader(fileReeader2);
                        while ((sCurrentLine1 = br1.readLine()) != null | (sCurrentLine2 = br2.readLine()) != null) {
                            if(!sCurrentLine1.equals(sCurrentLine2)){
                                System.out.println(file);
                                break;
                            }
                        }
                    }
                }
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
