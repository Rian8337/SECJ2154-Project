package src.inventory.search;

import java.util.ArrayList;
import java.util.List;

import src.inventory.InventoryManager;
import src.item.Item;

/**
 * Represents the search result of an {@link InventoryManager}.
 */
public class InventorySearchResult {
    private final ArrayList<Item> items;

    public InventorySearchResult(ArrayList<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }

    public void print() {
        System.out.println("Inventory Search Result");
        System.out.println("-".repeat(23));
        System.out.printf("ID\tName%s\tType%s\tStock\tPrice\n", "-".repeat(50), "-".repeat(16));

        for (var item : items) {
            System.out.println(item.generateTableRow());
        }
    }
}
