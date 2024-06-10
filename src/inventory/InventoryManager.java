package src.inventory;

import java.util.ArrayList;
import java.util.List;

import src.inventory.search.InventorySearchQuery;
import src.inventory.search.InventorySearchResult;
import src.inventory.search.ItemSortMethod;
import src.item.Item;

/**
 * The manager of an inventory.
 */
public class InventoryManager {
    private final ArrayList<Item> items = new ArrayList<>(10);

    /**
     * Adds an {@link Item} to this {@link InventoryManager}.
     *
     * @param item The {@link Item} to add.
     */
    public void addItem(Item item) {
        int index = getItemIndex(item.getIdentifier().getID());

        // Check if the item already exists.
        if (index < items.size() - 1 && items.get(index).getIdentifier().getID() == item.getIdentifier().getID()) {
            return;
        }

        items.add(index, item);
    }

    /**
     * Removes an {@link Item} from this {@link InventoryManager}.
     *
     * @param item The {@link Item}.
     * @return Whether the {@link Item} was removed.
     */
    public boolean removeItem(Item item) {
        return removeItem(item.getIdentifier().getID());
    }

    /**
     * Removes an {@link Item} from this {@link InventoryManager}.
     *
     * @param id The ID of the {@link Item}.
     * @return Whether the {@link Item} was removed.
     */
    public boolean removeItem(long id) {
        if (items.isEmpty()) {
            return false;
        }

        int index = getItemIndex(id);

        // Make sure that the item is what we actually want to remove.
        if (index > items.size() - 1 || items.get(index).getIdentifier().getID() != id) {
            return false;
        }

        items.remove(index);

        return true;
    }

    /**
     * Searches for an {@link Item} with the given ID using the binary search algorithm.
     * <p>
     * The binary search algorithm narrows the location of the {@link Item} by comparing it against the middle
     * {@link Item} of the given range. If the {@link Item} is on the left side of the range (<code>left</code>),
     * the range will be shrunk to the left side of the array. If the {@link Item} is on the right side of the range
     * (<code>right</code>), the range will be shrunk to the right side of the array. This operation is repeated
     * until the range is invalid (<code>left > right</code>) or the {@link Item} is found.
     * </p>
     *
     * @param id The ID of the {@link Item}.
     * @return The {@link Item}, <code>null</code> if not found.
     */
    public Item getItem(long id) {
        int index = getItemIndex(id);

        if (index > items.size() - 1 || items.get(index).getIdentifier().getID() != id) {
            return null;
        }

        return items.get(index);
    }

    /**
     * Gets the {@link Item}s in this {@link InventoryManager}.
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * Gets the sorted {@link Item}s on this {@link InventoryManager}.
     *
     * @param sortMethod The sort method.
     * @return The sorted {@link Item}s.
     */
    public List<Item> getItems(ItemSortMethod sortMethod) {
        var items = copyItems(false);

        sortItems(items, sortMethod);

        return items;
    }

    /**
     * Searches for {@link Item}s in this {@link InventoryManager}.
     *
     * @param query The query to search for.
     * @return An {@link InventorySearchResult} containing the search result.
     */
    public InventorySearchResult searchItems(InventorySearchQuery query) {
        var items = new ArrayList<Item>(this.items.size());

        // Apply the filter first and then sort, otherwise the sort operation may be more expensive.
        for (var item : this.items) {
            var identifier = item.getIdentifier();
            boolean insert = true;

            if (!query.getQuery().isEmpty()) {
                insert = switch (query.getFilterMethod()) {
                    case name -> identifier.getName().toLowerCase().contains(query.getQuery().toLowerCase());
                    case description -> identifier.getDescription().toLowerCase().contains(query.getQuery().toLowerCase());
                };
            }

            if (!query.getItemTypes().isEmpty()) {
                insert = query.getItemTypes().contains(identifier.getType());
            }

            if (insert) {
                items.add(item);
            }
        }

        sortItems(items, query.getSortMethod());

        return new InventorySearchResult(items);
    }

    /**
     * Loads a backup into this {@link InventoryManager}.
     *
     * @param backup The {@link InventoryBackup} to load.
     * @return Whether the operation succeeded.
     */
    public boolean loadBackup(InventoryBackup backup) {
        // Remove current items first.
        items.clear();

        for (var item : backup.getItems()) {
            addItem(item.clone());
        }

        return true;
    }

