package gitlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;


public class IndexArea {

    public static void add (String fileName) throws IOException {
         File CWDD = new File(System.getProperty("user.dir"));
         File f = new File(CWDD , fileName) ;
         if(!f.exists()){
             System.out.println("File does not exist.");
             System.exit(0);
         }

         String fullPath = f.getAbsolutePath();
         System.out.println(fullPath);
         String hashedPath = HelperMethods.hashPath(fullPath);
         // if the file staged for removal remove it .
         IndexArea.removeFromRemoval(hashedPath);
         // This return the current commit after deserialization  .
         String shaCommit = Head.getHeadSha1() ;
         Commit currentCommit = Commit.getCommitBySha(shaCommit) ;
         Map<String , String> trackedFilesForCurrentCommit = currentCommit.getTrackedFiles();

         removeFromAddition(hashedPath);
         if(trackedFilesForCurrentCommit.containsKey(fullPath)){
             File fileToAdd = new File(fullPath);
             String shaForFileToAdd = HelperMethods.getShaForFile(fileToAdd);
             if (!shaForFileToAdd.equals(trackedFilesForCurrentCommit.get(fullPath))){
                 IndexArea.addForAddition(fullPath, hashedPath);
             }
         }
         else {
             IndexArea.addForAddition(fullPath, hashedPath);
         }
    }


    /*  Steps for remove :
       - remove the file from addition area if it exist .
       - if the file is tracked stage it for removal
       - remove the file from the CWD
     */
    public static void rm (String name) throws IOException {
        String fullPath = System.getProperty("user.dir") + "\\" + name;
        File file = new File(fullPath);

        if(!file.exists()){
            file.createNewFile() ;
        }

        String currenCommitSha = Head.getHeadSha1() ;
        Commit currentCommit = Commit.getCommitBySha(currenCommitSha);
        Map<String , String> trackedFilesForCurrentCommit = currentCommit.getTrackedFiles();

        String hashedPath = HelperMethods.hashPath(fullPath);

        boolean isTracked = trackedFilesForCurrentCommit.containsKey(fullPath);
        boolean isStaged = IndexArea.existInStagedForAddition(hashedPath);


        if(!isTracked && !isStaged){
            System.out.println("No reason to remove the file.");
            System.exit(0);
        }

        removeFromAddition(hashedPath);

        if(isTracked){
            addForRemoval(name);
        }

        if(file.exists()) {
            file.delete();
        }

    }
    public static void addForRemoval(String fileName) throws IOException {
        String fullPath = new File(System.getProperty("user.dir")).getAbsolutePath() + "/" + fileName;
        String hashedPath = HelperMethods.hashPath(fullPath);
        /* TODO : why i do that */
        File folder = new File(Repository.STAGED_RM, hashedPath);
        folder.mkdir();
        File path = new File(folder , "path");
        Utils.writeContents(path , fullPath);
        File originFile = new File(fullPath);
        File file = new File(folder , originFile.getName());
        Files.copy(originFile.toPath(), file.toPath());
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
    public static void addForAddition(String fullPath, String hashedPath) throws IOException {

        File FolderToAdd = new File (Repository.STAGED_ADD, hashedPath);
        if (!FolderToAdd.exists()) {
            FolderToAdd.mkdir();
        }

        File path = new File(FolderToAdd, "path");

        if(!path.exists()) {
            path.createNewFile();
        }

        Utils.writeContents(path , fullPath);

        File originFile = new File(fullPath);
        File FileToadd = new File(FolderToAdd, originFile.getName());

        if (FileToadd.exists()) {
            FileToadd.delete();
        }
        Files.copy(originFile.toPath(), FileToadd.toPath());
    }

    public static boolean existInStagedForAddition(String hashedPath){
        File hasedFile = new File(Repository.STAGED_ADD, hashedPath);
        if (hasedFile.exists()){
            return true;
        }
        return false;
    }




}
