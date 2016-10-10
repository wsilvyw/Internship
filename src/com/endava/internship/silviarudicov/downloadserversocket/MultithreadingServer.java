package com.endava.internship.silviarudicov.downloadserversocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by srudicov on 10/10/2016.
 */
public class MultithreadingServer implements Runnable{

    protected int          serverPort    = 9090;
    protected ServerSocket serverSocket  = null;
    protected boolean      isStopped     = false;
    protected Thread       runningThread = null;
    protected ExecutorService threadPool = Executors.newFixedThreadPool(10); //schedules work to be done

    public MultithreadingServer(int port){
        this.serverPort = port;
    }

    public void run(){
        synchronized(this){
            this.runningThread = Thread.currentThread();
        }

        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port " + this.serverPort, e);
        }

        while(! isStopped()){
            Socket clientSocket = null;
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                if(isStopped()) {
                    System.out.println("Server already stopped.") ;
                    break;
                }
                throw new RuntimeException("Error accepting client connection", e);
            }
            this.threadPool.execute( new ClientSocket(clientSocket) );
        }

        this.threadPool.shutdown();
        System.out.println("Server is stopped.") ;
    }
    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop(){
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    public static void main(String[] args) {
        MultithreadingServer server = new MultithreadingServer(9090);
        new Thread(server).start();

        try {
            Thread.sleep(40 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Stopping Server");
        server.stop();
    }
}