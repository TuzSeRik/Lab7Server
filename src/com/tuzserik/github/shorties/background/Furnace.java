package com.tuzserik.github.shorties.background;

class Furnace {
    private Food food;

    Furnace(String name, int quantity) {
        food = new Food(name, quantity);
    }

    int getQuantity(){
        return food.quantity;
    }
    String getName(){
        return food.name;
    }

    void foodTaken(){
        food.decreaseQuantity();
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