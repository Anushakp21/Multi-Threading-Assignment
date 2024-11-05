package synchronous_operation;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class SynchronousMapExample {

    // A ConcurrentHashMap for thread-safe operations on a shared map
    private final ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

    // Synchronized method to put an entry in the map
    public synchronized void put(String key, Integer value) {
        System.out.println("Putting key: " + key + ", value: " + value);
        map.put(key, value);
        System.out.println("Key: " + key + " added successfully.");
    }

    // Synchronized method to remove an entry by key
    public synchronized void remove(String key) {
        System.out.println("Removing key: " + key);
        map.remove(key);
        System.out.println("Key: " + key + " removed successfully.");
    }

    // Method to get a value associated with a key (no need for explicit synchronization)
    public Integer get(String key) {
        return map.get(key);
    }

    // Synchronized method to print all entries in the map
    public synchronized void printAllEntries() {
        System.out.println("Current map entries:");
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }
    }

    public static void main(String[] args) {
        SynchronousMapExample example = new SynchronousMapExample();

        // Creating and starting threads to put, remove, and print entries
        Thread thread1 = new Thread(() -> {
            example.put("A", 1);
            example.put("B", 2);
            example.put("C", 3);
        });

        Thread thread2 = new Thread(() -> {
            example.remove("A");
            example.put("D", 4);
        });

        Thread thread3 = new Thread(() -> {
            example.printAllEntries();
        });

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Final state of map:");
        example.printAllEntries();
    }
}

