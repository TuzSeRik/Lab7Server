package com.tuzserik.github.shorties.background;

import java.io.IOException;
import java.io.ObjectOutputStream;

class Simulation {
    private Shorty veryPrevious = null;
    private Shorty previous = null;
    private Shorty current = null;
    private Shorty reserve = null;
    private Collection collection;
    private Furnace furnace;
    private Human human;
    private ObjectOutputStream output;

    Simulation(Collection collection, Furnace furnace, Human human, ObjectOutputStream output) throws IOException{
        this.collection = collection;
        this.furnace = furnace;
        this.human = human;
        this.output = output;

        simulate();
    }

    private void simulate() throws IOException {
        while (furnace.getQuantity() > 0){
            veryPrevious = collection.getQueue().poll();
            previous = collection.getQueue().poll();
            if (Math.random()>0.5){
                veryPrevious.takeFood(furnace);

            } else if (!previous.isBeaten()) veryPrevious.beat(previous);
                else  veryPrevious.takeFood(furnace);
            while (!collection.getQueue().isEmpty()){
                current = collection.getQueue().poll();
                if (Math.random()>0.5){
                    if (!previous.beat(veryPrevious)){
                        if (!previous.beat(current)){
                            reserve = previous;
                            previous = veryPrevious;
                            veryPrevious = reserve;
                        }
                    }
                }
            }
        }
    }


}
//++




/*{  void simulate(ObjectOutputStream os) throws IOException {
        while (furnace.getQuantity() > 0) {
            try {
                while (x == null) {
                    x = collection.getRandomMember();
                }
                while (y == null) {
                    y = collection.getRandomMember();
                }
                x.Beat(y, os);
            } catch (NullPointerException e) {
                os.writeUTF("Коллекция пуста, драться некому!");
                break;
            }

            if (x.getIsWinner()) {
                x.takeFood(furnace, os);
                for (int i = 0; i < 2; i++) {
                    while (z == null) {
                        z = collection.getRandomMember();
                    }
                    z.Buzz(x, os);
                }
            }
            Collection.Rest();
        }

        try {
            human.takeFood(furnace, os);
        } catch (Exception e) {
            try {
                collection.getBest().Laugh(human, os);
            } catch (NullPointerException ex) {
                os.writeUTF("Да не работает же!");
            }
        }
    }}*/