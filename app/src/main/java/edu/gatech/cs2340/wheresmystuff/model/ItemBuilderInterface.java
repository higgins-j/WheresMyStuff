package edu.gatech.cs2340.wheresmystuff.model;

import com.google.android.gms.maps.model.LatLng;

/*
  @author John Abrams
 * @version 1.0
 */

/**
 *ItemBuilder Abstraction
 */
public interface ItemBuilderInterface {

    Item build();

    ItemBuilder setTitle(final String title);

    ItemBuilder setLatLng(final LatLng latlng);

    ItemBuilder setDescription(final String desc);

    ItemBuilder setCategory(final String cat);

    ItemBuilder setStatus(final String stat);
}
