package iths.helena;

import java.net.FileNameMap;
import java.net.URLConnection;

public class HttpRequest {

    //first line contain 3 parts
    //1 request type, 2 request file name and 3 request http version
    //and for us file name is important
    String method, mimeType, filename, ext;

    //here we have to create a constructor that accepts a string
    public HttpRequest(String request){

        String lines[] = request.split("\r\n");
        method = lines[0].split(" ")[0];
        filename = lines[0].split(" ")[1];
        filename = filename.substring(1);
        ext = filename.split("\\.")[1];
        if (!ext.equals("css") && !ext.equals("js")) {
            FileNameMap fileNameMap = URLConnection.getFileNameMap();
            mimeType = fileNameMap.getContentTypeFor(filename);
        } else {
            if(ext.equals("css")) {
                mimeType = "text/css";
            } else if(ext.equals("js")) {
                mimeType = "text/javascript";
            } else {
                mimeType = "text/plain";
            }
        }

    }

}
