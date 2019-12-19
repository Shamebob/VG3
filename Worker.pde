public class Worker extends Staff {
    ItemType serving;
    EnvironmentItem nearbyResource;
    boolean achievedGoal;
    Customer target;
    PVector startPos;


    public Worker (float x, float y, ItemType serving) {
        super(x, y);
        this.startPos = new PVector(x,y);
        this.currentFacing = Facing.DOWN;
        this.staffImage = SERVER_DOWN_IDLE;
        this.serving = serving;
        this.nearbyResource = null;
        this.achievedGoal = true;
        this.direction = new PVector(0,0);
    }

    public void setFacing(Facing facingDirection) {
        if(facingDirection == this.currentFacing)
            return;
        
        this.currentFacing = facingDirection;

        switch (facingDirection) {
            case UP:
                this.staffImage = SERVER_UP_IDLE;
                break;
            
            case DOWN:
                this.staffImage = SERVER_DOWN_IDLE;
                break;  
            
            case LEFT:
                this.staffImage = SERVER_LEFT_IDLE;
                break;
            
            case RIGHT:
                this.staffImage = SERVER_RIGHT_IDLE;
                break;
        }
    }

    private boolean isCloser(PVector newPos) {
        try {
            return (this.nearbyResource.getPos().sub(this.getPos()).mag() > newPos.sub(newPos).mag());
        } catch (NullPointerException e) {
            return false;
        }
    }

    private void findKeg() {
        System.out.println("Finding Keg");
        for(EnvironmentItem item : controller.items) {
            if(!(item instanceof Keg))
                continue;

            if(this.nearbyResource == null)
                this.nearbyResource = item;
            
            if(this.isCloser(item.getPos()))
                this.nearbyResource = item;
        }

    }

    private void findChicken() {
        System.out.println("Finding chicken");
        for(EnvironmentItem item : controller.items) {
            if(!(item instanceof Chicken))
                continue;
                
            if(this.nearbyResource == null)
                this.nearbyResource = item;
            
            if(this.isCloser(item.getPos()))
                this.nearbyResource = item;
        }
    }

    private void findCheese() {
        System.out.println("Finding cheese");
        for(EnvironmentItem item : controller.items) {
            if(!(item instanceof CheeseBarrel))
                continue;
                
            if(this.nearbyResource == null)
                this.nearbyResource = item;
            
            if(this.isCloser(item.getPos()))
                this.nearbyResource = item;
        }
    }

    private void findChalice() {
        System.out.println("Finding cheese");
        for(EnvironmentItem item : controller.items) {
            if(!(item instanceof ChaliceTable))
                continue;
                
            if(this.nearbyResource == null)
                this.nearbyResource = item;
            
            if(this.isCloser(item.getPos()))
                this.nearbyResource = item;
        }
    }

    private void findResource() {
        switch(this.serving) {
            case BEER:
                this.findKeg();
                break;
                
            case CHICKENLEG:
                this.findChicken();
                break;

            case CHALICE:
                this.findChalice();
                break;
            
            case CHEESE:
                this.findCheese();
                break;
        }
    }

    private void findCustomer() {
        boolean likesItem = false;
        for(Customer customer : controller.customers) {
            for(ItemType item : customer.likes) {
                if(item == this.serving) {
                     likesItem = true;
                     break;
                }
            }

            if(!likesItem || customer.entering || customer.leaving || customer.getDiminishingReturns() >= 30)
                continue;

            if(this.target == null)
                this.target = customer;
            
            if(this.isCloser(customer.getPos()))
                this.target = customer;
        }
    }

    @Override
    public void move(PVector change) {
        if (!achievedGoal) {
            if(this.target != null) {
                this.achievedGoal = this.targetSearch();
            } else if(this.nearbyResource != null) {
                this.achievedGoal = this.resourceSearch();
            }
        } else {
            if(this.inventory.size() == 0) {
                // System.out.println("Going to find resources");
                this.nearbyResource = null;
                this.findResource();      

                if(this.nearbyResource == null) {
                    this.beIdle();
                } else {
                    this.achievedGoal = false;
                }
                
            } else if(this.target == null) {
                // System.out.println("Going to find targets");
                this.findCustomer();
                if(this.target == null) {
                    this.beIdle();
                } else {
                    this.achievedGoal = false;
                }
            }
        }

        // System.out.println("Moving!: " +change);
        super.move(this.direction);
    }

    private void beIdle() {
        this.findDirection(this.startPos.copy());
        if(this.getPos().sub(this.startPos.copy()).mag() < 1) {
            this.direction = new PVector(0,0);
         }
        this.achievedGoal = true;
    }

    private boolean targetSearch() {
        if(controller.collisionDetector.checkCollision(this.target.getShape(), this.findZone())) {
            System.out.println("Found target!");
            this.useItem(1);
            this.target = null;
            return true;
        } else {
            this.findDirection(this.target.getPos());
            return false;
        }
    }

    private boolean resourceSearch() {
        if(controller.collisionDetector.checkCollision(this.nearbyResource.getShape(), this.findZone())) {
            System.out.println("Found resource!");
            this.direction = new PVector(0,0);
            while(this.inventory.size() < 5) {
                this.pickupItem();
            }
            this.nearbyResource = null;
            return true;
        } else {
            this.findDirection(this.nearbyResource.getPos());
            return false;
        }
    }

    private void findDirection(PVector targetPos) {
        PVector change = targetPos.sub(this.getPos()).normalize();
        float xChange = change.x;
        float yChange = change.y;
        float moveSize = 10;

        if(abs(xChange) > abs(yChange)) {
            if(xChange >= 0) {
                this.setFacing(Facing.RIGHT);
                this.direction = new PVector(moveSize, 0);
            } else {
                this.setFacing(Facing.LEFT);
                this.direction = new PVector(-moveSize, 0);
            }
        } else {
            if(yChange >= 0) {
                this.setFacing(Facing.DOWN);
                this.direction = new PVector(0, moveSize);
            } else {
                this.setFacing(Facing.UP);
                this.direction = new PVector(0, -moveSize);
            }
        }
    }

    public void fillInventory() {
    }

    @Override
    public void draw() {
        this.move(this.direction);
        super.draw();
    }

     @Override
    public void pickupItem() {
        this.staffImage = SERVER_PICKUP;
        super.pickupItem();
    }

    @Override
    public void useItem(int index) {
        this.staffImage = SERVER_USEITEM;
        super.useItem(index);
    }

}
