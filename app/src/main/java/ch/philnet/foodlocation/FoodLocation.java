package ch.philnet.foodlocation;

/**
 * Model class for FoodLocations
 */
public class FoodLocation implements Comparable<FoodLocation> {
    private String shop;
    private String desc;
    private String location;
    private boolean favorite;

    public FoodLocation(String _shop, String _desc, String _location, boolean _favorite) {
        shop = _shop;
        desc = _desc;
        location = _location;
        favorite = _favorite;
    }

    public String getShop() {
        return shop;
    }

    public String getDesc() {
        return desc;
    }

    public String getLocation() { return location; }

    public boolean getFavorite() { return favorite; }

    public void setFavorite(boolean _favorite) { favorite = _favorite; }

    @Override
    public int compareTo(FoodLocation another) {
        int last = this.shop.compareTo(another.shop);
        return last;
    }
}
