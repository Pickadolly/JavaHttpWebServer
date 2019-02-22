package iths.helena;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class HttpResponse {

    HttpRequest req;
    //this is the final response which is generated
    int fileLength;
    String response, root = "C/root";
    //root path of the server, this is nothing but the folder which contains all the files
    byte[] data;
    static final String webServerVersion = "PickaHTTPd v1.0";

    public HttpResponse(HttpRequest request) {
        req = request;
        //now we have to open the file mentioned in request
        File f = new File(root + "/" + req.filename);
        fileLength = (int) f.length();
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
        String timeNow = now.format(DateTimeFormatter.RFC_1123_DATE_TIME);

        if (!req.method.equals("GET") && !req.method.equals("HEAD")) {
            response = "HTTP/1.1 501 Not Implemented\r\n";
            response += "Date: " + timeNow + "\r\n";
            response += "Server: " + webServerVersion + "\r\n";
        } else {
            if (req.method.equals("GET")) {
                response += "HTTP/1.1 200 OK\r\n";
                response += "Date: " + timeNow + "\r\n";
                response += "Server: " + webServerVersion + "\r\n";
                try {
                    FileInputStream fis = new FileInputStream(f);
                    response += "Content-Type: " + req.mimeType + "\r\n";
                    response += "Connection: close \r\n";
                    response += "Content-Length: " + fileLength + "\r\n";
                    response += "\r\n";
                    data = new byte[fileLength];
                    fis.read(data);
                    fis.close();
                } catch (FileNotFoundException e) {
                    //if we dont get a file then error 404
                    response = response.replace("200", "404");
                } catch (Exception e) {
                    //if other error then 500 internal server error
                    response = response.replace("200", "500");
                }
            }
        }
    }
}
