package com.endava.internship.silviarudicov.downloadviasocket;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by srudicov on 10/6/2016.
 */
public class Download {

    public static void main(String[] args) throws IOException{

        Socket s = new Socket(InetAddress.getByName("i.imgur.com"), 80);
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())));
                    // Socket class implements client sockets (also called just "sockets"). A socket is an endpoint for communication between two machines.
                    // Socket(InetAddress address, int port) Creates a stream socket and connects it to the specified port number at the specified IP address.
                    // InetAddress class represents an Internet Protocol (IP) address.
                    //	getByName(String host) Determines the IP address(InetAddress) of a host, given the host's name


                    // PrintWriter - Prints formatted representations of objects to a text-output stream
                    // BufferedWriter - Writes text to a character-output stream, buffering characters so as to provide for the efficient writing of single characters, arrays, and strings.
                    // An OutputStreamWriter is a bridge from character streams to byte streams: Characters written to it are encoded into bytes using a specified charset.

        //PrintWriter(Writer out)   Creates a new PrintWriter, without automatic line flushing.
        //BufferedWriter(Writer out)  Creates a buffered character-output stream that uses a default-sized output buffer.
        //OutputStreamWriter(OutputStream out)  Creates an OutputStreamWriter that uses the default character encoding.

        out.println("GET /H1USigj.gif HTTP/1.1"); //Prints a string
        out.println("Host: i.imgur.com\r\n");
        out.flush();  //Flushes the stream

        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

        String inputLine;
        //int count = 0;

        while ((inputLine = in.readLine()) != null) {
            //count++;
            //System.out.println(count + " " + inputLine);
        }

        out.close();
        in.close();
        s.close();
    }

}
