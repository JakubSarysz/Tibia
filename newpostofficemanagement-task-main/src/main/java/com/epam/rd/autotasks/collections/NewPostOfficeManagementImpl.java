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
        Objects.requireNonNull(boxes, "Input collection cannot be null");
        for (Box box : boxes) {
            Objects.requireNonNull(box, "Input collection cannot contain null values");
        }
        parcels = new ArrayList<>(boxes);
    }

    @Override
    public Optional<Box> getBoxById(int id) {
        for (Box box : parcels) {
            if (box.getId() == id) {
                return Optional.of(box);
            }
        }
        return Optional.empty();
    }

    @Override
    public String getDescSortedBoxesByWeight() {
        List<Box> sortedParcels = new ArrayList<>(parcels);
        sortedParcels.sort(Comparator.comparing(Box::getWeight).reversed());

        StringBuilder result = new StringBuilder();
        int size = sortedParcels.size();
        for (int i = 0; i < size; i++) {
            String line = sortedParcels.get(i).toString().trim(); // Remove leading/trailing spaces
            result.append(line);
            if (i < size - 1) {
                result.append("\n");
            }
        }
        return result.toString();
    }


    @Override
    public String getAscSortedBoxesByCost() {
        List<Box> sortedParcels = new ArrayList<>(parcels);
        sortedParcels.sort(Comparator.comparing(Box::getCost));

        StringBuilder result = new StringBuilder();
        int size = sortedParcels.size();
        for (int i = 0; i < size; i++) {
            result.append(sortedParcels.get(i).toString());
            if (i < size - 1) {
                result.append("\n");
            }
        }
        return result.toString();
    }


    @Override
    public List<Box> getBoxesByRecipient(String recipient) {
        Objects.requireNonNull(recipient, "Recipient cannot be null.");
        List<Box> recipientParcels = new ArrayList<>();

        for (Box box : parcels) {
            if (recipient.equals(box.getRecipient())) {
                recipientParcels.add(box);
            }
        }

        recipientParcels.sort(Comparator.comparing(Box::getId)); // Sort by ID
        return recipientParcels;
    }
}
