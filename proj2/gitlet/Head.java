package gitlet;

public class Head {


    public static void setHead(String sha){
        Utils.writeContents(Repository.HEAD_FILE, sha);
    }

}
