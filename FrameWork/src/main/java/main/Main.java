
package main;

import eventbroker.EventBroker;
import order.BlacklistOrderProcessor;
import order.Customer;
import order.OrderProcessor;

import java.util.LinkedList;


public class Main {
    
    public static int noOrders = 500;

    public static void main(String[] args){
        
        String[] names = new String[]{"Jan", "Piet", "Joris", "Corneel"};
        
        OrderProcessor orderProcessor = BlacklistOrderProcessor.Factory.createDefault();

        LinkedList<Thread> orders = new LinkedList<>();

        EventBroker broker = EventBroker.getEventBroker();
        broker.start();

        for(String name : names){
            Customer customer = new Customer(name);
            for(int i=0;i<noOrders;i++){
                int finalI = i;
                Thread t1 = new Thread(() -> customer.buy("item-"+ finalI));
                t1.start();
                orders.add(t1);
                try {Thread.sleep(0);}catch(InterruptedException _) {}
            }
        }

        for (Thread order : orders) {
            try {
                order.join();
            }
            catch(InterruptedException _) {}
        }
        broker.stop();
        System.out.println(OrderProcessor.getNumberOfOrders());
    }
    
}
