package fr.nkri.shell.cli;

import fr.nkri.shell.enums.InputPattern;
import fr.nkri.shell.enums.LogLevel;
import fr.nkri.shell.enums.MiniColor;

import java.util.Scanner;

/**
 * A utility class with many methods to simplify
 * command interactions
 */
public class MiniCLI {

    protected static final Scanner scanner = new Scanner(System.in);

    /**
     * Displays a prompt with several options and returns the user's choice.
     *
     * @param message Option main message
     * @param options The different options to choose from
     * @return The chosen option string format
     */
    public static String promptChoice(final String message, final String... options){
        if(options == null || options.length == 0){
            throw new IllegalArgumentException("You must provide at least one option!");
        }

        int choice = -1;

        while (true){
            System.out.println(message);

            /*
            Display options index
             */
            for(int i =0; i < options.length; i++){
                System.out.println((i + 1) + ". " + options[i]);
            }

            System.out.println("Enter the number of your choice: ");

            /*
            Check if options is a number
             */
            final String input = scanner.nextLine();
            try{
                choice = Integer.parseInt(input);

                /*
                Check if the choice is within the range of options
                 */
                if(choice >= 1 && choice <= options.length){
                    break;
                }
                else{
                    System.out.println("Please enter a number between 1 and " + options.length);
                }
            }
            catch (final NumberFormatException e){
                System.out.println("Please enter a valid number !");
            }
        }

        return options[choice -1];
    }

    /**
     * Prompt Yes/No to confirm an action.
     * @param message confirm message
     * @return true is Yes or false if no or else error message
     */
    public static boolean promptYesNo(final String message){
        while (true){
            System.out.println(message + " (Y/N) : ");
            final String input = scanner.nextLine().trim().toLowerCase();

            /*
            Check input
             */
            if(input.equals("y") || input.equals("yes")){
                return true;
            }
            else if(input.equals("n") || input.equals("no")){
                return false;
            }
            else {
                System.out.println("Please enter Y (Yes) or N (No) !");
            }
        }
    }

    /**
     * Requests user input and validates it with a regular expression
     *
     * @param message message displayed to request input
     * @param regex regular expression that the input must respect
     * @param errorMsg message displayed if the input is invalid
     * @return Valid user input
     */
    public static String promptInput(final String message, final String regex, final String errorMsg) {
        while (true) {
            System.out.println(message + ": ");

            final String input = scanner.nextLine();
            if (input.matches(regex)){
                return input;
            }

            System.out.println(errorMsg);
        }
    }

    public static String promptInput(final String message, final InputPattern regex, final String errorMsg) {
        while (true) {
            System.out.println(message + ": ");

            final String input = scanner.nextLine();
            if (input.matches(regex.getRegex())){
                return input;
            }

            System.out.println(MiniColor.color(errorMsg, MiniColor.RED));
        }
    }

    public static String promptEmail(final String message, final String errorMsg) {
        return promptInput(message, InputPattern.EMAIL, errorMsg);
    }

    public static String promptUser(final String message, final String errorMsg) {
        return promptInput(message, InputPattern.USERNAME, errorMsg);
    }

    public static String promptFrPhone(final String message, final String errorMsg) {
        return promptInput(message, InputPattern.FR_PHONE, errorMsg);
    }

    public static String promptURL(final String message, final String errorMsg) {
        return promptInput(message, InputPattern.URL, errorMsg);
    }

    /**
     * Displays a stylish title with a decorative border and color
     * @param title text to display
     */
    public static void printTitle(final String title, final String titleColor, final String borderColor) {
        final String borderTop = "╔" + "═".repeat(title.length() + 4) + "╗";
        final String borderBottom = "╚" + "═".repeat(title.length() + 4) + "╝";

        System.out.println(borderColor + borderTop);
        System.out.println("║  " + titleColor + title + borderColor + "  ║");
        System.out.println(borderBottom + MiniColor.RESET);
    }

    public static void printTitle(final String title, final MiniColor titleColor, final MiniColor borderColor) {
        printTitle(title, titleColor.getCode(), borderColor.getCode());
    }

    public static void printTitle(final String title) {
        printTitle(title, MiniColor.WHITE.getCode() + MiniColor.BOLD.getCode(), MiniColor.DARK_BLUE.getCode());
    }

    /**
     * Error display
     * @param message message to display
     * @param level log level
     */
    public static void log(final String message, final LogLevel level){
        switch(level){
            case INFO:
                System.out.println(MiniColor.BLUE + "[INFO] " + message + MiniColor.RESET);
                break;

            case SUCCESS:
                System.out.println(MiniColor.GREEN + "[SUCCESS] " + message + MiniColor.RESET);
                break;

            case WARN:
                System.out.println(MiniColor.YELLOW + "[WARN] " + message + MiniColor.RESET);
                break;

            case ERROR:
                System.out.println(MiniColor.RED + "[ERROR] " + message + MiniColor.RESET);
                break;

            default:
                System.out.println(message);
        }
    }

    public static void error(final String message){
        System.out.println(MiniColor.color(message, MiniColor.RED));
    }
}