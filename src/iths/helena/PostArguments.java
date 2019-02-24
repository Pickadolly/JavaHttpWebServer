package iths.helena;

public class PostArguments {
    private String[] args;
    private String fname,lname;
    Person p;

    public PostArguments() {

    }

    public Person breakUpArguments(String[] args) {
        fname = args[0].split("=")[1];
        lname = args[1].split("=")[1];
        p = new Person(fname,lname);
        return p;
    }
}