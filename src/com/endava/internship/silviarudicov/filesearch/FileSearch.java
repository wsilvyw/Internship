package com.endava.internship.silviarudicov.filesearch;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by srudicov on 10/3/2016.
 */
public class FileSearch {

    public static void main(String[] args) {
        try {

            List<File> files = new ArrayList<File>();
            listf("D:\\Internship",files);

            for (File file:files) {
                try (BufferedReader bf = new BufferedReader(new FileReader(file))) {
                    Pattern p = Pattern.compile("text in file", Pattern.CASE_INSENSITIVE);
                    String line;
                    int linecount = 0;
                    List<Integer> lines = new ArrayList<>();
                    while ((line = bf.readLine()) != null) {
                        linecount++;

                        Matcher m = p.matcher(line);

                        while (m.find()) {
                            lines.add(linecount);
                        }
                    }
                    if(!lines.isEmpty()){
                        System.out.print(file.getAbsolutePath() + " lines(");
                        for (Integer nr: lines) {
                            System.out.print(nr + " ");
                        }
                        System.out.println(")");
                    }
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    public static void listf(String directoryName, List<File> files) {
        File directory = new File(directoryName);

        // get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList) {
            if (file.isFile() && file.getName().endsWith(".txt")) {
                files.add(file);
            } else if (file.isDirectory()) {
                listf(file.getAbsolutePath(), files);
            }
        }
    }
}
