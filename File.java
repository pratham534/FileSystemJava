package filesys.src;

// import java.io.*;
// // import java.util.Objects;

// public class File {
//     private String name;
//     private String path;
//     private FileVersion fileVersions;

//     public File(String name, String path) {
//         this.name = name;
//         this.path = path;
//         fileVersions = new FileVersion();
//     }

//     public String getCurrentFileContent() {
//         try (BufferedReader br = new BufferedReader(new FileReader(path + "/" + name))) {
//             StringBuilder content = new StringBuilder();
//             String line;
//             while ((line = br.readLine()) != null) {
//                 content.append(line).append("\n");
//             }
//             return content.toString();
//         } catch (IOException e) {
//             System.err.println("Error opening file: " + e.getMessage());
//             return "";
//         }
//     }

//     public void readFile() {
//         try (BufferedReader br = new BufferedReader(new FileReader(path + "/" + name))) {
//             System.out.println("Reading File " + name + "\n");
//             String line;
//             while ((line = br.readLine()) != null) {
//                 System.out.println(line);
//             }
//         } catch (IOException e) {
//             System.err.println("Error opening file: " + e.getMessage());
//         }
//     }

//     public boolean updateFile(String newContent) {
//         try (BufferedWriter writer = new BufferedWriter(new FileWriter(path + "/" + name, true))) {
//             writer.write(newContent);
//             writer.newLine();
//             System.out.println("File updated successfully");
//             fileVersions.saveVersion(getCurrentFileContent());
//             return true;
//         } catch (IOException e) {
//             System.err.println("Error opening the file: " + e.getMessage());
//             return false;
//         }
//     }

//     public int getVersionCount() {
//         return fileVersions.getVersionCount();
//     }

//     public void revertToVersion(int versionNumber) {
//         if (versionNumber > fileVersions.getVersionCount()) {
//             System.out.println("No such version of the file exists");
//             return;
//         }
//         try (BufferedWriter writer = new BufferedWriter(new FileWriter(path + "/" + name))) {
//             String newContent = fileVersions.getVersion(versionNumber);
//             fileVersions.updateVersionHistory(versionNumber);
//             writer.write(newContent);
//             System.out.println("File successfully reverted to version: " + versionNumber);
//         } catch (IOException e) {
//             System.err.println("Error opening the file: " + e.getMessage());
//         }
//     }

//     public String getPath() {
//         return path;
//     }

//     public String getName() {
//         return name;
//     }

//     public FileVersion getFileVersions() {
//         return fileVersions;
//     }
// }

import java.io.*;

public class File {
    private String name;
    private String path;
    private FileVersion fileVersions;

    public File(String name, String path) {
        this.name = name;
        this.path = path;
        this.fileVersions = new FileVersion();
        // Save the initial version when the file is created
        // saveCurrentVersion();
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

    // public void saveCurrentVersion() {
    //     String currentContent = getCurrentFileContent();
    //     System.out.println(currentContent);
    //     fileVersions.saveVersion(currentContent);
    // }

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
