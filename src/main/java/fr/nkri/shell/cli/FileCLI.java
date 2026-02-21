package fr.nkri.shell.cli;

import fr.nkri.shell.enums.MiniColor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Custom file manager
 */
public class FileCLI {

    /**
     * List all files and directories in a given folder.
     * @param path folder path to list
     */
    public static void listFiles(final String path) {
        final File folder = new File(path);

        if(!folder.exists() || !folder.isDirectory()){
            MiniCLI.error("File not found!");
            return;
        }

        System.out.println(MiniColor.color("Content of:" + path, MiniColor.AQUA));
        for(File file : folder.listFiles()){
            if(file.isDirectory()) {
                System.out.println(MiniColor.color("[DIR] " + file.getName(), MiniColor.BLUE));
            }
            else {
                System.out.println(file.getName());
            }
        }
    }

    /**
     * Create a new file with content.
     * @param path file path to create
     * @param content text content to write in the file
     */
    public static void createFile(final String path, final String content) {
        try (FileWriter writer = new FileWriter(path)) {
            writer.write(content);
            System.out.println(MiniColor.color("File created successfully!", MiniColor.GREEN));
        }
        catch (final IOException e) {
            MiniCLI.error("Error creating file");
        }
    }

    /**
     * Delete a file.
     * @param path file path to delete
     */
    public static void deleteFile(final String path) {
        final File file = new File(path);
        if(!file.exists()){
            MiniCLI.error("File not found!");
            return;
        }

        if(file.delete()){
            System.out.println(MiniColor.color("File successfully deleted!", MiniColor.GREEN));
        }
        else {
            MiniCLI.error("Unable to delete the file!");
        }
    }

    /**
     * Read the content of a file.
     * @param path file path to read
     * @return content as string or null if error
     */
    public static String readFile(final String path) {
        try {
            return new String(Files.readAllBytes(Paths.get(path)));
        }
        catch (final IOException e) {
            MiniCLI.error("Error reading file");
            return null;
        }
    }
}