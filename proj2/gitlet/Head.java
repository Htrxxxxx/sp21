package gitlet;

import java.io.File;


public class Head {


    public static void setHead(String sha1) {
        Utils.writeContents(Repository.HEAD_FILE, sha1);
    }

    public static String getHeadSha1() {
        String headContent = Utils.readContentsAsString(Repository.HEAD_FILE);
        if (headContent.startsWith("ref:")) {
            // Resolve branch reference
            String branchName = headContent.substring("ref: ".length()).trim();
            File branchFile = new File(Repository.BRANCHES_DIR, branchName);
            if (!branchFile.exists()) {
                throw new IllegalStateException("Branch file does not exist: " + branchName);
            }
            return Utils.readContentsAsString(branchFile);
        } else {
            return headContent;
        }
    }

}
