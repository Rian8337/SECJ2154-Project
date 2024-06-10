package src.inventory.search;

/**
 * Available filtering methods when processing items.
 */
public enum ItemFilterMethod {
    name,
    description;

    /**
     * Attempts to parse a number to an {@link ItemFilterMethod}.
     *
     * @param num The number to parse.
     * @return The parsed {@link ItemFilterMethod}.
     * @throws IllegalArgumentException If the number is invalid.
     */
    public static ItemFilterMethod from(short num) throws IllegalArgumentException {
        return switch (num) {
            case 1 -> name;
            case 2 -> description;
            default -> throw new IllegalArgumentException("Invalid method: " + num);
        };
    }

    /**
     * Lists all {@link ItemFilterMethod}s.
     */
    public static void listFilterMethods() {
        System.out.println("Available item filtering methods:");
        System.out.println("1. " + name);
        System.out.println("2. " + description);
    }

    @Override
    public String toString() {
        return switch (this) {
            case name -> "Name";
            case description -> "Description";
        };
    }
}
