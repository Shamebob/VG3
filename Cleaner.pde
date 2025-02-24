/**
* The cleaner class is used to remove items from the game state in order to make things more efficient.
*/
public class Cleaner {
    Cleaner() {
    }

    // Dereference destroyed items in order to have them cleaned up by the JVM
    public void cleanGame() {
        cleanEnvironmentItems(controller.items);
        cleanFeelings(controller.feelings);
        cleanCustomers(controller.customers);
    }

    private void cleanEnvironmentItems(ArrayList<EnvironmentItem> list) {
        Iterator iter = list.iterator();
        EnvironmentItem curObj;
        while(iter.hasNext()) {
            curObj = (EnvironmentItem) iter.next();
            if(!curObj.isActive()) {
                iter.remove();
            }
        }
    }

    private void cleanFeelings(ArrayList<Feeling> list) {
        Iterator iter = list.iterator();
        Feeling curObj;
        while(iter.hasNext()) {
            curObj = (Feeling) iter.next();
            if(!curObj.isActive()) {
                iter.remove();
            }
        }
    }

    private void cleanCustomers(ArrayList<Customer> list) {
        Iterator iter = list.iterator();
        Customer curObj;
        while(iter.hasNext()) {
            curObj = (Customer) iter.next();
            if(!curObj.isActive()) {
                iter.remove();
            }
        }
    }

}