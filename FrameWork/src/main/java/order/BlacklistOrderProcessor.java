
package order;

import eventbroker.EventBroker;

import java.util.HashSet;
import java.util.Set;


public class BlacklistOrderProcessor extends OrderProcessor {

    private Set<String> blacklist;

    public static class Factory {
        public static BlacklistOrderProcessor createDefault() {
            BlacklistOrderProcessor res = new BlacklistOrderProcessor();
            EventBroker.getEventBroker().addEventListener(OrderEvent.TYPE_ORDER, res);
            return res;
        }
    }

    public BlacklistOrderProcessor(){
        super();
        
        // some work to get to the blacklist data
        try {
            Thread.sleep(10);
        } catch (InterruptedException _) {}
       
        // add the blacklisted customers
        blacklist = new HashSet<>();
        blacklist.add("Jan");
    }
    
    @Override
    protected void processOrder(OrderEvent order){
        // ignore blacklisted customers
        if(blacklist.contains(order.getCustomer())){
            System.out.println("Order of customer "+order.getCustomer()+" discarded");
            return;
        }
        
        super.processOrder(order);
    }
    
}
