package src.inventory.search;

/**
 * Available sorting methods when processing items.
 */
public enum ItemSortMethod {
    id,
    name,
    priceSmallestToHighest,
    priceHighestToSmallest,
    stockSmallestToHighest,
    stockHighestToSmallest;

    /**
     * Attempts to parse a number to an {@link ItemSortMethod}.
     *
     * @param num The number to parse.
     * @return The parsed {@link ItemSortMethod}.
     * @throws IllegalArgumentException If the number is invalid.
     */
    public static ItemSortMethod from(short num) throws IllegalArgumentException {
        return switch (num) {
            case 1 -> id;
            case 2 -> name;
            case 3 -> priceSmallestToHighest;
            case 4 -> priceHighestToSmallest;
            case 5 -> stockSmallestToHighest;
            case 6 -> stockHighestToSmallest;
            default -> throw new IllegalArgumentException("Invalid method: " + num);
        };
    }

    /**
     * Lists all {@link ItemSortMethod}s.
     */
    public static void listSortMethods() {
        System.out.println("Available item filtering methods:");
        System.out.println("1. " + id);
        System.out.println("2. " + name);
        System.out.println("3. " + priceSmallestToHighest);
        System.out.println("4. " + priceHighestToSmallest);
        System.out.println("5. " + stockSmallestToHighest);
        System.out.println("6. " + stockHighestToSmallest);
    }

    @Override
    public String toString() {
        return switch (this) {
            case id -> "ID";
            case name -> "Name";
            case priceSmallestToHighest -> "Price (smallest to highest)";
            case priceHighestToSmallest -> "Price (highest to smallest)";
            case stockSmallestToHighest -> "Stock (smallest to highest)";
            case stockHighestToSmallest -> "Stock (highest to smallest)";
        };
    }
}
