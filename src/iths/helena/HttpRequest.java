package iths.helena;

public class HttpRequest {
    String method, mimeType, filename, ext, jsonData;
    String[] postArgs = null;
    PostArguments pa;
    Person p;
    JsonConverter jc;

    public HttpRequest(String request) {
        if (request != null && !request.isEmpty()) {
            String lines[] = request.split("\r\n");
            method = lines[0].split(" ")[0];
            if(method.equals("POST")) {
                pa = new PostArguments();
                p = pa.breakUpArguments(lines[lines.length-1].split("&"));
                jc = new JsonConverter(p);
                jsonData = jc.personToJson();
            }
            filename = lines[0].split(" ")[1];
            if (filename.equals("/"))
                filename = "/index.html";
            ext = filename.substring(filename.lastIndexOf('.') + 1);
        } else {
            System.out.println("GAH!");
        }
        try {
            if(ext != null) {
                switch (ext) {
                    case "htm":
                        mimeType = "text/html";
                        break;
                    case "html":
                        mimeType = "text/html";
                        break;
                    case "css":
                        mimeType = "text/css";
                        break;
                    case "js":
                        mimeType = "text/javascript";
                        break;
                    case "png":
                        mimeType = "image/png";
                        break;
                    case "jpg":
                        mimeType = "image/jpeg";
                        break;
                    case "jpeg":
                        mimeType = "image/jpeg";
                        break;
                    case "ico":
                        mimeType = "image/vnd.microsoft.icon";
                        break;
                    case "json":
                        mimeType = "application/json";
                        break;
                    case "pdf":
                        mimeType = "application/pdf";
                        break;
                    default:
                        mimeType = "text/plain";
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {

        }
    }
}
