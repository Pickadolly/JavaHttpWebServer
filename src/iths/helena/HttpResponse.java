package iths.helena;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class HttpResponse {

    HttpRequest req;
    Person p;
    PostArguments pa;
    int fileLenght;
    String response, x404 = "/404.html";
    byte[] data;
    private static final String root = "/root";
    private static final String webServerVersion = "PickaHTTPd v1.0";

    public HttpResponse(HttpRequest req) {
        //req = request;
        File getFile = new File("");
        File f = new File(getFile.getAbsolutePath() + root + req.filename);
        boolean fileExists = f.exists();
        if(!fileExists && req.filename != "/post_result.json") {
            f = new File(getFile.getAbsolutePath() + root + x404);
        }
        fileLenght = (int) f.length();
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
        String timeNow = now.format(DateTimeFormatter.RFC_1123_DATE_TIME);

        if (!req.method.equals("GET") && !req.method.equals("HEAD") && !req.method.equals("POST")) {
            response = "HTTP/1.1 501 Not Implemented\r\n";
            response += "Date: " + timeNow + "\r\n";
            response += "Server: "+ webServerVersion + "\r\n";
        } else {
            if(req.method.equals("HEAD")) {
                response = "HTTP/1.1 200 OK\r\n";
                response += "Date: " + timeNow + "\r\n";
                response += "Server: " + webServerVersion + "\r\n";
            } else if(req.method.equals("GET")) {
                response = "HTTP/1.1 200 OK\r\n";
                response += "Date: " + timeNow + "\r\n";
                response += "Server: " + webServerVersion + "\r\n";
                try {
                    FileInputStream fis = new FileInputStream(f);
                    response += "Content-Type: " + req.mimeType + "\r\n";
                    response += "Connection: close \r\n";
                    response += "Content-Length: " + fileLenght + "\r\n";
                    response += "\r\n";
                    data = new byte[fileLenght];
                    fis.read(data);
                    fis.close();
                } catch (FileNotFoundException e) {
                    response = response.replace("200", "404");
                } catch (Exception e) {
                    response = response.replace("200", "500");
                }
            } else if(req.method.equals("POST")) {
                response = "HTTP/1.1 200 OK\r\n";
                response += "Date: " + timeNow + "\r\n";
                response += "Server: " + webServerVersion + "\r\n";
                if(req.filename.equals("/post_result.json")) {
                    fileLenght = req.jsonData.length();
                    response += "Content-Type: " + req.mimeType + "\r\n";
                    response += "Connection: close \r\n";
                    response += "Content-Length: " + fileLenght + "\r\n";
                    response += "\r\n";
                    fileExists = true;
                    data = req.jsonData.getBytes();
                } else {
                    try {
                        FileInputStream fis = new FileInputStream(f);
                        response += "Content-Type: " + req.mimeType + "\r\n";
                        response += "Connection: close \r\n";
                        response += "Content-Length: " + fileLenght + "\r\n";
                        response += "\r\n";
                        data = new byte[fileLenght];
                        fis.read(data);
                        fis.close();
                    } catch (FileNotFoundException e) {
                        response = response.replace("200", "404");
                    } catch (Exception e) {
                        response = response.replace("200", "500");
                    }
                }
            }
        }
        if(!fileExists) {
            response = response.replace("text/plain", "text/html");
            response = response.replace("HTTP/1.1 200 OK", "HTTP/1.1 404 File Not Found");
        }
    }
}