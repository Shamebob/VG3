public class Cleaner {
    Cleaner() {
    }

    public void cleanGame() {
        cleanEnvironmentItems(controller.items);
        cleanFeelings(controller.feelings);
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


    
}