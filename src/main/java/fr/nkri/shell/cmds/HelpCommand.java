package fr.nkri.shell.cmds;

import fr.nkri.shell.MiniShell;

public class HelpCommand implements ShellCommand {

    private final MiniShell miniShell;

    /**
     * default commande : help display command
     * @param miniShell parent shell
     */
    public HelpCommand(final MiniShell miniShell) {
        this.miniShell = miniShell;
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "help command that displays all commands";
    }

    @Override
    public String getUsage() {
        return "help";
    }

    @Override
    public void action(String[] args) {
        this.miniShell.helpMessages();
    }
}
