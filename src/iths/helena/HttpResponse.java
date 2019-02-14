package iths.helena;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class HttpResponse {

    HttpRequest req;
    //this is the final response which is generated
    String response;
    //root path of the server, this is nothing but the folder which contains all the files
    String root = "C:/root";

    public HttpResponse(HttpRequest request) {
        req = request;
        //now we have to open the file mentioned in request
        File f = new File(root + req.filename);

        try {
            //to read this file
            FileInputStream fis = new FileInputStream(f);

            response = "HTTP/1.1 200";  //version of http and 200 for status code
                                        //200 means everything is ok
            response += "Server: Our Java Server/1.0 \r\n"; //identity of the server
            response += "Content-Type: text/html\r\n"; //response is html format
            response += "Connection: close \r\n"; //this line tells the browser to close the connection
            response += "Content-Length: " + f.length() + " \r\n"; //length of response file
            response += "\r\n"; //after blank line we have to append file data
            int s;
            while ((s = fis.read()) != -1){ //-1 means end of file
                response += (char) s;
            }
            fis.close();
        } catch (FileNotFoundException e){
            //if we dont get a file then error 404
            response = response.replace("200", "404");
        }
        catch (Exception e){
            //if other error then 500 internal server error
            response = response.replace("200", "500");
        }
    }
}
