package iths.helena;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    ServerSocket serverSocket;


    //entry point
    public static void main(String[] args) throws IOException {

        //first get the request from browser in request class
        //after processing the request into the response class which generate the output back to the browser
        //to accept the request from browser we need the class ServerSocket that creates a specific port number
        //next step is to accept the clients ie browser

        new Main().runServer(); //to avoid any problem with static fields


    }

    public void runServer() throws IOException {
        System.out.println("Server is started");
        serverSocket = new ServerSocket(6543); //port number at wich server is running

        //for accepting requests
        acceptRequests();
    }

    private void acceptRequests() throws IOException {
        while(true) { //we have to accept all the requests
            //connection to client is in the form of socket which contain the stream for input and output
            Socket s = serverSocket.accept();
            ConnectionHandler ch = new ConnectionHandler(s);

            //ch is the thread, so we have to start our thread using
            ch.start(); //this will call the run method automatically
        }
    }


    //understanding the request
    //there are 4 types of requests out 4 2 are important GET and POST
    //this is the GET request:
    // / is indicate the browser need for the root file
    //HTTP/1.1 indicate http version
    //2nd line indicate which browser has sent the request and browser running which javascript engine and on which OS and what version of browser
    //for this application only the first line is important

}
