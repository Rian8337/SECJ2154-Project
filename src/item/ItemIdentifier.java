package src.item;

import java.util.Scanner;

/**
 * A data class for identifying an {@link Item}.
 */
public class ItemIdentifier {
    private static long incrementalId;

    private long id;
    private String name;
    private String description;
    private final ItemType type;

    public ItemIdentifier(ItemType type) {
        this(++incrementalId, type);
    }

    public ItemIdentifier(long id, ItemType type) {
        this.id = id;
        name = "";
        description = "";
        this.type = type;
    }

    public ItemIdentifier(String name, String description, ItemType type) {
        this.id = ++incrementalId;
        this.name = name;
        this.description = description;
        this.type = type;
    }

    public ItemIdentifier(ItemIdentifier copy) {
        this.id = copy.id;
        this.name = copy.name;
        this.description = copy.description;
        this.type = copy.type;
    }

    public static void setIncrementalID(long id) {
        incrementalId = id;
    }

    public long getID() {
        return id;
    }

    public void setID(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ItemType getType() {
        return type;
    }

    /**
     * Prompts the user for data to be entered into this {@link ItemIdentifier}.
     */
    public void inputData() {
        var scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter the name of the item, up to 50 characters (currently " + (!name.isEmpty() ? name : "None") + "):");
            String name;

            try {
                name = scanner.nextLine();
                System.out.println();
            } catch (Exception e) {
                System.out.println("Invalid name.");
                continue;
            }

            if (name.isEmpty()) {
                System.out.println("Name cannot be empty.");
                continue;
            }

            if (name.length() > 50) {
                System.out.println("Name must not exceed 50 characters.");
                continue;
            }

            this.name = name;
            break;
        }

        while (true) {
            System.out.println("Enter the description of the item, up to 100 characters (currently " + (!description.isEmpty() ? description : "None") + "):");
            String description;

            try {
                description = scanner.nextLine();
                System.out.println();
            } catch (Exception e) {
                System.out.println("Invalid description.");
                continue;
            }

            if (description.isEmpty()) {
                System.out.println("Description cannot be empty.");
                continue;
            }

            if (description.length() > 100) {
                System.out.println("Description must not exceed 100 characters.");
                continue;
            }

            this.description = description;
            break;
        }
    }
}
