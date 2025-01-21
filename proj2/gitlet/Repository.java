package gitlet;

import java.io.File;
import static gitlet.Utils.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author Yasser
 */
public class Repository implements Cloneable {
    public static File CWD = new File(System.getProperty("user.dir"));
    public static File GITLET_DIR = new File(CWD, ".gitlet");
    public static File COMMITS_DIR = new File(GITLET_DIR, "commits");
    public static File BLOBS_DIR = new File(GITLET_DIR, "BLOBS");
    public static File BRANCHES_DIR = new File(GITLET_DIR, "branches");
    public static File STAGED_ADD = new File(GITLET_DIR, "staggedAdd");
    public static File STAGED_RM = new File(GITLET_DIR, "staggedRemove");
    public static File HEAD_FILE = new File(GITLET_DIR, "head");
    public static File CUR_BRANCH = new File(GITLET_DIR, "curBranch");



    public static void init () throws IOException {
        // check for exist repo
        if (repoExists()){
            System.out.println("A Gitlet version-control system already exists in the current directory.");
            System.exit(0);
        }
        GITLET_DIR.mkdir();
        COMMITS_DIR.mkdir();
        BLOBS_DIR.mkdir();
        BRANCHES_DIR.mkdir();
        STAGED_ADD.mkdir();
        STAGED_RM.mkdir();
        HEAD_FILE.createNewFile();
        CUR_BRANCH.createNewFile();
        /** create initial commit */
        Commit intialCommit = new Commit();
        /** set Head to this sha1 */
        String intialCommitSha = intialCommit.getShaCommit();
        Head.setHead(intialCommitSha);

        /** create main branch and make it pointer to inital commit */
        Branch.createBranch("main", intialCommitSha);
        /** set current branch name to main */
        Branch.setCurrentBranchName("main");
    }
    /* Add a file to staging area *
       we need to check if the file exist or not *
       The staging area should be somewhere in .gitlet *
        If the current working version of the file is identical to the version in the current commit
        , do not stage it to be added, and remove it from the staging area if it is already there *

     */
    /** TODO : add this file to sageed for addition */
    public static void add (String fileName) throws IOException {
          IndexArea.addForAddition(fileName);
    }


    public  static void rm(String fileName) throws IOException {
        IndexArea.addForRemoval(fileName);
    }


    public static void commit(String message) throws IOException {
        Commit commit = new Commit(message);
    }



    public static boolean repoExists() {
        if (GITLET_DIR.exists()) {
            return true;
        }
        return false;
    }

}
