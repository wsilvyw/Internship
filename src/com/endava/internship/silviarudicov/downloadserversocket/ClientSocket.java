package com.endava.internship.silviarudicov.downloadserversocket;

import java.io.*;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by srudicov on 10/10/2016.
 */
public class ClientSocket implements Runnable{

        protected Socket clientSocket = null;

    public ClientSocket(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

    public void run() {
            try {
                System.out.println(Thread.currentThread().getName());
                InputStream inS = clientSocket.getInputStream();
                OutputStream outS = clientSocket.getOutputStream();

                try{
                    BufferedReader br = new BufferedReader(new InputStreamReader(inS));
                    String getResponse =  br.readLine();

                    System.out.println("The user wants = " + getResponse);

                    Pattern pattern = Pattern.compile("GET \\/(.*?) HTTP");
                    Matcher m = pattern.matcher(getResponse);

                    if(m.find()) {
                        if(getResponse.indexOf("favicon.ico") < 0) {
                            StringBuilder relativePath = new StringBuilder(m.group(1));
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
                        }
                    }
                }catch(IOException e)
                {
                    e.printStackTrace();
                }
                finally {
                    inS.close();
                    outS.close();
                    clientSocket.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
    }

}
