package fr.nkri.shell.cmds;

import fr.nkri.shell.MiniShell;

public interface ShellCommand {

    /*
    Command name
     */
    String getName();

    /*
    Command description
     */
    String getDescription();

    /*
    Command usage
     */
    String getUsage();

    /*
    Command action execute with past arguments
     */
    void action(final String[] args);

    /*
    Injection automatic
     */
    default void setShell(final MiniShell miniShell) {}
}