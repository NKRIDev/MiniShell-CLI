package fr.nkri.shell.enums;

public enum InputPattern {

    EMAIL("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$"),

    /*
    French phone
     */
    FR_PHONE("^\\d{10}$"),

    /*
    USA phone: (123) 456-7890 or 123-456-7890 or 1234567890
     */
    US_PHONE("^(\\(\\d{3}\\) ?|\\d{3}-?)\\d{3}-?\\d{4}$"),

    /*
    UK phone: 10 or 11 digits, may start with 0
     */
    UK_PHONE("^0\\d{9,10}$"),

    /*
    Username : 3-16 characters
     */
    USERNAME("^[a-zA-Z0-9_-]{3,16}$"),

    /*
    letters + numbers, 6-20 characters
     */
    PASSWORD("^(?=.*[0-9])(?=.*[a-zA-Z]).{6,20}$"),

    /*
    French postal code
     */
    ZIPCODE("^\\d{5}$"),

    /*
    Date in YYYY-MM-DD format
     */
    DATE_YYYY_MM_DD("^\\d{4}-\\d{2}-\\d{2}$"),

    /*
    Time HH:MM 24h
     */
    TIME_HH_MM("^([01]\\d|2[0-3]):([0-5]\\d)$"),

    /*
    Hexadecimal color (#fff or #ffffff)
     */
    HEX_COLOR("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$"),

    /*
    Basic URL
     */
    URL("^(https?://)?[\\w.-]+(?:\\.[\\w\\.-]+)+[/#?]?.*$"),
    ;

    private final String regex;

    /**
     * Enum for common input patterns using regular expressions.
     * @param regex code regex
     */
    private InputPattern(final String regex) {
        this.regex = regex;
    }

    public String getRegex() { return regex; }
}
