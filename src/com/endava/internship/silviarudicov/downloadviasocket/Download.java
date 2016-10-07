package com.endava.internship.silviarudicov.downloadviasocket;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by srudicov on 10/6/2016.
 */
public class Download {

    public static void main(String[] args) throws IOException{
        Socket socket = null;
        PrintWriter out = null;
        InputStream is = null;
        OutputStream os = null;

        try {
            String host = "i.imgur.com";
            String fileAbsolutePath = "H1USigj.gif";
            String outputFileAbsolutePath = "image.gif";
            socket = new Socket(InetAddress.getByName(host), 80);
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));

            out.println("GET " + fileAbsolutePath + " HTTP/1.1"); //Prints a string
            out.println("Host: " + host + "\r\n");
            out.flush();  //Flushes the stream

            is = socket.getInputStream();
            os = new FileOutputStream(outputFileAbsolutePath);

            boolean headerEnded = false;
            //browsers have limits ranging on the 2048 character allowed in get method
            byte[] bytes = new byte[2048];
            byte[] searchBytes  = {13,10,13,10};
/*
            readAfter(is, searchBytes, data -> {

            });
*/
            int length;
            while ((length = is.read(bytes)) != -1) {
                System.out.println("length=" + length);
                // If the end of the header had already been reached, write the bytes to the file as normal.
                if (headerEnded) {
                    os.write(bytes, 0, length);
                    os.flush();
                }
                // Locates the end of the header by comparing the current byte as well as the next 3 bytes
                // with the HTTP header end "\r\n\r\n" (which in integer representation would be 13 10 13 10)
                else {
                    for (int i = 0; i < length; i++) {
                        if (bytes[i] == 13 && bytes[i + 1] == 10 && bytes[i + 2] == 13 && bytes[i + 3] == 10) {
                            System.out.println(i + "=i");
                            headerEnded = true;
                            os.write(bytes, i + 4, length - i - 4);
                            os.flush();
                            break;
                        }
                    }
                }
            }
        }finally{
            if(os != null)
                os.close();
            if(is != null)
                is.close();
            if(out != null)
                out.close();
            if(socket != null)
                socket.close();
        }
    }
/*
    public static void readAfter(InputStream in, byte[] searchBytes, StreamWriter sw) {
        //sw.write(data);
    }

    interface StreamWriter {
        void write(byte[] data);
    }*/

}
