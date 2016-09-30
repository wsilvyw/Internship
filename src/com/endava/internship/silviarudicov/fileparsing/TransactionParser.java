package com.endava.internship.silviarudicov.fileparsing;

import java.io.IOException;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by srudicov on 9/29/2016.
 */
public class TransactionParser {

    // PRIVATE
    private static Path fFilePath;
    private final static Charset ENCODING = StandardCharsets.UTF_8;
    private static List<Sale> sales = new ArrayList<Sale>();
    private static int totalAmount;
    private static Sale mostExpenciveProd;

    public static void main(String[] args) {
        try{
            fFilePath = Paths.get(args[0]); //("D:\\Internship\\transactions.txt");

            try (Scanner scanner =  new Scanner(fFilePath, ENCODING.name())){
                while (scanner.hasNextLine()){
                    processLine(scanner.nextLine());
                }
            }catch(IOException e){
            Logger.getLogger(e.getMessage(),"");
            }
            /*
            Collections.sort(sales, Collections.reverseOrder());
            System.out.println("List of object sorted in descending order : " + sales);

            Collections.sort(sales, new Sale.OrderByProduct());
            System.out.println("List of object sorted by product: " + sales);
            */

            Map<String, Integer> customerMap = new TreeMap<>();
            mostExpenciveProd = sales.get(1);
            int mostExpenciveProdAmount = mostExpenciveProd.getAmount();

            for (Sale sale:sales) {
                if(sale.getAmount() > mostExpenciveProdAmount)
                    mostExpenciveProd = sale;
                if(customerMap.get(sale.getCustomer()) == null)
                    customerMap.put(sale.getCustomer(), sale.getAmount());
                else
                    customerMap.put(sale.getCustomer(),customerMap.get(sale.getCustomer())+ sale.getAmount());
            }

            System.out.println("\nAccount Total");
            for (Map.Entry<String, Integer> entry : customerMap.entrySet()) {
                String key = entry.getKey().toString();
                Integer value = entry.getValue();
                System.out.println(key + " " + value);
            }


            Map<String, Product> productMap = new TreeMap<>();
            int i=1;
            for (Sale sale:sales) {
                if(productMap.get(sale.getProduct()) == null){
                    Product product = new Product(sale.getProduct(), i, sale.getAmount());
                    productMap.put(sale.getProduct(), product);
                }else{
                    int amount = (productMap.get(sale.getProduct())).getAmount();
                    Product product = new Product(sale.getProduct(), amount+1, sale.getAmount());
                    productMap.put(sale.getProduct(),product);
                }
            }

            System.out.println("\nProduct Quantity");
            for (Map.Entry<String, Product> entry : productMap.entrySet()) {
                String key = entry.getKey().toString();
                Product value = entry.getValue();
                System.out.println(key + " " + value.getAmount() + "*" + value.getPrice());
            }

            System.out.println("\nMost expensive Product: " + mostExpenciveProd.getProduct());
            System.out.println("\nTotal: " + totalAmount);

        }catch(Exception e){
            Logger.getLogger(e.getMessage(),"");
        }
    }

    private static void processLine(String aLine){
        //use a second Scanner to parse the content of each line
        Scanner scanner = new Scanner(aLine);
        scanner.useDelimiter(",");
        if (scanner.hasNext()){
            //assumes the line has a certain structure
            String customer  = scanner.next();
            String product = scanner.next();
            int amount = Integer.parseInt((scanner.next()).trim());
            totalAmount += amount;
            //log("Customer is : " + customer .trim() + ", product is : " +product.trim() + ", amount is : " +amount);
            Sale sale = new Sale(customer,product,amount);
            sales.add(sale);
        }
        else {
            log("Empty or invalid line. Unable to process.");
        }
    }

    private static void log(Object aObject){
        System.out.println(String.valueOf(aObject));
    }
}
