package fr.nkri.shell.utils;

import fr.nkri.shell.enums.MiniColor;

public class ProgressBar {

    private final int total;
    private final char symbol;
    private final MiniColor color;

    /**
     * Progress bar
     * @param total Bar length (in characters)
     */
    public ProgressBar(final int total) {
        this(total, '=', MiniColor.GREEN);
    }

    /**
     * Progress bar
     *
     * @param total Bar length (in characters)
     * @param symbol Fill symbol
     * @param color mini color
     */
    public ProgressBar(final int total, final char symbol, final MiniColor color) {
        this.total = total;
        this.symbol = symbol;
        this.color = color;
    }

    /**
     * Updates the progress bar
     * @param current current value
     */
    public void update(final int current) {
        final int filled = (int) ((current / (double) total) * total);
        final StringBuilder bar = new StringBuilder();

        bar.append("[");
        for(int i = 0; i < total; i++){
            if(i < filled){
                bar.append(symbol);
            }
            else{
                bar.append(" ");
            }
        }
        bar.append("] ");
        bar.append((int) ((current / (double) total) * 100)).append("%");

        System.out.print("\r" + color + bar.toString() + "\u001B[0m");

        if(current >= total){
            System.out.println();
        }
    }
}
