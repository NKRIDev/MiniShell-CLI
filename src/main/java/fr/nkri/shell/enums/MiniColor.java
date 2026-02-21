package fr.nkri.shell.enums;

public enum MiniColor {

    /*
    Colors
     */
    BLACK("\033[30m"),
    DARK_BLUE("\033[34m"),
    DARK_GREEN("\033[32m"),
    DARK_AQUA("\033[36m"),
    DARK_RED("\033[31m"),
    DARK_PURPLE("\033[35m"),
    GOLD("\033[33m"),
    GRAY("\033[37m"),
    DARK_GRAY("\033[90m"),
    BLUE("\033[94m"),
    GREEN("\033[92m"),
    AQUA("\033[96m"),
    RED("\033[91m"),
    LIGHT_PURPLE("\033[95m"),
    YELLOW("\033[93m"),
    WHITE("\033[97m"),

    /*
    Styles
     */
    BOLD("\033[1m"),
    ITALIC("\033[3m"),
    UNDERLINE("\033[4m"),
    STRIKETHROUGH("\033[9m"),
    RESET("\033[0m")
    ;

    private final String code;

    /**
     * List of colors
     * @param code Color code for the CLI
     */
    MiniColor(final String code) {
        this.code = code;
    }

    /**
     * Displays a color with automatic reset
     * @param message display message
     * @param color color message
     * @return message with color
     */
    public static String color(final String message, final MiniColor color) {
        return color + message + RESET;
    }

    public String getCode() {
        return code;
    }

    public String toString() {
        return code;
    }
}
