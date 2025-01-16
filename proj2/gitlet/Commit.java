package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.Date; // TODO: You'll likely use this in this class
import java.util.HashMap;
import java.util.Map;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /** The message of this Commit. */
    private String message;
    private String parent; // in SHA1 Hashing
    private Date timestamp;
    private Map<String, String> trackedFiles;



    public Commit () throws IOException {
        this.message = "initial commit";
        this.parent = "";
        this.timestamp = new Date(0);
        this.trackedFiles = new HashMap<String, String>();
        saveCommit();
    }
    public Commit (String message){

    }






    public void saveCommit() throws IOException {
          String curCommitSha = getShaCommit();
          File curCommit = createCommitFile(curCommitSha);
          Utils.writeObject(curCommit, this);
    }


    public String getShaCommit() throws IOException {
        File tempFile = createCommitFile("commit");
        tempFile.createNewFile();
        Utils.writeObject(tempFile, this);
        byte[] bytes = Files.readAllBytes(tempFile.toPath());
        tempFile.delete();
        return Utils.sha1(bytes);
    }



    public File createCommitFile(String name) throws IOException {
        File file = new File(Repository.COMMITS_DIR, name);
        return file;
    }



    /* TODO: fill in the rest of this class. */
}
