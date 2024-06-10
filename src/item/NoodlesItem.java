package src.item;

import java.util.Scanner;

/**
 * Represents noodles.
 */
public class NoodlesItem extends FoodItem implements ItemWithWeight {
    private float weight;

    public NoodlesItem() {
        super(ItemType.noodles);
    }

    public NoodlesItem(long id) {
        super(id, ItemType.noodles);
    }

    public NoodlesItem(String name, String description, long stock, float price, float calories, float weight) {
        super(name, description, ItemType.noodles, stock, price, calories);

        this.weight = weight;
    }

    private NoodlesItem(NoodlesItem copy) {
        super(copy);

        this.weight = copy.weight;
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
    public NoodlesItem clone() {
        return new NoodlesItem(this);
    }
}
