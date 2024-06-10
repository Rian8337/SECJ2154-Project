package src.inventory.search;

import java.util.ArrayList;
import java.util.List;

import src.inventory.InventoryManager;
import src.item.ItemType;

/**
 * Utility class for querying an {@link InventoryManager}.
 */
public class InventorySearchQuery {
    private String query = "";
    private ItemSortMethod sortMethod = ItemSortMethod.id;
    private ItemFilterMethod filterMethod = ItemFilterMethod.name;
    private final ArrayList<ItemType> itemTypes = new ArrayList<>(5);

    public InventorySearchQuery() {}

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public ItemSortMethod getSortMethod() {
        return sortMethod;
    }

    public void setSortMethod(ItemSortMethod sortMethod) {
        this.sortMethod = sortMethod;
    }

    public ItemFilterMethod getFilterMethod() {
        return filterMethod;
    }

    public void setFilterMethod(ItemFilterMethod filterMethod) {
        this.filterMethod = filterMethod;
    }

    public List<ItemType> getItemTypes() {
        return itemTypes;
    }

    /**
     * Adds an {@link ItemType} to be included in the result.
     *
     * @param itemType The type of item.
     */
    public void addItemType(ItemType itemType) {
        itemTypes.add(itemType);
    }

    /**
     * Removes an {@link ItemType} from being included in the result.
     *
     * @param itemType The type of item.
     */
    public void removeItemType(ItemType itemType) {
        itemTypes.remove(itemType);
    }

    /**
     * Removes all {@link ItemType}s filter.
     */
    public void emptyItemTypes() {
        itemTypes.clear();
    }

    public void print() {
        System.out.println("Inventory Search Information");
        System.out.println("-".repeat(28));
        System.out.printf("Search term: %s\n", !query.isEmpty() ? query : "None");
        System.out.printf("Sort by: %s\n", sortMethod);
        System.out.printf("Filter by: %s\n", filterMethod);
        System.out.print("Included item types: ");

        if (!itemTypes.isEmpty()) {
            System.out.print(itemTypes.get(0));

            for (int i = 1; i < itemTypes.size(); ++i) {
                System.out.printf(", %s", itemTypes.get(i));
            }

            System.out.println();
        } else {
            System.out.println("All");
        }
    }
}
