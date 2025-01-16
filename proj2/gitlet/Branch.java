package gitlet;

import javax.swing.plaf.synth.SynthTabbedPaneUI;
import java.io.File;
import java.io.IOException;

public class Branch {



    public static void setBranch(String name, String sha) throws IOException {
         File branch = new File(Repository.BRANCHES_DIR, name);
             branch.createNewFile();
             Utils.writeContents(branch, sha);

    }


    public static void createBranch(String name, String sha) throws IOException {
        File branch = new File(Repository.BRANCHES_DIR, name);
        if (branch.exists()){
            System.out.println("A branch with that name already exists.");
            System.exit(0);
        }
        else {
            branch.createNewFile();
            Utils.writeContents(branch, sha);
        }
    }

    public static String currentBranchName() throws IOException {
       return Utils.readContentsAsString(Repository.CUR_BRANCH);
    }
    public static void setCurrentBranchName(String name) throws IOException {
        Utils.writeContents(Repository.CUR_BRANCH, name);
    }










}
