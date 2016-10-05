package com.endava.internship.silviarudicov.otherTrials;

/**
 * Created by srudicov on 10/5/2016.
 */
public class EnumTrial {

    public static void main(String[] args) {

        System.out.println(Numbers.ONE.equals(Numbers.ONE));    //true
        System.out.println(Numbers.ONE == Numbers.ONE);         //true

        System.out.println(Numbers.ONE.equals(Numbers.TWO));    //false
        System.out.println(Numbers.ONE == Numbers.TWO);         //false


        System.out.println(Numbers.valueOf("ONE"));  // e case-sensitive  //ONE
        System.out.println(Numbers.ONE.name());                           //ONE

        System.out.println(Numbers.ONE.ordinal());                        //0
        System.out.println(Numbers.TWO.ordinal());                        //1

        System.out.println(Numbers.ONE.compareTo(Numbers.TWO));            //-1
        System.out.println(Numbers.TWO.compareTo(Numbers.ONE));            //1

        System.out.println(Numbers.ONE.getClass());            //class com.endava.internship.silviarudicov.otherTrials.Numbers
        System.out.println(Numbers.ONE.getDeclaringClass());   //class com.endava.internship.silviarudicov.otherTrials.Numbers
    }
}
