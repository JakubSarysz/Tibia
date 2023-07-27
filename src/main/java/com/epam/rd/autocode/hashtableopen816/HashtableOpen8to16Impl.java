package com.epam.rd.autocode.hashtableopen816;

import java.util.Arrays;

public class HashtableOpen8to16Impl implements HashtableOpen8to16 {
    private int minCapacity = 2;
    private int capacity = 8;
    private int maxCapacity = 16;
    private int size = 0;
    private Entry[] buckets;

    public HashtableOpen8to16Impl() {
        buckets = createBucketArray(capacity);
    }

    private Entry[] createBucketArray(int capacity) {
        return new Entry[capacity];
    }

    private int hashFunction(int key) {
        return Math.abs(key) % capacity;
    }

    private void doubleCapacity() {
        if (capacity * 2 <= maxCapacity) {
            capacity *= 2;
            rehash();
        }
    }

    private void halveCapacity() {
        if (capacity / 2 >= minCapacity) {
            capacity /= 2;
            rehash();
        }
    }

    private void rehash() {
        Entry[] oldBuckets = buckets;
        size = 0;
        buckets = createBucketArray(capacity);
        for (Entry entry : oldBuckets) {
            while (entry != null) {
                insert(entry.key, entry.value);
                entry = entry.next;
            }
        }
    }

    @Override
    public void insert(int key, Object value) {
        int index = hashFunction(key);
        Entry entry = buckets[index];
        while (entry != null) {
            if (entry.key == key) {
                entry.value = value;
                return;
            }
            entry = entry.next;
        }
        Entry newEntry = new Entry(key, value);
        newEntry.next = buckets[index];
        buckets[index] = newEntry;
        size++;

        if (size > capacity) {
            throw new IllegalStateException("Hashtable capacity exceeded!");
        }

        if (size == capacity) {
            doubleCapacity();
        }
    }

    @Override
    public Object search(int key) {
        int index = hashFunction(key);
        Entry entry = buckets[index];
        while (entry != null) {
            if (entry.key == key) {
                return entry.value;
            }
            entry = entry.next;
        }
        return null;
    }

    @Override
    public void remove(int key) {
        int index = hashFunction(key);
        Entry entry = buckets[index];
        Entry prev = null;
        while (entry != null) {
            if (entry.key == key) {
                if (prev == null) {
                    buckets[index] = entry.next;
                } else {
                    prev.next = entry.next;
                }
                size--;

                if (size > 0 && size <= capacity / 4) {
                    halveCapacity();
                }
                return;
            }
            prev = entry;
            entry = entry.next;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int[] keys() {
        int[] result = new int[size];
        int index = 0;
        for (Entry entry : buckets) {
            while (entry != null) {
                result[index++] = entry.key;
                entry = entry.next;
            }
        }
        return result;
    }

    private static class Entry {
        int key;
        Object value;
        Entry next;

        Entry(int key, Object value) {
            this.key = key;
            this.value = value;
        }
    }
}
