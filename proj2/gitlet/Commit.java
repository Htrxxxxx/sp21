package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static gitlet.Utils.getFileSha;
public class Commit implements Serializable {

    private String message;
    private String parent; // in SHA1 Hashing
    private Date timestamp;
    private static Map<String, String> trackedFiles;


    // this handels the intiall commit
    public Commit () throws IOException {
        this.message = "initial commit";
        this.parent = "";
        this.timestamp = new Date(0);
        this.trackedFiles = new HashMap<>(); // path of the file  - sha1 of the content .

        saveCommit();
    }
    // This handel the commits
    public Commit (String message) throws IOException {
        this.message = message;
        this.parent = Head.getHeadSha1();
        this.timestamp = new Date();
        trackedFiles = getTrackedFiles() ; // previos commit tracked_files
        processStagedFiles();
        clearIndex();
        saveCommit() ;
    }

    public void processStagedFiles() throws IOException {
        File files_added = Repository.STAGED_ADD;
        File files_removed = Repository.STAGED_RM;

        String [] added_List = files_added.list() ;
        String [] Removed_List = files_removed.list() ;

        if(added_List != null) {
            for (String file : added_List) {
                // This file contains the path of the file that has been modified .
                File F2 = new File(files_added, file); // hashed File
                String [] Added_Files = F2.list() ;
                String B = "";
                File FileToAdd = null ;
                String ShaForFile = "" ;
                for(String PathorFile : Added_Files) {
                    if (PathorFile.equals("path")) {
                        File PathFile = new File(F2, PathorFile);
                        String Path = Utils.readContentsAsString(PathFile);
                        B = Path;
                    } else {
                        FileToAdd = new File(F2, PathorFile);
                    }
                    if(FileToAdd != null) {
                        ShaForFile = Utils.getFileSha(FileToAdd);
                        trackedFiles.put(B, ShaForFile);
                    }
                }
                File newBlob = new File(Repository.BLOBS_DIR, ShaForFile);
                newBlob.mkdir();
                String fileName = new File(B).getName();
                File newFile = new File(newBlob, fileName);
                newFile.createNewFile();
                Files.copy(FileToAdd.toPath(), newFile.toPath() ,  StandardCopyOption.REPLACE_EXISTING);
            }
        }
        if(Removed_List != null) {
            for (String file : Removed_List) {
                File F2 = new File(files_removed, file);
                File f = new File(F2, "path");
                String pathOftheremoved = Utils.readContentsAsString(f);
                if (trackedFiles.containsKey(pathOftheremoved)) {
                    trackedFiles.remove(pathOftheremoved);
                }
            }
        }
    }

    // the name of the file which will be added is the sha1
    public void saveCommit() throws IOException {
          String curCommitSha = getShaCommit();
          File curCommit = createCommitFile(curCommitSha);
          // the content will be the serialization of it .
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


    public File createCommitFile(String commitSha) throws IOException {
        File file = new File(Repository.COMMITS_DIR, commitSha);
        return file;
    }


    public static Commit getCommitBySha(String shaForCommit) {

        File commitsDir = Repository.COMMITS_DIR;
        if (!commitsDir.exists()) {
            throw new IllegalArgumentException("Commits directory does not exist.");
        }

        File commitFile = new File(commitsDir, shaForCommit);
        if (!commitFile.exists()) {
            throw new IllegalArgumentException("Commit file does not exist: " + shaForCommit);
        }


        return Utils.readObject(commitFile, Commit.class);
    }

    static void clearIndex () {
        File files_added = Repository.STAGED_ADD;
        File files_removed = Repository.STAGED_RM;

        String [] added_List = files_added.list() ;
        String [] Removed_List = files_removed.list() ;
        if(added_List != null) {
            for (String file : added_List) {
                File F2 = new File(files_added, file);
                String [] Added_Files = F2.list() ;
                for(String PathorFile : Added_Files) {
                    File PathFile = new File(F2 , PathorFile);
                    PathFile.delete() ;
                }
                F2.delete();
            }
        }
        if(Removed_List != null) {
            for (String file : Removed_List) {
                File F2 = new File(files_removed, file);
                String [] Removed_Files = F2.list() ;
                for(String PathorFile : Removed_Files) {
                    File PathFile = new File(F2 , PathorFile);
                    PathFile.delete() ;
                }
                F2.delete();
            }
        }
    }

    public String getParentSha() {
        return parent;
    }
    public Date getTimestamp() {
        return timestamp;
    }
    public String message() {
        return message;
    }
    public Map<String, String> getTrackedFiles() {
        if(trackedFiles == null) {
            trackedFiles = new HashMap<>();
        }
        return trackedFiles;
    }

}
