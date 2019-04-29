package Server.Background;

public class Furnace {
    private Food food;

    public Furnace(String name, int quantity) {
        food = new Food(name, quantity);
    }

    void foodTaken(){
        food.decreaseQuantity();
    }

    public int getQuantity(){
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