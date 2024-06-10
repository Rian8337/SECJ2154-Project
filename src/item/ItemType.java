package src.item;

/**
 * Available item types.
 */
public enum ItemType {
    none,
    cosmetic,
    kitchenware,
    softDrinks,
    breakfastSpread,
    noodles,
    washing,
    fruit,
    vegetables;

    /**
     * Attempts to parse a number to an {@link ItemType}.
     *
     * @param num The number to parse.
     * @return The parsed {@link ItemType}.
     * @throws IllegalArgumentException If the number is invalid.
     */
    public static ItemType from(short num) throws IllegalArgumentException {
        return switch (num) {
            case 1 -> cosmetic;
            case 2 -> kitchenware;
            case 3 -> softDrinks;
            case 4 -> breakfastSpread;
            case 5 -> noodles;
            case 6 -> washing;
            case 7 -> fruit;
            case 8 -> vegetables;
            default -> throw new IllegalArgumentException("Invalid item type: " + num);
        };
    }

    /**
     * Lists all {@link ItemType}s.
     */
    public static void listTypes() {
        System.out.println("Available item types:");
        System.out.println("1. " + cosmetic);
        System.out.println("2. " + kitchenware);
        System.out.println("3. " + softDrinks);
        System.out.println("4. " + breakfastSpread);
        System.out.println("5. " + noodles);
        System.out.println("6. " + washing);
        System.out.println("7. " + fruit);
        System.out.println("8. " + vegetables);
    }

    @Override
    public String toString() {
        return switch (this) {
            case none -> "None";
            case cosmetic -> "Cosmetic";
            case kitchenware -> "Kitchenware";
            case softDrinks -> "Soft Drinks";
            case breakfastSpread -> "Breakfast Spread";
            case noodles -> "Noodles";
            case washing -> "Washing";
            case fruit -> "Fruit";
            case vegetables -> "Vegetables";
        };
    }
}
