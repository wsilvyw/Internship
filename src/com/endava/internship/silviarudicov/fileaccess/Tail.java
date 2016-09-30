package com.endava.internship.silviarudicov.fileaccess;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by srudicov on 9/30/2016.
 */
public class Tail {

    static String[] lines;
    static int count;
    static int rowsToShow;
    static RandomAccessFile raf;
    public static void main(String[] args) {
        try {
            raf = new RandomAccessFile(args[0], "r");
            long fileLength = raf.length();
            System.out.println("fileLength="+fileLength);
            rowsToShow = Integer.parseInt(args[1]);
            long position = fileLength - fileLength / rowsToShow ;

            count = 0;
            while(count <= rowsToShow){
                raf.seek(position);
                position = iterateThrough(position);
            }
            for (int i = 1; i < lines.length; i++) {
                System.out.println(lines[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static long iterateThrough(long position) throws IOException {
        lines = new String[rowsToShow+1];
        count = 0;
        String line = null;
        while ((line = raf.readLine()) != null) {
            lines[count % lines.length] = line;
            count++;
        }
        return position/2;
    }
}
