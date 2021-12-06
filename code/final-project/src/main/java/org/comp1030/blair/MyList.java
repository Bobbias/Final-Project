package org.comp1030.blair;

/**
 * Implements a doubly linked list.
 * 
 */

public class MyList {
    private Node head = null;
    private Node tail = null;

    public MyList() {

    }

    public void append(int x, int y) {
        Node newNode = new Node(x, y);
        if (head == null) {
            head = newNode;
            tail = head;
            head.next = null;
            head.prev = null;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
            tail.next = null;
        }
    }

    public void append(Ship ship) {
        Node newNode = new Node(ship);
        if (head == null) {
            head = newNode;
            tail = head;
            head.next = null;
            head.prev = null;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
            tail.next = null;
        }
    }

    public void printList() {
        Node current = head;
        if (head == null) {
            System.out.println("Doubly linked list is empty");
            return;
        }
        System.out.println("Nodes of doubly linked list: ");
        while (current != null) {
            //Print each node and then go to next.  
            StringBuilder sb = new StringBuilder();
            sb.append("(x: ");
            sb.append(current.getX());
            sb.append(", y: ");
            sb.append(current.getY());
            sb.append("),\n");
            System.out.print(sb);
            current = current.next;
        }
    }

    public int size() {
        int size = 0;
        Node current = head;
        while (current != null) {
            current = current.next;
            ++size;
        }
        return size;
    }

    public Node nodeAt(int loc) {
        Node current = head;
        if (current == null) {
            throw new IndexOutOfBoundsException("Node index was too high.");
        }
        for (int i = 0; i < loc; ++i) {
            current = current.next;
        }
        return current;
    }

    public void deleteNode(Node toBeDeleted) {
        if (toBeDeleted == head) {
            head = toBeDeleted.next;
        }
        if (toBeDeleted.prev != null) {
            toBeDeleted.next.prev = toBeDeleted.prev;
        }
        if (toBeDeleted.next != null) {
            toBeDeleted.prev.next = toBeDeleted.next;
        }
    }
}
