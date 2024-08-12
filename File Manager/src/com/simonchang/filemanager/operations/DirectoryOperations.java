package com.simonchang.filemanager.operations;

import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;

public class DirectoryOperations {

    // Method to create a directory
    public void createDirectory(Path dir) throws IOException {
        if (!Files.exists(dir)) {
            Files.createDirectory(dir);
            System.out.println("Directory created: " + dir.toString());
        } else {
            System.out.println("Directory already exists: " + dir.toString());
        }
    }

    // Method to delete a directory
    public void deleteDirectory(Path dir) throws IOException {
        if (Files.exists(dir)) {
            if (Files.isDirectory(dir)) {
                if (Files.list(dir).findAny().isEmpty()) {
                    Files.delete(dir);
                    System.out.println("Directory deleted: " + dir.toString());
                } else {
                    System.out.println("Directory is not empty, cannot delete: " + dir.toString());
                }
            } else {
                System.out.println("The specified path is not a directory: " + dir.toString());
            }
        } else {
            System.out.println("Directory does not exist: " + dir.toString());
        }
    }

    // Method to list the contents of a directory
    public void listDirectoryContents(Path dir) throws IOException {
        if (Files.exists(dir) && Files.isDirectory(dir)) {
            try (Stream<Path> stream = Files.list(dir)) {
                stream.forEach(path -> {
                    try {
                        System.out.println(path.getFileName() + " " +
                                Files.size(path) + " bytes, last modified: " +
                                Files.getLastModifiedTime(path));
                    } catch (IOException e) {
                        System.err.println("Error retrieving file details: " + e.getMessage());
                    }
                });
            }
        } else {
            System.out.println("Directory does not exist or is not a directory: " + dir.toString());
        }
    }
}