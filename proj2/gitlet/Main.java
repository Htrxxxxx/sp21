package gitlet;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                Repository.init();
                break;
            case "add":
                Repository.add(args[1]);
                break;
            case "commit":
                String message = args[1];
                Repository.commit(message) ;
                break;
            case "rm":
                Repository.rm(args[1]);
                break;
            case "log":
                break;
            case "branch" :
                // TODO
                break;
            case "rm-branch":
                // TODO
                break;
            case "reset" :
                // TODO
                break;
            case "merge":
                // TODO
                break;
            default:
                System.out.println("Unknown command: " + firstArg);
                break;
            // TODO: FILL THE REST IN
        }
    }
}
