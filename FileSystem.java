package filesys.src;

import java.util.HashMap;
import java.util.Map;

public class FileSystem {
    private Map<String, Directory> directories;

    public FileSystem() {
        directories = new HashMap<>();
        directories.put("./", new Directory("./")); // Default root directory
    }

    public void createDirectory(String dirPath) {
        if (directories.containsKey(dirPath)) {
            System.out.println("Directory already exists.");
            return;
        }
        directories.put(dirPath, new Directory(dirPath));
        System.out.println("Directory created successfully at " + dirPath);
    }

    public Directory getDirectory(String dirPath) {
        if (!directories.containsKey(dirPath)) {
            System.out.println("No such directory exists " + dirPath);
            return null;
        }
        return directories.get(dirPath);
    }

    public void listDirectories() {
        if (directories.isEmpty()) {
            System.out.println("No directories available.");
            return;
        }
        System.out.println("Directories:");
        for (String dir : directories.keySet()) {
            System.out.println(dir);
        }
    }

    public boolean moveFile(String from, String fromFile, String to, String toFile) {
        Directory dir1 = getDirectory(from);
        Directory dir2 = getDirectory(to);
        if(dir1 == null) return false;
        File sourceFile = dir1.getFile(fromFile);

        if (sourceFile != null && dir2 != null) {
            dir2.createFile(toFile);
            String content = sourceFile.getCurrentFileContent();
            dir2.getFile(toFile).updateFile(content);
            dir1.deleteFile(fromFile);
            return true;
        }
        System.err.println("Error moving file, verify if directory exists or not.");
        return false;
    }

    public boolean copyFile(String from, String fromFile, String to, String toFile) {
        Directory dir1 = getDirectory(from);
        Directory dir2 = getDirectory(to);
        if(dir1 == null) return false;
        File sourceFile = dir1.getFile(fromFile);

        if (sourceFile != null && dir2 != null) {
            dir2.createFile(toFile);
            String content = sourceFile.getCurrentFileContent();
            System.out.println(content);
            dir2.getFile(toFile).updateFile(content);
            return true;
        }
        System.err.println("Error copying file, verify if directory or file exists.");
        return false;
    }
}
