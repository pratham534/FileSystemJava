package filesys.src;

import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static FileSystem fs = new FileSystem();

    public static void main(String[] args) {
        boolean exit = false;

        while (!exit) {
            showMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    createDirectory(); // New option to create a directory
                    break;
                case "2":
                    createFile();
                    break;
                case "3":
                    readFile();
                    break;
                case "4":
                    updateFile();
                    break;
                case "5":
                    revertFileVersion();
                    break;
                case "6":
                    listFilesInDirectory();
                    break;
                case "7":
                    showFileVersions();
                    break;
                case "8":
                    copyFile();
                    break;
                case "9":
                    moveFile();
                    break;
                case "10":
                    deleteFile();
                    break;
                case "11":
                    listDirectories();
                    break;
                case "0":
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        System.out.println("Program terminated.");
    }

    private static void showMenu() {
        System.out.println("1. Create a directory");
        System.out.println("2. Create a file");
        System.out.println("3. Read a file");
        System.out.println("4. Update a file");
        System.out.println("5. Revert file version");
        System.out.println("6. List files in a directory");
        System.out.println("7. Show file versions");
        System.out.println("8. Copy a file");
        System.out.println("9. Move a file");
        System.out.println("10. Delete a file");
        System.out.println("11. List all directories");
        System.out.println("0. Exit the program");
        System.out.print("Your choice: ");
    }
    
    private static void showFileVersions() {
        System.out.print("Enter directory path to view file versions: ");
        String dirPath = scanner.nextLine();
        Directory dir = fs.getDirectory(dirPath);
        if (dir != null) {
            System.out.print("Enter file name to view versions: ");
            String fileName = scanner.nextLine();
            File file = dir.getFile(fileName);
            if (file != null) {
                int versionCount = file.getVersionCount();
                System.out.println("Versions for \'" + fileName + "\':-");
                for (int i = 1; i <= versionCount; i++) {
                    System.out.println("Version " + i + ": \n" + file.getFileVersions().getVersion(i));
                }
            }
        }
    }
    

    private static void createDirectory() {
        System.out.print("Enter the directory path to create: ");
        String dirPath = scanner.nextLine();
        fs.createDirectory(dirPath);
    }

    private static void createFile() {
        System.out.print("Enter directory path to create a file in: ");
        String dirPath = scanner.nextLine();
        Directory dir = fs.getDirectory(dirPath);
        if (dir != null) {
            System.out.print("Enter file name: ");
            String fileName = scanner.nextLine();
            dir.createFile(fileName);
        }
    }

    private static void readFile() {
        System.out.print("Enter directory path to read a file from: ");
        String dirPath = scanner.nextLine();
        Directory dir = fs.getDirectory(dirPath);
        if (dir != null) {
            System.out.print("Enter file name to read: ");
            String fileName = scanner.nextLine();
            File file = dir.getFile(fileName);
            if (file != null) {
                file.readFile();
            }
        }
    }

    private static void updateFile() {
        System.out.print("Enter directory path to update a file in: ");
        String dirPath = scanner.nextLine();
        Directory dir = fs.getDirectory(dirPath);
        if (dir != null) {
            System.out.print("Enter file name to update: ");
            String fileName = scanner.nextLine();
            File file = dir.getFile(fileName);
            if (file != null) {
                System.out.print("Enter new content: ");
                String content = scanner.nextLine();
                file.updateFile(content);
            }
        }
    }

    private static void revertFileVersion() {
        System.out.print("Enter directory path to revert a file in: ");
        String dirPath = scanner.nextLine();
        Directory dir = fs.getDirectory(dirPath);
        if (dir != null) {
            System.out.print("Enter file name to revert: ");
            String fileName = scanner.nextLine();
            File file = dir.getFile(fileName);
            if (file != null) {
                System.out.print("Enter version number to revert to: ");
                int versionNumber = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                file.revertToVersion(versionNumber);
            }
        }
    }

    private static void listFilesInDirectory() {
        System.out.print("Enter directory path to list files in: ");
        String dirPath = scanner.nextLine();
        Directory dir = fs.getDirectory(dirPath);
        if (dir != null) {
            dir.listFiles();
        }
    }

    private static void copyFile() {
        System.out.print("Enter source directory path: ");
        String from = scanner.nextLine();
        System.out.print("Enter source file name: ");
        String fromFile = scanner.nextLine();
        System.out.print("Enter destination directory path: ");
        String to = scanner.nextLine();
        System.out.print("Enter destination file name: ");
        String toFile = scanner.nextLine();
        fs.copyFile(from, fromFile, to, toFile);
    }

    private static void moveFile() {
        System.out.print("Enter source directory path: ");
        String from = scanner.nextLine();
        System.out.print("Enter source file name: ");
        String fromFile = scanner.nextLine();
        System.out.print("Enter destination directory path: ");
        String to = scanner.nextLine();
        System.out.print("Enter destination file name: ");
        String toFile = scanner.nextLine();
        fs.moveFile(from, fromFile, to, toFile);
    }

    private static void deleteFile() {
        System.out.print("Enter directory path to delete a file from: ");
        String dirPath = scanner.nextLine();
        Directory dir = fs.getDirectory(dirPath);
        if (dir != null) {
            System.out.print("Enter file name to delete: ");
            String fileName = scanner.nextLine();
            dir.deleteFile(fileName);
        }
    }

    private static void listDirectories() {
        fs.listDirectories();
    }
}
