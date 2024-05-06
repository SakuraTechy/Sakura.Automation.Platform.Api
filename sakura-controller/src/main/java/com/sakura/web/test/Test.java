package com.sakura.web.test;
import java.util.*;

public class Test {
    public static void main (String[] args) {
        ArrayList<String> list = new ArrayList<String>();
        list.add("V3.0B08D006");
        list.add("V3.0B08D007");
        list.add("V3.0B08D008");
        list.add("V3.0B08D009");
        System.out.println("Original list : " + list);
        Collections.sort(list);
        System.out.println("Sorted list : " + list);
        Collections.sort(list,Collections.reverseOrder());
        System.out.println("Sorted list using Collections.reverseOrder() : " + list);
    }
}
