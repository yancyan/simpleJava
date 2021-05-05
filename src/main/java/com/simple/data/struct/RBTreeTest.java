package com.simple.data.struct;

import javax.swing.*;
import java.util.Scanner;

public class RBTreeTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RBTree<String, Object> rbTree = new RBTree<>();
        while (true) {
            System.out.println("input key:");
            String key = scanner.next();
            rbTree.insert(key, null);
        }
    }
}
