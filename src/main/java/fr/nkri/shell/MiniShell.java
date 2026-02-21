package fr.nkri.shell;

import fr.nkri.shell.cli.MiniCLI;
import fr.nkri.shell.cmds.HelpCommand;
import fr.nkri.shell.cmds.ShellCommand;
import fr.nkri.shell.enums.MiniColor;

import java.util.*;

public abstract class MiniShell {

    /*
    Scanner of this shell
     */
    protected final Scanner scanner;

    /*
    List of custom shell commands
     */
    protected final Set<ShellCommand> shellCommands;

    /*
    List of environment variables
     */
    protected final Map<String, String> environmentVars;

    /*
    Shell settings : name and propt
     */
    private final String shellName;
    private final String prompt;

    /**
     * Base for creating a custom CLI shell
     * @param shellName shell name
     * @param prompt symbol that appears in the console to indicate
     *               that the user can type something
     */
    public MiniShell(String shellName, String prompt) {
        this.shellName = shellName;
        this.prompt = prompt;
        this.scanner = new Scanner(System.in);
        this.shellCommands = new HashSet<>();
        this.environmentVars = new HashMap<>();

        registerCommand(new HelpCommand(this));
    }

    /**
     * Method to implement for the shell's hello command
     */
    protected void welcome(){
        MiniCLI.printTitle("Welcome to Mini Shell by NKRI");
    }

    /**
     * Method to display the shell with its prompt
     */
    protected String prefix(){
        return MiniColor.color(this.shellName + this.prompt, MiniColor.AQUA);
    }

    /*
    Message configuration if no command is found
     */
    protected String commandNotFound(){
        return MiniColor.color("Unknown command! Type 'help' for commands.", MiniColor.RED);
    }

    /*
    Help message
     */
    public void helpMessages(){
        MiniCLI.printTitle(this.shellName + " - Help Commands");
        for(ShellCommand shellCommand : shellCommands){
            System.out.println(MiniColor.color(shellCommand.getUsage(), MiniColor.BOLD)
                    + " : " +shellCommand.getDescription());
        }
    }

    /**
     * Each shell defines how to handle a command line
     */
    protected abstract void handleProcess(final String input);

    /*
    Main shell loop
     */
    public void run(){
        welcome();

        while(true){
            System.out.print(prefix() + " ");
            String lineInput = scanner.nextLine();

            /*
            Check if user cancel shell
             */
            if(lineInput.equals("quit") || lineInput.equals("exit")){
                break;
            }

            /*
            Ignore if the field is empty
             */
            if(lineInput.isEmpty()){
                continue;
            }

            /*
            Replace if there is an environment variable
             */
            final String input = resolveVariables(lineInput);

            /*
            Found and run command
             */
            final String[] parts = input.split(" ");
            final String cmdName = parts[0];
            final String[] args = parts.length > 1 ? Arrays.copyOfRange(parts, 1, parts.length) : new String[0];

            /*
            Loop command
             */
            boolean hasFound = false;
            for(ShellCommand command : this.shellCommands){
                if(command.getName().equalsIgnoreCase(cmdName)){
                    command.action(args);
                    hasFound = true;
                    break;
                }
            }

            if(!hasFound){
                System.out.println(commandNotFound());
            }

            /*
            Direct implementation, if the user wants to
            retrieve the console input
             */
            handleProcess(input);
        }
    }

    /**
     * Save a command in the custom shell
     * @param command command to save
     */
    public void registerCommand(final ShellCommand command){
        command.setShell(this);
        this.shellCommands.add(command);
    }

    public void registerCommands(final ShellCommand... commands){
        for(ShellCommand command : commands){
            command.setShell(this);
            this.shellCommands.add(command);
        }
    }

    /**
     * Save environment variable in the custom shell without prefix
     * @param name environment variable name
     * @param value env vars value
     */
    public void registerEnvVar(final String name, final String value){
        this.environmentVars.put(name, value);
    }

    /**
     * Retrieve the value of an environment variable
     * @param name environment variable name
     * @return env vars value
     */
    public String getEnvVars(final String name){
        return this.environmentVars.get(name);
    }

    public void setEnvVar(final String name, final String value){
        this.environmentVars.put(name, value);
    }

    public boolean hasEnvVar(final String name){
        return this.environmentVars.containsKey(name);
    }

    public void removeEnvVar(final String name){
        this.environmentVars.remove(name);
    }

    /**
     * Shell would automatically replace env var with its value.
     * @param input user input
     * @return env var value
     */
    private String resolveVariables(String input){
        for(Map.Entry<String, String> entry : this.environmentVars.entrySet()){
            final String variable = "$" + entry.getKey();
            final String value = entry.getValue();

            if(input.contains(variable)){
                input = input.replace(variable, value);
            }
        }

        return input;
    }

    public String getPrompt() {
        return prompt;
    }

    public String getShellName() {
        return shellName;
    }
}
