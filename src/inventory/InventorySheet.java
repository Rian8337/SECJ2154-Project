package src.inventory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import src.item.Item;

/**
 * Represents an inventory sheet containing the state of an {@link InventoryManager}
 * at the time when this {@link InventorySheet} was created.
 */
public class InventorySheet {
    private final long creationTime;
    private final ArrayList<Item> items;

    public InventorySheet(ArrayList<Item> items) {
        creationTime = System.currentTimeMillis();

        this.items = items;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public List<Item> getItems() {
        return items;
    }

    public void print() {
        var sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        System.out.printf("Inventory report as of %s UTC\n\n", sdf.format(creationTime));
        System.out.printf("ID\tName%s\tType%s\tStock\tPrice (RM)\n", " ".repeat(50), " ".repeat(16));

        for (var item : items) {
            System.out.println(item.generateTableRow());
        }
    }
}
