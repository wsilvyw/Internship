package com.endava.internship.silviarudicov.downloadserversocket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by srudicov on 10/7/2016.
 */
public class Server {

    public static void main(String args[]) throws IOException {
        ServerSocket serverSocket = null;
        Socket socket = null;
        InputStream inS = null;
        OutputStream outS = null;

        try
        {
            serverSocket = new ServerSocket(9090);
            while(true)
            {
                socket = serverSocket.accept();
                inS = socket.getInputStream();
                outS = socket.getOutputStream();


                try{
                    BufferedReader br = new BufferedReader(new InputStreamReader(inS));
                    String getResponse =  br.readLine();
                    System.out.println("The user wants = " + getResponse);

                    int i = getResponse.indexOf("/");
                    String fileN = getResponse.substring(i+1);
                    StringBuilder relativePath = new StringBuilder(fileN.substring(0, fileN.indexOf(' ')));
                    relativePath.insert(1, ':');

                    File file = new File(relativePath.toString());
                    FileInputStream fis = new FileInputStream(file);
                    byte[] data = new byte[(int) file.length()];
                    fis.read(data);
                    fis.close();


                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outS));
                    DataOutputStream binaryOut = new DataOutputStream(outS);
                    binaryOut.writeBytes("HTTP/1.0 200 OK\r\n");
                    binaryOut.writeBytes("Content-Type: image/png\r\n");
                    binaryOut.writeBytes("Content-Length: " + data.length);
                    binaryOut.writeBytes("\r\n\r\n");
                    binaryOut.write(data);
                    binaryOut.close();

                    bw.close();
                }catch(Exception e)
                {
                   e.printStackTrace();
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

        inS.close();
        outS.close();
        socket.close();
        serverSocket.close();
    }
}
