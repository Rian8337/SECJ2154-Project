package src.inventory;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import src.item.BreakfastSpreadItem;
import src.item.CosmeticItem;
import src.item.FoodItem;
import src.item.FruitItem;
import src.item.Item;
import src.item.ItemIdentifier;
import src.item.ItemType;
import src.item.ItemWithVolume;
import src.item.ItemWithWeight;
import src.item.KitchenwareItem;
import src.item.NoodlesItem;
import src.item.SoftDrinksItem;
import src.item.VegetableItem;
import src.item.WashingItem;

/**
 * Represents the backup of an {@link InventoryManager}.
 */
public class InventoryBackup {
    private final long creationTime;
    private final ArrayList<Item> items;
    private static final String backupDir = "./backups/";

    public InventoryBackup(ArrayList<Item> items) {
        creationTime = System.currentTimeMillis();

        this.items = items;
    }

    private InventoryBackup() {
        this(new ArrayList<>(10));
    }

    /**
     * Reads a backup from a file.
     *
     * @param filename The name of the file.
     * @return An {@link InventoryBackup} generated from the file, <code>null</code>
     * if the backup file contains an error.
     */
    public static InventoryBackup readBackup(String filename) {
        String dir = backupDir + filename;

        var backup = new InventoryBackup();
        long highestId = 0;

        try (var scanner = new Scanner(new File(dir))) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                int index = 0;

                long id = Long.parseLong(parts[index++]);
                String name = parts[index++];
                String description = parts[index++];

                var item = switch (ItemType.from(Short.parseShort(parts[3]))) {
                    case cosmetic -> new CosmeticItem(id);
                    case kitchenware -> new KitchenwareItem(id);
                    case softDrinks -> new SoftDrinksItem(id);
                    case breakfastSpread -> new BreakfastSpreadItem(id);
                    case noodles -> new NoodlesItem(id);
                    case washing -> new WashingItem(id);
                    case fruit -> new FruitItem(id);
                    case vegetables -> new VegetableItem(id);
                    default -> throw new IllegalArgumentException("Unable to infer item type from backup.");
                };

                var identifier = item.getIdentifier();
                identifier.setName(name);
                identifier.setDescription(description);
                item.setStock(Long.parseLong(parts[index++]));
                item.setPrice(Float.parseFloat(parts[index++]));

                // Extract additional data.
                if (item instanceof FoodItem foodItem) {
                    foodItem.setCalories(Float.parseFloat(parts[index++]));
                }

                if (item instanceof ItemWithVolume volumeItem) {
                    volumeItem.setVolume(Float.parseFloat(parts[index++]));
                }

                if (item instanceof ItemWithWeight weightItem) {
                    weightItem.setWeight(Float.parseFloat(parts[index]));
                }

                // Finally, insert the data and save the highest item ID.
                backup.items.add(item);
                highestId = Math.max(highestId, id);
            }
        } catch (Exception e) {
            return null;
        }

        // Set the incremental ID so that subsequent item additions have the correct ID.
        ItemIdentifier.setIncrementalID(highestId);

        return backup;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public List<Item> getItems() {
        return items;
    }

    /**
     * Obtains the filename of this {@link InventoryBackup} were it to be saved.
     */
    public String getFilename() {
        var sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");

        return sdf.format(creationTime) + ".backup";
    }

    /**
     * Saves this backup to a file.
     *
     * @return Whether the operation succeeded.
     */
    public boolean saveToFile() {
        var file = new File(backupDir, getFilename());

        try {
            if (!file.createNewFile()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        try (var writer = new FileWriter(file)) {
            for (var item : items) {
                var identifier = item.getIdentifier();

                writer.write(String.format("%d,%s,%s,%d,%d,%.2f", identifier.getID(), identifier.getName(), identifier.getDescription(), identifier.getType().ordinal(), item.getStock(), item.getPrice()));

                // Store additional data.
                if (item instanceof FoodItem foodItem) {
                    writer.write(String.format(",%.2f", foodItem.getCalories()));
                }

                if (item instanceof ItemWithVolume volumeItem) {
                    writer.write(String.format(",%.2f", volumeItem.getVolume()));
                }

                if (item instanceof ItemWithWeight weightItem) {
                    writer.write(String.format(",%.2f", weightItem.getWeight()));
                }

                writer.write("\n");
            }
        } catch (Exception e) {
            return false;
        }

        return true;
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
