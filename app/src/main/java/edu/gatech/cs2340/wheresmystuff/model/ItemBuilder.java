package edu.gatech.cs2340.wheresmystuff.model;

import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
import java.util.Date;

/*
  @author John Abrams
 * @version 1.0
 */

/**
 * This class represents an Item Builder
 */
public class ItemBuilder implements ItemBuilderInterface {

    private Item item;
    private ArrayList<Double> location;

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
        item.setLatLng(item.getLatLng());
        return this;
    }

    @Override
    public ItemBuilder setDescription(final String desc) {
        item.setDescription(desc);
        return this;
    }

    @Override
    public ItemBuilder setCategory(final String cat) {
        item.setCategory(cat);
        return this;
    }

    @Override
    public ItemBuilder setStatus(final String stat) {
        item.setStatus(stat);
        return this;
    }


}
