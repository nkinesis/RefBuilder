package br.com.refbuilder;

import java.util.Scanner;

/**
 * This program helps assembling URL references/citations to be used in
 * papers/articles following the ABNT guidelines
 *
 * @author Gabriel Cavalheiro Ullmann
 * @version 1.0
 * @since 2019-04-14
 */

// /home/gabriel/Documents/NetBeansProjects/RefBuilder/test/file1.txt
public class AppMain {

    public static void main(String[] args) {
        System.out.println("Enter file path:");
        Scanner sc = new Scanner(System.in);
        String path = sc.nextLine();
        ReferenceBuilder rb = new ReferenceBuilder();
        rb.buildFromFile(path);
    }

}
