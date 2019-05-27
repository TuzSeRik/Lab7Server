package com.tuzserik.github.shorties.server.background;

import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.concurrent.PriorityBlockingQueue;

class Simulation {
    private PriorityBlockingQueue<Shorty> collection;
    private PriorityBlockingQueue<Shorty> recollection;
    private Collection.Commands commands;
    private Furnace furnace;
    private Human human;
    private ObjectOutputStream output;


    Simulation(Collection.Commands commands,PriorityBlockingQueue<Shorty> collection, PriorityBlockingQueue<Shorty> recollection, Furnace furnace, Human human, ObjectOutputStream output) throws IOException{
        this.commands = commands;
        this.collection = collection;
        this.recollection = recollection;
        this.furnace = furnace;
        this.human = human;
        this.output = output;

        simulate();
    }

    private void simulate() throws IOException {
        output.writeUTF("Да начнется битва!");
        while (furnace.getQuantity() > 0){
            Shorty veryPrevious = collection.poll();
            Shorty previous = collection.poll();
            Shorty current;
            if (Math.random()>0.5){
                veryPrevious.takeFood(furnace);
            } else if (!previous.isBeaten()) veryPrevious.beat(previous);
                else  veryPrevious.takeFood(furnace);
            while (!collection.isEmpty()){
                current = collection.poll();
                if (Math.random()>0.5){
                    if (!previous.beat(veryPrevious)){
                        if (!previous.beat(current)){
                            if (veryPrevious.isBeaten()) veryPrevious.heal();
                            Shorty reserve = previous;
                            previous = veryPrevious;
                            veryPrevious = reserve;
                        }
                    }
                }
                recollection.add(veryPrevious);
                veryPrevious = previous;
                previous = current;
            }
            if(veryPrevious.isBeaten()){
                veryPrevious.heal();
                Shorty reserve = veryPrevious;
                veryPrevious = previous;
                previous = reserve;
            } else if(!previous.isBeaten())previous.beat(veryPrevious);
                else previous.heal();
            recollection.add(veryPrevious);
            recollection.add(previous);
            collection = recollection;
            recollection.clear();
        }
        commands.getMaxShorty().laugh(human);
    }
}
//++