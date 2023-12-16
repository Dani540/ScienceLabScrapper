package com.maker.filemanagement.out;

import com.maker.filemanagement.FileManagementException;

import java.util.List;

/**
 * This interface defines methods for file and directory management operations.
 */
public interface IFileManagement {
    /**
     * Creates a new file at the specified path.
     * @param path The path to the new file.
     * @return true if the file was created successfully, false otherwise.
     */
    boolean createFile(String path);

    /**
     * Creates a new directory at the specified path.
     * @param path The path to the new directory.
     * @return true if the directory was created successfully, false otherwise.
     */
    boolean createDir(String path);

    /**
     * Deletes the file at the specified path.
     * @param path The path to the file to be deleted.
     * @return true if the file was deleted successfully, false otherwise.
     */
    boolean deleteFile(String path);

    /**
     * Deletes the directory at the specified path.
     * @param path The path to the directory to be deleted.
     * @return true if the directory was deleted successfully, false otherwise.
     */
    boolean deleteDir(String path);

    /**
     * Checks if a file exists at the specified path.
     * @param path The path to the file.
     * @return true if the file exists, false otherwise.
     */
    boolean isFileExist(String path);

    /**
     * Checks if a directory exists at the specified path.
     * @param path The path to the directory.
     * @return true if the directory exists, false otherwise.
     */
    boolean isDirExist(String path);

    /**
     * Reads the entire content of a file and returns it as a string.
     * @param path The path to the file.
     * @return The content of the file as a string.
     */
    String readFile(String path);

    /**
     * Reads a specific line from a file.
     * @param path The path to the file.
     * @param line The line number to read (starting from 1).
     * @return The content of the specified line as a string.
     */
    String readLine(String path, int line);

    /**
     * Reads all lines from a file and returns them as a list of strings.
     * @param path The path to the file.
     * @return A list containing all lines from the file.
     */
    List<String> readAllLines(String path);

    /**
     * Reads a specified number of lines from a file and returns them as a list of strings.
     * @param path The path to the file.
     * @param bound The maximum number of lines to read.
     * @return A list containing the requested number of lines from the file.
     */
    List<String> readLines(String path, int bound);

    /**
     * Reads a range of lines from a file and returns them as a list of strings.
     * @param path The path to the file.
     * @param start The starting line number (inclusive).
     * @param bound The maximum number of lines to read.
     * @throws FileManagementException This is a custom exception for read handling
     * @return A list containing the requested range of lines from the file.
     */
    List<String> readLines(String path, int start, int bound) throws FileManagementException;

    /**
     * Writes a line of content to a file.
     * If the file does not exist, it will be created.
     * @param path The path to the file.
     * @param content The content to write.
     * @return true if the content was written successfully, false otherwise.
     */
    boolean writeLine(String path, String content);

    String getDir(String fileToTest);
}

