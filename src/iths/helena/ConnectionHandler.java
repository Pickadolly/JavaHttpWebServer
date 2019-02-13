package iths.helena;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

//this class handles all the connection which contains the requests
public class ConnectionHandler extends Thread { //by extending to thread this class becomes a thread

    Socket s;

    //for sending the output to client
    PrintWriter pw;

    //for getting the input from client
    BufferedReader br;

    //constructor
    public ConnectionHandler(Socket s) throws IOException {
        this.s = s;
        br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        pw = new PrintWriter(s.getOutputStream());
    }


    //thread class contains a method run which is call automatically when we start the thread
    //in this method we have to read the request and give the response
    @Override
    public void run() {
        try {
            //in this method we get the request string and give this string to the request class
            String reqS = "";

            //from br we have to read our request
            while (br.ready())
                reqS += (char) br.read();
            System.out.println(reqS); //for display
            HttpRequest req = new HttpRequest(reqS);

            //now we pass the httprequest object to the response class
            HttpResponse res = new HttpResponse(req);

            //write the final output to pw
            pw.write(res.response.toCharArray());
            s.close();
            pw.close();
            br.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
