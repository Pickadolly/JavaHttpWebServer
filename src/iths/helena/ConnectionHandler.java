package iths.helena;

import java.io.*;
import java.net.Socket;

//this class handles all the connection which contains the requests
public class ConnectionHandler extends Thread { //by extending to thread this class becomes a thread

    private Socket s;

    //for sending the output to client
    PrintWriter pw;

    //for getting the input from client
    BufferedReader br;

    BufferedOutputStream bos = null;

    //constructor
    public ConnectionHandler(Socket s) throws IOException {
        this.s = s;
        br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        pw = new PrintWriter(s.getOutputStream());
        bos = new BufferedOutputStream(s.getOutputStream());
    }

    //thread class contains a method run which is call automatically when we start the thread
    //in this method we have to read the request and give the response
    @Override
    public void run() {
        try {
            //in this method we get the request string and give this string to the request class
            String reqS = "";
            StringBuilder sb = new StringBuilder();

            //from br we have to read our request
            //read until request is not get or br is ready
            while (br.ready() || sb.length() == 0)
                sb.append((char) br.read());
            System.out.println("----------------------------------------------------");
            System.out.println("HTTP REQUEST:");
            System.out.println(sb.toString()); //for display

            try {
                HttpRequest req = new HttpRequest(sb.toString());
                //now we pass the http request object to the response class
                HttpResponse res = new HttpResponse(req);
                //write the final output to pw
                System.out.println("----------------------------------------------------");
                System.out.println("HTTP RESPONSE:");
                System.out.println(res.response.toCharArray());
                pw.write(res.response.toCharArray());
                pw.flush();
                if (res.data == null) {
                } else {
                    bos.write(res.data, 0, res.fileLenght);
                    bos.flush();
                }
                bos.close();
                br.close();
                s.close();
            } catch (Exception a){
                a.printStackTrace();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}