package src.item;

/**
 * Represents a cosmetic item.
 */
public class CosmeticItem extends Item {
    public CosmeticItem() {
        super(ItemType.cosmetic);
    }

    public CosmeticItem(long id) {
        super(id, ItemType.cosmetic);
    }

    public CosmeticItem(String name, String description, long stock, float price) {
        super(name, description, ItemType.cosmetic, stock, price);
    }

    private CosmeticItem(CosmeticItem copy) {
        super(copy);
    }

    @Override
    public CosmeticItem clone() {
        return new CosmeticItem(this);
    }
}
