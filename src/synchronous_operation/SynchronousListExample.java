package synchronous_operation;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;

public class SynchronousListExample {

    // Thread-safe list using CopyOnWriteArrayList
    private final List<String> list = new CopyOnWriteArrayList<>();

    // Synchronized method to add an element to the list
    public synchronized void add(String element) {
        System.out.println("Adding element: " + element);
        list.add(element);
        System.out.println("Element added: " + element);
    }

    // Synchronized method to remove an element from the list by value
    public synchronized void remove(String element) {
        System.out.println("Removing element: " + element);
        list.remove(element);
        System.out.println("Element removed: " + element);
    }

    // Method to get an element at a specific index
    public String get(int index) {
        return list.get(index);
    }

    // Synchronized method to print all elements in the list
    public synchronized void printAllElements() {
        System.out.println("Current list elements:");
        for (String element : list) {
            System.out.println("Element: " + element);
        }
    }

    public static void main(String[] args) {
        SynchronousListExample example = new SynchronousListExample();

        // Creating and starting threads to add, remove, and print elements
        Thread thread1 = new Thread(() -> {
            example.add("Element-1");
            example.add("Element-2");
            example.add("Element-3");
        });

        Thread thread2 = new Thread(() -> {
            example.remove("Element-1");
            example.add("Element-4");
        });

        Thread thread3 = new Thread(() -> {
            example.printAllElements();
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

        System.out.println("Final state of list:");
        example.printAllElements();
    }
}

