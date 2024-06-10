package src.item;

import java.util.Scanner;

/**
 * Represents an item.
 */
public abstract class Item implements Cloneable {
    protected ItemIdentifier identifier;
    protected long stock;
    protected float price;

    protected Item() {
        this(ItemType.none);
    }

    protected Item(Item copy) {
        identifier = new ItemIdentifier(copy.identifier);
        stock = copy.stock;
        price = copy.price;
    }

    protected Item(ItemType type) {
        identifier = new ItemIdentifier(type);
        stock = 0;
        price = 0;
    }

    protected Item(long id, ItemType type) {
        identifier = new ItemIdentifier(id, type);
        stock = 0;
        price = 0;
    }

    protected Item(String name, String description, ItemType type, long stock, float price) {
        identifier = new ItemIdentifier(name, description, type);
        this.stock = stock;
        this.price = price;
    }

    public ItemIdentifier getIdentifier() {
        return identifier;
    }

    public long getStock() {
        return stock;
    }

    public void setStock(long stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("The stock of an item must be equal to or more than zero.");
        }

        this.stock = stock;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        if (price <= 0) {
            throw new IllegalArgumentException("The price of an item must be more than zero.");
        }

        this.price = price;
    }

    /**
     * Generates a table row of this {@link Item}.
     */
    public String generateTableRow() {
        return String.format("%d\t%-50s\t%-16s\t%d\t%.2f", identifier.getID(), identifier.getName(), identifier.getType(), stock, price);
    }

    /**
     * Outputs this {@link Item} to the console.
     */
    public void print() {
        System.out.println("Item Information");
        System.out.println("-".repeat(16));
        System.out.printf("Name: %s\n", identifier.getName());
        System.out.printf("Description: %s\n", identifier.getDescription());
        System.out.printf("Type: %s\n", identifier.getType());
        System.out.printf("Stock: %d\n", stock);
        System.out.printf("Price: RM%.2f\n", price);
    }

    /**
     * Inputs data for this {@link Item}.
     */
    public void inputData() {
        identifier.inputData();

        var scanner = new Scanner(System.in);

        while (true) {
            System.out.printf("Enter item stock (currently %d): ", stock);

            try {
                setStock(Long.parseLong(scanner.nextLine()));
                break;
            } catch (Exception e) {
                System.out.println("Invalid stock.");
            }
        }

        while (true) {
            System.out.printf("Enter item price in RM (currently %.2f): ", price);

            try {
                setPrice(Float.parseFloat(scanner.nextLine()));
                break;
            } catch (Exception e) {
                System.out.println("Invalid price.");
            }
        }
    }

    @Override
    public abstract Item clone();
}
