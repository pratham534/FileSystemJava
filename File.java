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
        System.out.println(getCurrentFileContent());
    }

    public boolean updateFile(String newContent) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path + "/" + name, true))) {
            bw.write(newContent);
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            System.err.println("Error updating file: " + e.getMessage());
            return false;
        }
        String latestContent = getCurrentFileContent();
        // System.out.println(latestContent);
        fileVersions.saveVersion(latestContent);
        return true;
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
            br.close();
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
