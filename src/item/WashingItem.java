package src.item;

/**
 * Represents a washing item.
 */
public class WashingItem extends Item {
    public WashingItem() {
        super(ItemType.washing);
    }

    public WashingItem(long id) {
        super(id, ItemType.washing);
    }

    public WashingItem(String name, String description, long stock, float price) {
        super(name, description, ItemType.cosmetic, stock, price);
    }

    private WashingItem(WashingItem copy) {
        super(copy);
    }

    @Override
    public WashingItem clone() {
        return new WashingItem(this);
    }
}
