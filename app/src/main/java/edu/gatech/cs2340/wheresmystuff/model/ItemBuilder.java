package edu.gatech.cs2340.wheresmystuff.model;

import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
import java.util.Date;

/*
 * @author John Abrams & Hartley McGuire
 * @version 1.1
 */

/**
 * This class represents an Item Builder
 */
public class ItemBuilder implements ItemBuilderInterface {

    private Item item;

    /**
     * Constructor
     */
    public ItemBuilder() {
        item = new Item();
    }

    @Override
    public Item build() {
        return item;
    }

    @Override
    public ItemBuilder setTitle(final String title) {
        item.setTitle(title);
        return this;
    }

    @Override
    public ItemBuilder setLatLng(final LatLng latLng) {
        ArrayList<Double> list = new ArrayList<>();
        list.add(latLng.latitude);
        list.add(latLng.longitude);
        item.setLatLng(list);
        return this;
    }

    @Override
    public ItemBuilder setDescription(final String desc) {
        item.setDescription(desc);
        return this;
    }

    @Override
    public ItemBuilder setCategory(final Item.Category cat) {
        item.setCategory(cat.toString());
        return this;
    }

    @Override
    public ItemBuilder setStatus(final Item.Status stat) {
        item.setStatus(stat.toString());
        return this;
    }

    @Override
    public ItemBuilder setUser(final String userID) {
        item.setUserID(userID);
        return this;
    }

}
