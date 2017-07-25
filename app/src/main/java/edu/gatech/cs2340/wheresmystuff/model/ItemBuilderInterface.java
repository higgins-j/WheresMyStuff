package edu.gatech.cs2340.wheresmystuff.model;

import com.google.android.gms.maps.model.LatLng;

/*
 * ItemBuilder Abstraction
 * @author John Abrams
 * @version 1.0
 */
interface ItemBuilderInterface {

    Item build();

    ItemBuilder setTitle(final String title);

    ItemBuilder setLatLng(final LatLng latlng);

    ItemBuilder setDescription(final String desc);

    ItemBuilder setCategory(final Item.Category cat);

    ItemBuilder setStatus(final Item.Status stat);

    ItemBuilder setUser(final String userID);
}
