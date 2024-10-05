package filesys.src;

import filesys.src.File;
import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class Directory {
    private String path;
    private Map<String, File> files;

    public Directory() {
        this.path = "./";
        files = new HashMap<>();
    }

    public Directory(String path) {
        this.path = path;
        files = new HashMap<>();

        // Create the actual directory if it doesn't exist
        java.io.File dir = new java.io.File(path);
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                System.out.println("Directory created: " + path);
            } else {
                System.out.println("Failed to create directory: " + path);
            }
        }
    }

    public boolean createFile(String fileName) {
        if (files.containsKey(fileName)) {
            System.out.println("File already exists");
            return false;
        }
        files.put(fileName, new File(fileName, path));
        try {
            new java.io.File(path + "/" + fileName).createNewFile();
            System.out.println("File created successfully at " + path);
            return true;
        } catch (IOException e) {
            System.err.println("Error creating file: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteFile(String fileName) {
        File file = files.get(fileName);
        if (file != null) {
            // Construct the file path to delete
            String filePath = file.getPath() + "/" + file.getName();
            java.io.File actualFile = new java.io.File(filePath);
            
            if (actualFile.delete()) {
                System.out.println("File deleted successfully");
                files.remove(fileName); // Remove the reference from the map
                return true;
            } else {
                System.err.println("Error deleting file");
                return false;
            }
        } else {
            System.err.println("File not found in directory");
            return false;
        }
    }
    

    public void listFiles() {
        if (files.isEmpty()) {
            System.out.println("No files in directory " + path);
            return;
        }
        System.out.println("Files in directory " + path + " are: ");
        files.keySet().forEach(System.out::println);
    }

    public File getFile(String fileName) {
        if (!files.containsKey(fileName)) {
            System.out.println("No such file exists in directory " + path);
            return null;
        }
        return files.get(fileName);
    }

    public String getDirectoryPath() {
        return path;
    }
}
