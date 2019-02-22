package iths.helena;

import java.io.*;
import java.net.Socket;

//this class handles all the connection which contains the requests
public class ConnectionHandler extends Thread { //by extending to thread this class becomes a thread

    private Socket s;
    PrintWriter pw;
    BufferedReader br;
    BufferedOutputStream dataOut = null;

    //constructor
    public ConnectionHandler(Socket s) throws IOException {
        this.s = s;
        br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        pw = new PrintWriter(s.getOutputStream());
        dataOut = new BufferedOutputStream(s.getOutputStream());
    }


    //thread class contains a method run which is call automatically when we start the thread
    //in this method we have to read the request and give the response
    @Override
    public void run() {
        try {
            //in this method we get the request string and give this string to the request class
            String reqS = "";

            //from br we have to read our request
            //read until request is not get or br is ready
            while (br.ready() || reqS.length() == 0)
                reqS += (char) br.read();
            System.out.println(reqS); //for display
            HttpRequest req = new HttpRequest(reqS);

            //now we pass the http request object to the response class
            HttpResponse res = new HttpResponse(req);

            //write the final output to pw
            pw.write(res.response.toCharArray());
            pw.flush();
            if(res.data == null){
            } else {
                dataOut.write(res.data, 0, res.fileLength);
                dataOut.flush();
            }
            br.close();
            s.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
