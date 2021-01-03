package channelpopularity.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Results implements FileDisplayInterface, StdoutDisplayInterface {
    File file;
    FileWriter fwrite;

    public Results(String fname) {
        try{
            file = new File(fname);
            fwrite = new FileWriter(file);
        }
        catch (IOException e)
        {
            e.getMessage();
            System.out.println(e);
        }
    }

    /**
     * Prints output to console
     */
    public void printToConsole(StringBuilder text)
    {
        System.out.println(text+" ");
    }

    /**
     * Add a new line after previous line is processed
     */
    public void addNewlineToConsole() {
        System.out.print("\n");
    }

    /**
     * Write the output in a File
     */
    public void addOutputToFile(StringBuilder text) {
        try {
            if(file.length() == 0) {
                fwrite = new FileWriter(file, false);
            }
            else {
                fwrite = new FileWriter(file, true);
            }
            fwrite.write(text + " ");
        } catch (IOException e) {
            e.getMessage();
            System.out.println(e);
        }
    }

    /**
     * Add new line to output in a File
     */
    public void addNewLineToFile() {
        try {
            if(fwrite.toString().isEmpty())
                fwrite.write("\n");
            else
                fwrite.append("\n");
        } catch (IOException e) {
            e.getMessage();
            System.out.println(e);
        }
    }

    /**
     * To close the opened File
     */
    public void closeFile() {
        try{
            fwrite.close();
        }
        catch (IOException e)
        {
            e.getMessage();
            System.out.println(e);
        }
    }
}
