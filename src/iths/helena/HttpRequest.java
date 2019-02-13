package iths.helena;

public class HttpRequest {

    //first line contain 3 parts
    //1 request type, 2 request file name and 3 request http version
    //and for us file name is important
    String filename;

    //here we have to create a constructor that accepts a string
    public HttpRequest(String request){

        String lines[] = request.split("\n"); //now we got all lines of request separately
        filename = lines[0].split(" ")[1];    //this line basically split the first line using space" "
                                                    // and the selects the 2nd item which is our filename

    }

}
