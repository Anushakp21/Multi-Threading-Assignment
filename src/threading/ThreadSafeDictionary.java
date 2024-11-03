package threading;

import java.util.concurrent.ConcurrentHashMap;

public class ThreadSafeDictionary<K, V> {
    private final ConcurrentHashMap<K, V> dictionary = new ConcurrentHashMap<>();

    // Method to add a key-value pair to the dictionary.
    public void put(K key, V value) {
        dictionary.put(key, value);
        System.out.println("Added (" + key + ", " + value + ")");
    }

    // Method to retrieve a value by key.
    public V get(K key) {
        return dictionary.get(key);
    }

    // Method to remove a key-value pair by key.
    public void remove(K key) {
        V value = dictionary.remove(key);
        System.out.println("Removed (" + key + ", " + value + ")");
    }

    // Method to check if a key exists in the dictionary.
    public boolean containsKey(K key) {
        return dictionary.containsKey(key);
    }

    // Method to print all key-value pairs.
    public void printAll() {
        dictionary.forEach((key, value) -> System.out.println(key + ": " + value));
    }

    public static void main(String[] args) {
        ThreadSafeDictionary<String, Integer> dictionary = new ThreadSafeDictionary<>();

        // Creating threads to add and remove entries concurrently.
        Thread writerThread = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                dictionary.put("Key" + i, i * 100);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread readerThread = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println("Value for Key" + i + ": " + dictionary.get("Key" + i));
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        writerThread.start();
        readerThread.start();

        try {
            writerThread.join();
            readerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("All entries in the dictionary:");
        dictionary.printAll();
    }
}

