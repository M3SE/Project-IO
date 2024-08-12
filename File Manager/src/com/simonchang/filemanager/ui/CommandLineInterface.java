package com.simonchang.filemanager.ui;

import com.simonchang.filemanager.operations.DirectoryOperations;
import com.simonchang.filemanager.operations.FileOperations;
import com.simonchang.filemanager.utils.ExceptionHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class CommandLineInterface {

    private final Scanner scanner;
    private Path currentDirectory; // To store the current directory
    private final FileOperations fileOps; // FileOperations instance
    private static final Logger logger = Logger.getLogger(CommandLineInterface.class.getName());

    public CommandLineInterface() {
        this.scanner = new Scanner(System.in);
        this.fileOps = new FileOperations(); // Initialize FileOperations
    }

    // Method to prompt user to select a directory
    public Path selectDirectory() {
        while (true) {
            System.out.print("Enter the directory path: ");
            String dirPath = scanner.nextLine();
            Path path = Paths.get(dirPath);

            if (Files.exists(path) && Files.isDirectory(path)) {
                System.out.println("Directory selected: " + path.toString());
                logger.log(Level.INFO, "Directory selected: " + path.toString());
                return path;
            } else {
                System.out.println("Invalid directory. Please enter a valid directory path.");
                logger.log(Level.WARNING, "Invalid directory selected: " + dirPath);
            }
        }
    }

    // Start the Command Line Interface
    public void start() {
        while (true) {
            System.out.println("1. Select Directory");
            System.out.println("2. Exit");
            System.out.print("Choose an option: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                switch (choice) {
                    case 1 -> {
                        currentDirectory = selectDirectory();  // Select a directory
                        directoryMenu();  // Open the directory-specific menu
                    }
                    case 2 -> {
                        System.out.println("Exiting...");
                        logger.log(Level.INFO, "Exiting application.");
                        return;
                    }
                    default -> {
                        System.out.println("Invalid option. Please enter a number between 1 and 2.");
                        logger.log(Level.WARNING, "Invalid menu option selected.");
                    }
                }
            } catch (InputMismatchException e) {
                scanner.nextLine();  // Clear the invalid input
                System.out.println("Invalid input. Please enter a number.");
                logger.log(Level.WARNING, "InputMismatchException: Invalid input received.");
            } catch (Exception e) {
                ExceptionHandler.handle(e, "Error in main menu");
            }
        }
    }

    // Rest of your methods...

    private void directoryMenu() {
        while (true) {
            System.out.println("Current directory: " + currentDirectory.toString());
            System.out.println("1. List Directory Contents");
            System.out.println("2. Enter Subdirectory");
            System.out.println("3. Create Directory");
            System.out.println("4. Delete Directory");
            System.out.println("5. Create File");
            System.out.println("6. Delete File");
            System.out.println("7. Move File");
            System.out.println("8. Copy File");
            System.out.println("9. Search Files");
            System.out.println("10. Go Back to Main Menu");
            System.out.print("Choose an option: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                switch (choice) {
                    case 1 -> listDirectory();  // List contents of the current directory
                    case 2 -> enterSubdirectory();  // Enter a subdirectory within the current directory
                    case 3 -> createDirectory();  // Create a new subdirectory in the current directory
                    case 4 -> deleteDirectory();  // Delete a subdirectory in the current directory
                    case 5 -> createFile();  // Create a new file in the current directory
                    case 6 -> deleteFile();  // Delete a file in the current directory
                    case 7 -> moveFile();  // Move a file within the current directory
                    case 8 -> copyFile();  // Copy a file within the current directory
                    case 9 -> searchFiles();  // Search for files in the current directory
                    case 10 -> {
                        return;  // Go back to the main menu
                    }
                    default -> {
                        System.out.println("Invalid option. Please enter a number between 1 and 10.");
                        logger.log(Level.WARNING, "Invalid directory menu option selected.");
                    }
                }
            } catch (InputMismatchException e) {
                scanner.nextLine();  // Clear the invalid input
                System.out.println("Invalid input. Please enter a number.");
                logger.log(Level.WARNING, "InputMismatchException: Invalid input received.");
            } catch (Exception e) {
                ExceptionHandler.handle(e, "Error in directory menu");
            }
        }
    }

    // List contents of the current directory
    private void listDirectory() throws IOException {
        if (currentDirectory == null) {
            System.out.println("No directory selected. Please select a directory first.");
            logger.log(Level.WARNING, "Attempted to list contents with no directory selected.");
            return;
        }
        DirectoryOperations dirOps = new DirectoryOperations();
        dirOps.listDirectoryContents(currentDirectory);
    }

    // Enter a subdirectory within the current directory
    private void enterSubdirectory() {
        if (currentDirectory == null) {
            System.out.println("No directory selected. Please select a directory first.");
            logger.log(Level.WARNING, "Attempted to enter subdirectory with no directory selected.");
            return;
        }
        System.out.print("Enter the name of the subdirectory to enter: ");
        String subDirName = scanner.nextLine();
        Path subDirPath = currentDirectory.resolve(subDirName);

        if (Files.exists(subDirPath) && Files.isDirectory(subDirPath)) {
            currentDirectory = subDirPath;
            System.out.println("Entered subdirectory: " + currentDirectory.toString());
            logger.log(Level.INFO, "Entered subdirectory: " + currentDirectory.toString());
        } else {
            System.out.println("Subdirectory does not exist or is not a directory: " + subDirName);
            logger.log(Level.WARNING, "Invalid subdirectory entered: " + subDirName);
        }
    }

    // Create a subdirectory within the current directory
    private void createDirectory() throws IOException {
        if (currentDirectory == null) {
            System.out.println("No directory selected. Please select a directory first.");
            logger.log(Level.WARNING, "Attempted to create directory with no directory selected.");
            return;
        }
        System.out.print("Enter the name of the new directory: ");
        String newDirName = scanner.nextLine();
        Path newDirPath = currentDirectory.resolve(newDirName);
        DirectoryOperations dirOps = new DirectoryOperations();
        dirOps.createDirectory(newDirPath);
        logger.log(Level.INFO, "Directory created: " + newDirPath.toString());
    }

    // Delete a subdirectory within the current directory
    private void deleteDirectory() throws IOException {
        if (currentDirectory == null) {
            System.out.println("No directory selected. Please select a directory first.");
            logger.log(Level.WARNING, "Attempted to delete directory with no directory selected.");
            return;
        }
        System.out.print("Enter the name of the directory to delete: ");
        String dirName = scanner.nextLine();
        Path dirPath = currentDirectory.resolve(dirName);
        DirectoryOperations dirOps = new DirectoryOperations();
        dirOps.deleteDirectory(dirPath);
        logger.log(Level.INFO, "Directory deleted: " + dirPath.toString());
    }

    // Create a file within the current directory
    private void createFile() throws IOException {
        if (currentDirectory == null) {
            System.out.println("No directory selected. Please select a directory first.");
            logger.log(Level.WARNING, "Attempted to create file with no directory selected.");
            return;
        }
        System.out.print("Enter the name of the new file: ");
        String fileName = scanner.nextLine();
        try {
            fileOps.createFile(currentDirectory, fileName);
        } catch (IOException e) {
            ExceptionHandler.handle(e, "Failed to create file");
        }
    }

    // Delete a file within the current directory
    private void deleteFile() throws IOException {
        if (currentDirectory == null) {
            System.out.println("No directory selected. Please select a directory first.");
            logger.log(Level.WARNING, "Attempted to delete file with no directory selected.");
            return;
        }
        System.out.print("Enter the name of the file to delete: ");
        String fileName = scanner.nextLine();
        try {
            fileOps.deleteFile(currentDirectory, fileName);
        } catch (IOException e) {
            ExceptionHandler.handle(e, "Failed to delete file");
        }
    }

    // Move a file within the current directory
    private void moveFile() throws IOException {
        if (currentDirectory == null) {
            System.out.println("No directory selected. Please select a directory first.");
            logger.log(Level.WARNING, "Attempted to move file with no directory selected.");
            return;
        }
        System.out.print("Enter the name of the file to move: ");
        String fileName = scanner.nextLine();
        System.out.print("Enter the target directory path (relative to current directory): ");
        String targetDirName = scanner.nextLine();
        Path targetDirPath = currentDirectory.resolve(targetDirName);
        try {
            fileOps.moveFile(currentDirectory, fileName, targetDirPath);
        } catch (IOException e) {
            ExceptionHandler.handle(e, "Failed to move file");
        }
    }

    // Copy a file within the current directory
    private void copyFile() throws IOException {
        if (currentDirectory == null) {
            System.out.println("No directory selected. Please select a directory first.");
            logger.log(Level.WARNING, "Attempted to copy file with no directory selected.");
            return;
        }
        System.out.print("Enter the name of the file to copy: ");
        String fileName = scanner.nextLine();
        System.out.print("Enter the target directory path (relative to current directory): ");
        String targetDirName = scanner.nextLine();
        Path targetDirPath = currentDirectory.resolve(targetDirName);
        try {
            fileOps.copyFile(currentDirectory, fileName, targetDirPath);
        } catch (IOException e) {
            ExceptionHandler.handle(e, "Failed to copy file");
        }
    }

    // Search for files within the current directory and its subdirectories
    private void searchFiles() throws IOException {
        if (currentDirectory == null) {
            System.out.println("No directory selected. Please select a directory first.");
            logger.log(Level.WARNING, "Attempted to search files with no directory selected.");
            return;
        }
        System.out.print("Enter the file name or extension to search for: ");
        String searchQuery = scanner.nextLine();
        searchFilesInDirectory(currentDirectory, searchQuery);
    }

    // Method to search for files in a directory and its subdirectories
    private void searchFilesInDirectory(Path dir, String searchQuery) throws IOException {
        try (Stream<Path> stream = Files.walk(dir)) {
            stream.filter(path -> path.getFileName().toString().contains(searchQuery))
                    .forEach(path -> {
                        System.out.println("Found: " + path.toString());
                        logger.log(Level.INFO, "File found: " + path.toString());
                    });
        } catch (IOException e) {
            ExceptionHandler.handle(e, "Failed to search files");
        }
    }
}
