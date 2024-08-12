package com.simonchang.filemanager.operations;

import java.io.IOException;
import java.nio.file.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileOperations {

    private static final Logger logger = Logger.getLogger(FileOperations.class.getName());

    // Create a file within a directory
    public void createFile(Path directory, String fileName) throws IOException {
        Path filePath = directory.resolve(fileName);
        Files.createFile(filePath);
        System.out.println("File created: " + filePath.toString());
        logger.log(Level.INFO, "File created: " + filePath.toString());
    }

    // Delete a file within a directory
    public void deleteFile(Path directory, String fileName) throws IOException {
        Path filePath = directory.resolve(fileName);

        if (Files.exists(filePath) && Files.isRegularFile(filePath)) {
            Files.delete(filePath);
            System.out.println("File deleted: " + filePath.toString());
            logger.log(Level.INFO, "File deleted: " + filePath.toString());
        } else {
            System.out.println("File does not exist: " + fileName);
            logger.log(Level.WARNING, "Attempted to delete non-existent file: " + fileName);
        }
    }

    // Move a file within a directory
    public void moveFile(Path directory, String fileName, Path targetDirectory) throws IOException {
        Path sourceFilePath = directory.resolve(fileName);

        if (Files.exists(sourceFilePath) && Files.isRegularFile(sourceFilePath)) {
            Path targetFilePath = targetDirectory.resolve(fileName);
            Files.move(sourceFilePath, targetFilePath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File moved to: " + targetFilePath.toString());
            logger.log(Level.INFO, "File moved to: " + targetFilePath.toString());
        } else {
            System.out.println("File does not exist: " + fileName);
            logger.log(Level.WARNING, "Attempted to move non-existent file: " + fileName);
        }
    }

    // Copy a file within a directory
    public void copyFile(Path directory, String fileName, Path targetDirectory) throws IOException {
        Path sourceFilePath = directory.resolve(fileName);

        if (Files.exists(sourceFilePath) && Files.isRegularFile(sourceFilePath)) {
            Path targetFilePath = targetDirectory.resolve(fileName);
            Files.copy(sourceFilePath, targetFilePath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File copied to: " + targetFilePath.toString());
            logger.log(Level.INFO, "File copied to: " + targetFilePath.toString());
        } else {
            System.out.println("File does not exist: " + fileName);
            logger.log(Level.WARNING, "Attempted to copy non-existent file: " + fileName);
        }
    }
}
