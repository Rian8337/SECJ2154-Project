package src.item;

import java.util.Scanner;

/**
 * Represents a food item.
 */
public abstract class FoodItem extends Item {
    protected float calories;

    protected FoodItem(ItemType type) {
        super(type);
    }

    protected FoodItem(long id, ItemType type) {
        super(id, type);
    }

    protected FoodItem(String name, String description, ItemType type, long stock, float price, float calories) {
        super(name, description, type, stock, price);

        this.calories = calories;
    }

    protected FoodItem(FoodItem copy) {
        super(copy);

        calories = copy.calories;
    }

    public float getCalories() {
        return calories;
    }

    public void setCalories(float calories) {
        if (calories < 0) {
            throw new IllegalArgumentException("The calories of a food must be equal to or more than zero.");
        }

        this.calories = calories;
    }

    @Override
    public void inputData() {
        super.inputData();

        var scanner = new Scanner(System.in);

        while (true) {
            System.out.printf("Enter food item calories in kcal (currently %.2f): ", calories);

            try {
                setCalories(Float.parseFloat(scanner.nextLine()));
                break;
            } catch (Exception e) {
                System.out.println("Invalid calories.");
            }
        }
    }

    @Override
    public void print() {
        super.print();

        System.out.printf("Calories: %.2f kcal\n", calories);
    }
}