    /**
     * Performs a backup of the {@link Item}s in this {@link InventoryManager}.
     *
     * @return An {@link InventoryBackup} containing the backup of this {@link InventoryManager}'s current state.
     */
    public InventoryBackup createBackup() {
        return new InventoryBackup(copyItems(true));
    }

    /**
     * Creates an {@link InventorySheet} of this {@link InventoryManager}.
     *
     * @return An {@link InventorySheet} representing the current state of this {@link InventoryManager}.
     */
    public InventorySheet createSheet() {
        return new InventorySheet(copyItems(true));
    }

    /**
     * Performs a binary insertion sort on an array of items.
     *
     * @param items The array of items to sort.
     * @param method The method to sort the items for.
     */
    private static void sortItems(ArrayList<Item> items, ItemSortMethod method) {
        for (int i = 1; i < items.size(); i++) {
            int j = i - 1;
            var item = items.get(i);

            // We need to search the location where an item should belong using the binary search algorithm.
            // The binary search algorithm narrows the location of the item by comparing it against the middle
            // item of the given range. If the item is on the left side of the range (`left`), the range will be shrunk
            // to the left side of the array. If the item is on the right side of the range (`right`), the range will be
            // shrunk to the right side of the array. This operation is repeated until the range becomes invalid
            // (`left > right`) or the supposed location of the item is found.
            int left = 0;
            int right = j;

            while (left <= right) {
                int mid = left + (right - left) / 2;
                var midItem = items.get(mid);

                // Legend:
                // - `0` = item is equal
                // - `>0` = item is on the left
                // - `<0` = item is on the right
                int comparison = switch (method) {
                    case id -> Long.compare(item.getIdentifier().getID(), midItem.getIdentifier().getID());
                    case name -> item.getIdentifier().getName().toLowerCase().compareTo(midItem.getIdentifier().getName().toLowerCase());
                    case priceSmallestToHighest, priceHighestToSmallest -> Float.compare(item.getPrice(), midItem.getPrice());
                    case stockSmallestToHighest, stockHighestToSmallest -> Long.compare(item.getStock(), midItem.getStock());
                };

                if (comparison >= 0) {
                    // Two possibilities: the "same" item is found, or the item is on the left side of the range.
                    left = mid + 1;

                    // The "same" item is found, use the next index for insertion.
                    if (comparison == 0) {
                        break;
                    }
                } else {
                    // Item is on the right side of the range.
                    right = Math.max(mid - 1, 0);
                }
            }

            int insertionIndex = left;

            // Move all elements after the index to create space for the selected index.
            for (int k = i; k > insertionIndex; k--) {
                items.set(k, items.get(k - 1));
            }

            items.set(insertionIndex, item);
        }

        if (method == ItemSortMethod.priceHighestToSmallest || method == ItemSortMethod.stockHighestToSmallest) {
            // Reverse the array if the method is descending.
            for (int i = 0; i < items.size() / 2; i++) {
                var temp = items.get(i);
                items.set(i, items.get(items.size() - i - 1));
                items.set(items.size() - i - 1, temp);
            }
        }
    }

    /**
     * Creates a copy of the {@link Item}s in this {@link InventoryManager}.
     *
     * @param deep Whether to deep copy the {@link Item}s.
     * @return The copied {@link Item}s.
     */
    private ArrayList<Item> copyItems(boolean deep) {
        var copy = new ArrayList<Item>(items.size());

        for (var item : items) {
            copy.add(deep ? item.clone() : item);
        }

        return copy;
    }

    /**
     * Gets the index of an {@link Item} with an ID using the binary search algorithm.
     * <p>
     * The binary search algorithm narrows the location of the {@link Item} by comparing it against the middle
     * {@link Item} of the given range. If the {@link Item} is on the left side of the range (<code>left</code>),
     * the range will be shrunk to the left side of the array. If the {@link Item} is on the right side of the range
     * (<code>right</code>), the range will be shrunk to the right side of the array. This operation is repeated
     * until the range is invalid (<code>left > right</code>) or the {@link Item} is found.
     * </p>
     *
     * @param id The ID of the {@link Item}.
     * @return The index of the {@link Item} with the ID. If the {@link Item} is not found, the method
     * will return the index at which the item with that ID would be entered in.
     */
    private int getItemIndex(long id) {
        if (items.isEmpty()) {
            return 0;
        }

        int left = 0;
        int right = items.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            var midItem = items.get(mid);

            long midId = midItem.getIdentifier().getID();

            if (midId == id) {
                return mid;
            } else if (midId < id) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return left;
    }
}
