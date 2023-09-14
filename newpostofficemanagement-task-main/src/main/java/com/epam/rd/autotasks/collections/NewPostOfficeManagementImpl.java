package com.epam.rd.autotasks.collections;

import java.math.BigDecimal;
import java.util.*;

public class NewPostOfficeManagementImpl implements NewPostOfficeManagement {
    private List<Box> parcels;

    /**
     * Creates own storage and appends all parcels into it.
     * It must add either all the parcels or nothing, if an exception occurs.
     *
     * @param boxes a collection of parcels.
     * @throws NullPointerException if the parameter is {@code null}
     *                              or contains {@code null} values.
     */
    public NewPostOfficeManagementImpl(Collection<Box> boxes) {
        // place your code here
    }

    @Override
    public Optional<Box> getBoxById(int id) {
        // place your code here
        return null;
    }

    @Override
    public String getDescSortedBoxesByWeight() {
        // place your code here
        return null;
    }

    @Override
    public String getAscSortedBoxesByCost() {
        // place your code here
        return null;
    }

    @Override
    public List<Box> getBoxesByRecipient(String recipient) {
        // place your code here
        return null;
    }
}
