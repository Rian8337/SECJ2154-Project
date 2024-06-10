package src;

import java.util.Scanner;

import src.inventory.InventoryBackup;
import src.inventory.InventoryManager;
import src.inventory.search.InventorySearchQuery;
import src.inventory.search.ItemFilterMethod;
import src.inventory.search.ItemSortMethod;
import src.item.BreakfastSpreadItem;
import src.item.CosmeticItem;
import src.item.FruitItem;
import src.item.Item;
import src.item.ItemType;
import src.item.KitchenwareItem;
import src.item.NoodlesItem;
import src.item.SoftDrinksItem;
import src.item.VegetableItem;
import src.item.WashingItem;

public class Main {
    private static final InventoryManager inventoryManager = new InventoryManager();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        var line = "=".repeat(35);
        var mainMenuLine = "=".repeat(9);

        System.out.println("Inventory Management System");
        System.out.println(line);

        while (true) {
            System.out.println();
            System.out.println("Main Menu");
            System.out.println(mainMenuLine);
            System.out.println("1. Add item");
            System.out.println("2. Remove item");
            System.out.println("3. Edit item");
            System.out.println("4. Display item details");
            System.out.println("5. List items");
            System.out.println("6. Search items");
            System.out.println("7. Backup items");
            System.out.println("8. Load backup");
            System.out.println("9. Exit");
            System.out.println();

            short selection = getSelection((short) 9, false);

            System.out.println();
            System.out.println(line);
            System.out.println();

            switch (selection) {
                case 1 -> addItem();
                case 2 -> removeItem();
                case 3 -> editItem();
                case 4 -> displayItemDetails();
                case 5 -> listItems();
                case 6 -> searchItems();
                case 7 -> backupItems();
                case 8 -> loadBackup();
                case 9 -> {
                    System.out.println("Thank you for using the inventory management system!");
                    scanner.close();

                    return;
                }
            }
        }
    }

    private static void addItem() {
        ItemType.listTypes();

        System.out.println();

        ItemType itemType;

        while (true) {
            short selection = getSelection((short) ItemType.values().length);

            if (selection == -1) {
                return;
            }

            try {
                itemType = ItemType.from(selection);
                System.out.println();
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid item type.");
            }
        }

        var item = constructItem(itemType);
        inventoryManager.addItem(item);

        System.out.println();
        System.out.println("Item added.");
    }

    private static void removeItem() {
        if (inventoryManager.getItems().isEmpty()) {
            System.out.println("No items are in the inventory manager.");
            return;
        }

        var item = promptUserForItem();

        if (item == null) {
            return;
        }

        inventoryManager.removeItem(item);
        System.out.println("Item removed.");
    }

    private static void editItem() {
        if (inventoryManager.getItems().isEmpty()) {
            System.out.println("No items are in the inventory manager.");
            return;
        }

        var item = promptUserForItem();

        if (item != null) {
            item.inputData();
        }
    }

    private static void displayItemDetails() {
        if (inventoryManager.getItems().isEmpty()) {
            System.out.println("No items are in the inventory manager.");
            return;
        }

        var item = promptUserForItem();

        if (item != null) {
            item.print();
        }
    }

    private static void listItems() {
        if (inventoryManager.getItems().isEmpty()) {
            System.out.println("No items are in the inventory manager.");
            return;
        }

        inventoryManager.createSheet().print();
    }

    private static void searchItems() {
        if (inventoryManager.getItems().isEmpty()) {
            System.out.println("No items are in the inventory manager.");
            return;
        }

        var line = "=".repeat(17);
        var searchQuery = new InventorySearchQuery();

        while (true) {
            System.out.println("Search Menu");
            System.out.println(line);
            System.out.println("1. Set sorting method");
            System.out.println("2. Set filtering method");
            System.out.println("3. Enter search terms");
            System.out.println("4. Add item type filter");
            System.out.println("5. Remove item type filter");
            System.out.println("6. Remove all item type filters");
            System.out.println("7. Display search information");
            System.out.println("8. Search items");
            System.out.println("9. Return to main menu");

            short selection = getSelection((short) 9, false);

            if (selection == 9) {
                return;
            }

            System.out.println();
            System.out.println(line);
            System.out.println();

            switch (selection) {
                case 1 -> {
                    ItemSortMethod.listSortMethods();
                    System.out.println();

                    while (true) {
                        short sortSelection = getSelection((short) ItemSortMethod.values().length);
                        System.out.println();

                        if (sortSelection == -1) {
                            break;
                        }

                        try {
                            var method = ItemSortMethod.from(sortSelection);

                            searchQuery.setSortMethod(method);

                            System.out.printf("Successfully set item sorting method to %s.\n", method);
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid selection.");
                        }
                    }
                }

                case 2 -> {
                    ItemFilterMethod.listFilterMethods();
                    System.out.println();

                    while (true) {
                        short filterSelection = getSelection((short) ItemFilterMethod.values().length);
                        System.out.println();

                        if (filterSelection == -1) {
                            break;
                        }

                        try {
                            var method = ItemFilterMethod.from(filterSelection);

                            searchQuery.setFilterMethod(method);

                            System.out.printf("Successfully set item filtering method to %s.\n", method);
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid selection.");
                        }
                    }
                }

                case 3 -> {
                    try {
                        System.out.print("Enter search term: ");
                        searchQuery.setQuery(scanner.nextLine());

                        System.out.println();
                        System.out.printf("Successfully set the search term to %s.", !searchQuery.getQuery().isEmpty() ? searchQuery.getQuery() : "None");
                    } catch (Exception e) {
                        System.out.println("Invalid search term.");
                    }
                }

                case 4 -> {
                    ItemType.listTypes();
                    System.out.println();

                    while (true) {
                        short typeSelection = getSelection((short) ItemType.values().length);

                        if (typeSelection == -1) {
                            break;
                        }

                        try {
                            var type = ItemType.from(typeSelection);

                            searchQuery.addItemType(type);

                            System.out.printf("Added item type %s to the filter.\n", type);

                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid selection.");
                        }
                    }
                }

                case 5 -> {
                    ItemType.listTypes();
                    System.out.println();

                    while (true) {
                        short typeSelection = getSelection((short) ItemType.values().length);

                        if (typeSelection == -1) {
                            break;
                        }

                        try {
                            var type = ItemType.from(typeSelection);

                            searchQuery.removeItemType(type);

                            System.out.printf("Removed item type %s from the filter.\n", type);

                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid selection.");
                        }
                    }
                }

                case 6 -> {
                    searchQuery.emptyItemTypes();
                    System.out.println("Item type filters have been deleted.");
                }

                case 7 -> searchQuery.print();

                case 8 -> {
                    var result = inventoryManager.searchItems(searchQuery);

                    if (result.getItems().isEmpty()) {
                        System.out.print("No items were found.");
                        break;
                    }

                    result.print();
                    System.out.println();
                    System.out.println("Existing search settings will be saved as long as this menu is active.");
                }
            }

            System.out.println();
        }
    }

    private static void backupItems() {
        if (inventoryManager.getItems().isEmpty()) {
            System.out.println("No items are in the inventory manager.");
            return;
        }

        var backup = inventoryManager.createBackup();

        if (backup.saveToFile()) {
            System.out.printf("Backup saved as %s.\n", backup.getFilename());
        } else {
            System.out.println("Failed to save backup.");
        }
    }

    private static void loadBackup() {
        System.out.print("Enter the name of the backup file: ");
        String filename;

        while (true) {
            try {
                filename = scanner.nextLine();
                break;
            } catch (Exception e) {
                System.out.println("Invalid filename.");
            }
        }

        var backup = InventoryBackup.readBackup(filename);

        if (backup == null) {
            System.out.println("Failed to load backup.");
            return;
        }

        if (backup.getItems().isEmpty()) {
            System.out.println("No items were found in the backup file.");
            return;
        }

        if (inventoryManager.loadBackup(backup)) {
            System.out.println("Loaded backup into the inventory manager.");
        } else {
            System.out.println("Failed to load backup.");
        }
    }

    private static Item constructItem(ItemType itemType) {
        var item = switch (itemType) {
            case cosmetic -> new CosmeticItem();
            case kitchenware -> new KitchenwareItem();
            case softDrinks -> new SoftDrinksItem();
            case breakfastSpread -> new BreakfastSpreadItem();
            case noodles -> new NoodlesItem();
            case washing -> new WashingItem();
            case fruit -> new FruitItem();
            case vegetables -> new VegetableItem();
            default -> throw new IllegalArgumentException("Unable to infer item type.");
        };

        item.inputData();
        return item;
    }

    private static Item promptUserForItem() {
        while (true) {
            System.out.print("Enter the ID of the item, -1 to go back: ");
            long id;

            try {
                id = Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid ID.");
                continue;
            }

            if (id == -1) {
                return null;
            }

            Item item = inventoryManager.getItem(id);

            if (item == null || item.getIdentifier().getID() != id) {
                System.out.printf("No item with ID %d was found in the inventory manager.\n", id);
                continue;
            }

            System.out.println();
            return item;
        }
    }

    private static short getSelection(short highest) {
        return getSelection(highest, true);
    }

    private static short getSelection(short highest, boolean allowGoingBack) {
        short selection;

        while (true) {
            System.out.printf("Enter your selection (%d-%d)%s: ", (short) 1, highest, allowGoingBack ? ", -1 to go back" : "");

            try {
                selection = Short.parseShort(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid selection.");
                continue;
            }

            if ((!allowGoingBack || selection != -1) && (selection < 1 || selection > highest)) {
                System.out.println("Invalid selection.");
                continue;
            }

            break;
        }

        return selection;
    }
}
