package src.item;

import java.util.Scanner;

/**
 * Represents a soft drink item.
 */
public class SoftDrinksItem extends FoodItem implements ItemWithWeight, ItemWithVolume {
    private float weight;
    private float volume;

    public SoftDrinksItem() {
        super(ItemType.softDrinks);
    }

    public SoftDrinksItem(long id) {
        super(id, ItemType.softDrinks);
    }

    public SoftDrinksItem(String name, String description, long stock, float price, float calories, float weight, float volume) {
        super(name, description, ItemType.softDrinks, stock, price, calories);

        this.volume = volume;
    }

    private SoftDrinksItem(SoftDrinksItem copy) {
        super(copy);

        this.weight = copy.weight;
        this.volume = copy.volume;
    }

    @Override
    public float getWeight() {
        return weight;
    }

    @Override
    public void setWeight(float weight) {
        this.weight = weight;
    }

    @Override
    public float getVolume() {
        return volume;
    }

    @Override
    public void setVolume(float volume) {
        if (volume < 0) {
            throw new IllegalArgumentException("The volume of an item must be more than or equal to zero.");
        }

        this.volume = volume;
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

        while (true) {
            System.out.printf("Enter item volume in mL (currently %.2f): ", volume);

            try {
                setVolume(Float.parseFloat(scanner.nextLine()));
                break;
            } catch (Exception e) {
                System.out.println("Invalid volume.");
            }
        }
    }

    @Override
    public void print() {
        super.print();

        System.out.printf("Volume: %.2fmL\n", volume);
    }

    @Override
    public SoftDrinksItem clone() {
        return new SoftDrinksItem(this);
    }
}
