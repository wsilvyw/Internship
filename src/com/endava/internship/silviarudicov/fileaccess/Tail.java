package com.endava.internship.silviarudicov.fileaccess;

import java.io.*;

/**
 * Created by srudicov on 9/30/2016.
 */
public class Tail {

    RandomAccessFile raf;
    long fileLength;
    int rowsToShow;
    int averageLineLength;
    String[] lines;
    int count;
    long lastPosition;
    long position;

    public static void main(String[] args) {
        try {
            Tail tail = new Tail();
            tail.readFile();

            tail.findLines();

            tail.printLogs();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readFile() throws IOException{
        raf = new RandomAccessFile("D:\\Silvia\\JavaProg\\Internship\\logs.log","r");//(args[0], "r");

        fileLength = raf.length();

        rowsToShow = 3; //Integer.parseInt(args[1]);
    }

    private void findLines() throws IOException{
        if(fileLength>rowsToShow){
            determineAverageLineLength();
            position = fileLength ;

            while(count <= rowsToShow) {
                if(position<0) {
                    System.out.println("in ifff");
                    count = 1;
                    raf.seek(0);
                    iterateThrough();
                    break;
                }
                iterateThrough();
            }
            /*
            if(count > rowsToShow){
                System.out.println(position+ "=position");
                System.out.println(lastPosition+ "=lastPosition");
                position += (lastPosition-position)/2;
                System.out.println(position+ "=position");
                count=0;
                while(count <= rowsToShow) {
                    iterateThrough();
                }
            }*/
        }else{
            lines[1] = raf.readLine();
        }
    }

    private void determineAverageLineLength() throws IOException{
        String line;
        int sumOfAverageLineLength=0;
        int countIterations = 0;
        int currentPosition = 0;
        raf.seek(currentPosition);

        while ((countIterations < 10) && (line = raf.readLine())!= null) {
            if(line.length() > 0){
                countIterations++;              // only in case of non-null lines
            }
            int lineLength = line.length()+1; // +1 (the character '/n')
            sumOfAverageLineLength+=lineLength;
            currentPosition+=lineLength;
            raf.seek(currentPosition);
        }

        averageLineLength = sumOfAverageLineLength / countIterations;
    }

    private void iterateThrough() throws IOException {
        count = 0;
        lastPosition = position;
        position -= averageLineLength * (rowsToShow>1?(rowsToShow-1):1);
        raf.seek(position);
        lines = new String[rowsToShow*2]; // in first cell will be the end of a non-entire previous line
        String line;
        while ((line = raf.readLine()) != null) {
            lines[count % 10] = line;
            count++;
        }
    }

    private void printLogs() {
        for (int i = 1; i < lines.length && lines[i] != null; i++) {  // in first cell will be the end of a non-entire previous line
            System.out.println(lines[i]);
        }
    }
}
