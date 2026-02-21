package fr.nkri.shell.cmds;

import fr.nkri.shell.MiniShell;

/**
 * Directly retrieve the Mini Shell parent to access
 * its methods from the commands
 */
public class AbstractShellCommand implements ShellCommand {

    protected MiniShell shell;

    public MiniShell getShell() {
        return shell;
    }

    @Override
    public void setShell(MiniShell shell) {
        this.shell = shell;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public String getUsage() {
        return "";
    }

    @Override
    public void action(String[] args) {
    }
}
