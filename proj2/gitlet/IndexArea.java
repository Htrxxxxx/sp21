package gitlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

public class IndexArea {


    /** TODO: complete it  : remove the file from removal if it exist
     * check if exist in current commit or not (if not add it) and return;
     *
     * if the file exist in index area {
     *     if it's the same we don't do anything
     *     if it's diff we overwrite it unless it's the same of the current commit we remove it
     * }
     * check if the sha1 for this file is the same of the currentCommit sha for this path
     *
     *
     * */
    /** in this part
     * If the file we need to add in the stage area we will delete it and save the current commit if
     * that file differ from the file in the current commit */
    public static void addForAddition(String fileName) throws IOException {
         File CWDD = new File(System.getProperty("user.dir"));
         File f = new File(CWDD , fileName) ;
         if(!f.exists()){
             System.out.println("File does not exist.");
             System.exit(0);
         }
         // we should get the complete path by concatonate the cwd path and this fileName
         String fullPath = Repository.CWD.getAbsolutePath() + "/" + fileName;
         /** get hased for fullPaht */
         String hashedPath = HelperMethods.hashPath(fullPath);
         /** remove it from stage for removal if it exist*/
         IndexArea.removeFromRemoval(hashedPath);
         /** get current commit sha*/
         String currentCommitSha = Commit.getCurrentCommitSha();
         /** get current commmit*/
         Commit currentCommit = Commit.getCommitBySha(currentCommitSha);
         /** get tracked File by current commit*/
         Map<String , String> trackedFilesForCurrentCommit = currentCommit.getTrackedFiles();
         /** we remove file from additoin if it exist*/
         removeFromAddition(hashedPath);

        /** if it tracked and changed we add or not tracked */
         if(trackedFilesForCurrentCommit.containsKey(fullPath)){
             File fileToAdd = new File(fullPath);
             String shaForFileToAdd = HelperMethods.getShaForFile(fileToAdd);
             if (!shaForFileToAdd.equals(trackedFilesForCurrentCommit.get(fullPath))){
                 IndexArea.add(fullPath, hashedPath);
             }
         }
         else {
             IndexArea.add(fullPath, hashedPath);
         }

    }


    public static void removeFromRemoval(String hasedPath){
        File currentFile = new File(Repository.STAGED_RM, hasedPath);
        if (currentFile.exists()){
            String [] arr = currentFile.list();
            for(String str : arr){
                File file = new File(currentFile, str);
                file.delete();
            }
            currentFile.delete();
        }
    }

    public static void removeFromAddition(String hasedPath){
          File currentFile = new File(Repository.STAGED_ADD, hasedPath);
          if (currentFile.exists()){
              String [] arr = currentFile.list();
              for(String str : arr){
                  File file = new File(currentFile, str);
                  file.delete();
              }
              currentFile.delete();
          }
    }
    public static void add(String fullPath, String hashedPath) throws IOException {

        /** creat a folder for a file to add with name is the hashed path of file  added */
        File FolderToAdd = new File (Repository.STAGED_ADD, hashedPath);
        if (!FolderToAdd.exists()) {
            FolderToAdd.mkdir();
        }
        /** create a file inside it the path of the file to be added */
        File path = new File(FolderToAdd, "path");
        if(!path.exists()) {
            path.createNewFile();
        }
        /** write the path inside it*/
        Utils.writeContents(path , fullPath);
        /** copy the added file to this folder*/
        File originFile = new File(fullPath);
        File FileToadd = new File(FolderToAdd, originFile.getName());
        if (FileToadd.exists()) {
            FileToadd.delete();
        }
        Files.copy(originFile.toPath(), FileToadd.toPath());
    }


    public static void addForRemoval(String fileName) throws IOException {
        String fullPath = Repository.CWD.getAbsolutePath() + "/" + fileName;

        File file = new File(fullPath);
        if(!file.exists()){
            System.out.println("No reason to remove the file.");
            System.exit(0);
        }

        // if the file is tracked in the current commit .
        String hashedPath = HelperMethods.hashPath(fullPath);

        if (existInStagedForAddition(hashedPath)){
            removeFromAddition(hashedPath);
        }
        

    }




    public static void remove (String hasedPath, String fullPath) throws IOException {

        File FolderToRm = new File (Repository.STAGED_RM, hasedPath);
        if (!FolderToRm.exists()) {
            FolderToRm.mkdir();
        }
        File path = new File(FolderToRm, "path");
        if(!path.exists()) {
            path.createNewFile();
        }
        Utils.writeContents(path,fullPath);
        File originFile = new File(fullPath);
        File FileToRm = new File(FolderToRm, originFile.getName());
        if (FileToRm.exists()) {
            FileToRm.delete();
        }
        Files.copy(originFile.toPath(), FileToRm.toPath());
    }





    public static boolean existInStagedForAddition(String hashedPath){
        File hasedFile = new File(Repository.STAGED_ADD, hashedPath);
        if (hasedFile.exists()){
            System.out.println("hhhhhh");
            return true;
        }
        return false;
    }




}
