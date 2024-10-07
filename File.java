package filesys.src;

import java.io.*;

public class File {
    private String name;
    private String path;
    private FileVersion fileVersions;

    public File(String name, String path) {
        this.name = name;
        this.path = path;
        this.fileVersions = new FileVersion();
    }

    public void readFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(path + "/" + name))) {
            String line;
            System.out.println("Reading File " + name + ":");
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public boolean updateFile(String newContent) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path + "/" + name, false))) {
            bw.write(newContent);
            fileVersions.saveVersion(newContent);
            return true;
        } catch (IOException e) {
            System.err.println("Error updating file: " + e.getMessage());
            return false;
        }
    }

    public void revertToVersion(int versionNumber) {
        String previousVersionContent = fileVersions.getVersion(versionNumber);
        if (previousVersionContent.equals("No such version of the file exists")) {
            System.out.println(previousVersionContent);
            return;
        }
        updateFile(previousVersionContent); // Update the file with the previous version content
        fileVersions.updateVersionHistory(versionNumber); // Update version history
        System.out.println("Reverted to version: " + versionNumber);
    }

    public String getCurrentFileContent() {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(path + "/" + name))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Error getting current file content: " + e.getMessage());
        }
        return content.toString().trim();
    }

    public int getVersionCount() {
        return fileVersions.getVersionCount();
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public FileVersion getFileVersions() {
        return fileVersions;
    }
}
