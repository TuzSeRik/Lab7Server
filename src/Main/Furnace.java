package Main;

class Furnace {
    private Food food;

    Furnace(String name, int quantity) {
        food = new Food(name, quantity);
    }

    void foodTaken(){
        food.decreaseQuantity();
    }

    int getQuantity(){
        return food.quantity;
    }

    String getName(){
        return food.name;
    }




    class Food{
        private String name;
        private int quantity;

        Food(String name, int quantity){
            this.name = name;
            this.quantity = quantity;
        }

        void decreaseQuantity(){
            quantity--;
        }
    }
}
//+