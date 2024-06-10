package src.item;

import java.util.Scanner;

/**
 * Represents a fruit item.
 */
public class FruitItem extends FoodItem implements ItemWithWeight {
    private float weight;

    public FruitItem() {
        super(ItemType.fruit);
    }

    public FruitItem(long id) {
        super(id, ItemType.fruit);
    }

    public FruitItem(String name, String description, long stock, float price, float calories, float weight) {
        super(name, description, ItemType.fruit, stock, price, calories);

        this.weight = weight;
    }

    private FruitItem(FruitItem copy) {
        super(copy);

        weight = copy.weight;
    }

    @Override
    public float getWeight() {
        return weight;
    }

    @Override
    public void setWeight(float weight) {
        if (weight < 0) {
            throw new IllegalArgumentException("The weight of an item must be more than or equal to zero.");
        }

        this.weight = weight;
    }

    @Override
    public void inputData() {
        super.inputData();

        var scanner = new Scanner(System.in);

        while (true) {
            System.out.printf("Enter item weight in g (currently %.2f): ", weight);

            try {
                setWeight(Float.parseFloat(scanner.nextLine()));
                break;
            } catch (Exception e) {
                System.out.println("Invalid weight.");
            }
        }
    }

    @Override
    public void print() {
        super.print();

        System.out.printf("Weight: %.2fg\n", weight);
    }

    @Override
    public FruitItem clone() {
        return new FruitItem(this);
    }
}
