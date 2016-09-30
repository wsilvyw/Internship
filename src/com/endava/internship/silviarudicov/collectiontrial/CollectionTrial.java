package com.endava.internship.silviarudicov.collectiontrial;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by srudicov on 9/29/2016.
 */
public class CollectionTrial {

    public static void main(String[] args){

        Map<User, Integer> points = new HashMap<>();

        User user = new User("Vova");
        //User user = new User();
        //user.setName("Vova");
        points.put(user, 10);

        user = new User("Vova");
        //user = new User();
        //user.setName("Vova");

        Integer p = points.get(user);

        System.out.println("Vova has " + p + " points.");
    }
}
