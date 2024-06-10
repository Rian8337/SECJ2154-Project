package src.item;

/**
 * Represents a breakfast spread item.
 */
public class BreakfastSpreadItem extends FoodItem {
    public BreakfastSpreadItem() {
        super(ItemType.breakfastSpread);
    }

    public BreakfastSpreadItem(long id) {
        super(id, ItemType.breakfastSpread);
    }

    public BreakfastSpreadItem(String name, String description, long stock, float price, float calories) {
        super(name, description, ItemType.breakfastSpread, stock, price, calories);
    }

    private BreakfastSpreadItem(BreakfastSpreadItem copy) {
        super(copy);
    }

    @Override
    public BreakfastSpreadItem clone() {
        return new BreakfastSpreadItem(this);
    }
}
