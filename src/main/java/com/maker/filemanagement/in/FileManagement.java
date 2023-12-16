package com.maker.filemanagement.in;

import com.maker.filemanagement.FileManagementException;
import com.maker.filemanagement.out.IFileManagement;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * This class implements the IFileManagement interface and provides methods for
 * managing files and directories.
 */
public class FileManagement implements IFileManagement {

    /**
     * Creates a new file at the specified path.
     * @param path The path to the new file.
     * @return true if the file was created successfully, false otherwise.
     * @throws RuntimeException if an error occurs during file creation.
     */
    @Override
    public boolean createFile(String path) {
        String dir = getDir(path);
        if (!isDirExist(dir)) createDir(dir);
        if (isFileExist(path)) return false;
        try {
            File file = new File(path);
            return file.createNewFile();
        } catch (IOException e) {
            System.out.println("Error creating a new File");
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a new directory at the specified path.
     * @param path The path to the new directory.
     * @return true if the directory was created successfully, false otherwise.
     * @throws RuntimeException if an error occurs during directory creation.
     */
    @Override
    public boolean createDir(String path) {
        File directory = new File(path);
        return directory.mkdirs();
    }

    /**
     * Delete file at the specified path.
     * @param path The path of file to delete.
     * @return true if the file was deleted successfully, false otherwise.
     * @throws RuntimeException if an error occurs during file delete.
     */
    @Override
    public boolean deleteFile(String path) {
        File file = new File(path);
        return file.exists() && file.isFile() && file.delete();
    }

    /**
     * Deletes a directory and its contents at the specified path.
     * If the directory exists and is a valid directory, this method will delete
     * the directory along with all files and subdirectories within it.
     *
     * @param path The path of the directory to be deleted.
     * @return true if the directory and its contents were deleted successfully,
     *         false otherwise or if the path does not point to a valid directory.
     * @throws RuntimeException if an error occurs during deletion.
     */
    @Override
    public boolean deleteDir(String path) {
        File directory = new File(path);
        return directory.exists() && directory.isDirectory() && deleteDirectory(directory);
    }

    /**
     * Recursively deletes a directory and its contents.
     * If the specified file is a directory, all files and subdirectories within it
     * will be deleted. If it is a file, only the file itself will be deleted.
     *
     * @param directory The directory or file to be deleted.
     * @return true if the deletion was successful, false otherwise.
     * @throws RuntimeException if an error occurs during deletion.
     */
    private boolean deleteDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        }
        return directory.delete();
    }

    public List<File> getFilesOfDirectory(String path){
        File[] files = new File(path).listFiles();
        if (files!=null){
            return Arrays.stream(files).filter(File::isFile).toList();
        }else return List.of();
    }

    /**
     * Checks if a file exists at the specified path.
     *
     * @param path The path to the file.
     * @return true if a file exists at the specified path and it is a regular file,
     *         false otherwise.
     */
    @Override
    public boolean isFileExist(String path) {
        File file = new File(path);
        return file.exists() && file.isFile();
    }

    /**
     * Checks if a directory exists at the specified path.
     *
     * @param path The path to the directory.
     * @return true if a directory exists at the specified path, false otherwise.
     */
    @Override
    public boolean isDirExist(String path) {
        File directory = new File(path);
        return directory.exists() && directory.isDirectory();
    }

    /**
     * Reads the content of a file and returns it as a string.
     * @param path The path to the file.
     * @return The content of the file as a string.
     * @throws RuntimeException if an error occurs during file reading.
     */
    @Override
    public String readFile(String path) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return content.toString();
    }

    /**
     * Reads a specific line from a file.
     * @param path The path to the file.
     * @param line The line number to read (starting from 1).
     * @return The content of the specified line as a string.
     * @throws RuntimeException if an error occurs during file reading.
     */
    @Override
    public String readLine(String path, int line) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String currentLine;
            int currentLineNumber = 0;
            while ((currentLine = reader.readLine()) != null) {
                currentLineNumber++;
                if (currentLineNumber == line) {
                    return currentLine;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * Appends a line of content to a file at the specified path.
     * If the file does not exist, it will be created. If the file exists,
     * the content will be added to a new line.
     *
     * @param path The path to the file.
     * @param content The content to write as a new line.
     * @return true if the content was successfully written, false otherwise.
     * @throws RuntimeException if an error occurs during file writing.
     */
    @Override
    public boolean writeLine(String path, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
            writer.write(content);
            writer.newLine();
            return true;
        }catch (FileNotFoundException e){
            createDir( getDir(path) );
            writeLine(path, content);
            return true;
        }
        catch (IOException e) {
            System.out.println("Unknown error writing line");
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads all lines from a file and returns them as a list of strings.
     * @param path The path to the file.
     * @return A list containing all lines from the file.
     * @throws RuntimeException if an error occurs during file reading.
     */
    @Override
    public List<String> readAllLines(String path) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lines;
    }

    @Override
    public List<String> readLines(String path, int bound) {
        return readLines(path, 0, bound);
    }

    /**
     * Reads a range of lines from a file and returns them as a list of strings.
     * @param path The path to the file.
     * @param start The starting line number (inclusive).
     * @param bound The maximum number of lines to read.
     * @return A list containing the requested range of lines from the file.
     * @throws FileManagementException This is a custom exception for handling line limits on reading.
     */
    @Override
    public List<String> readLines(String path, int start, int bound) {
        List<String> lines = readAllLines(path);
        try{
            if(start <= 0 || start>lines.size() || bound<0 || bound > lines.size())
                throw new FileManagementException("Bounds are invalid!", lines.size());
        }catch (FileManagementException e){
            return lines; // we return the entire list of lines.
        }
        return IntStream.range(start-1, bound)// 1 is subtracted from the "start" param because the lines of the text file start counting from 1 instead of 0
                .mapToObj(lines::get)
                .toList();
    }

    /**
     * Extracts and returns the directory path from a given file path.
     * The directory path is obtained by removing the last component of the
     * provided file path.
     *
     * @param path The file path from which to extract the directory path.
     * @return The directory path containing all components except the last one.
     * @throws IllegalArgumentException if the provided path points to a file
     *         that is not located within a directory.
     */
    @Override
    public String getDir(String path) {
        List<String> words = new ArrayList<>(List.of(path.split("/")));
        if (words.size() <= 1) {
            throw new IllegalArgumentException("The provided path doesn't point to a file within a directory.");
        }
        words.remove(words.size() - 1);
        return String.join("/", words);
    }

}
