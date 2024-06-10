package src.item;

import java.util.Scanner;

/**
 * Represents a kitchenware item.
 */
public class KitchenwareItem extends Item {
    private String material = "";

    public KitchenwareItem() {
        super(ItemType.kitchenware);
    }

    public KitchenwareItem(long id) {
        super(id, ItemType.kitchenware);
    }

    public KitchenwareItem(String name, String description, long stock, float price, String material) {
        super(name, description, ItemType.kitchenware, stock, price);

        this.material = material;
    }

    private KitchenwareItem(KitchenwareItem copy) {
        super(copy);

        material = copy.material;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    @Override
    public void inputData() {
        super.inputData();

        var scanner = new Scanner(System.in);

        while (true) {
            System.out.printf("Enter kitchenware item material (currently %s): ", !material.isEmpty() ? material : "None");
            String material;

            try {
                material = scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Invalid material.");
                continue;
            }

            if (material.isEmpty()) {
                System.out.println("Material cannot be empty.");
                continue;
            }

            setMaterial(material);
            break;
        }
    }

    @Override
    public void print() {
        super.print();

        System.out.println("Material: " + material);
    }

    @Override
    public KitchenwareItem clone() {
        return new KitchenwareItem(this);
    }
}
