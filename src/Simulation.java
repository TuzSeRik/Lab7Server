import Background.Furnace;
import Background.Human;
import Background.Shorty;

import java.nio.channels.SocketChannel;

public class Simulation {
    private Shorty x = null;
    private Shorty y = null;
    private Shorty z = null;

    Simulation(Collection collection, SocketChannel channel){
        Furnace furnace = new Furnace("Картошка", 100);
        Human human = new Human("Human", 100, 100, "Rubaha", 100);
        while (furnace.getQuantity() > 0){
            try {
                while (x == null) {
                    x = collection.getRandomMember();
                }
                while (y == null) {
                    y = collection.getRandomMember();
                }
                x.Beat(y);
            } catch (NullPointerException e){
                System.err.println("Коллекция пуста, драться некому!");
                break;
            }
            if(x.getIsWinner()){
                x.takeFood(furnace);
                for (int i = 0; i < 2; i++){
                    while (z==null) {
                        z = collection.getRandomMember();
                    }
                    z.Buzz(x);
                }
            }
            Collection.Rest();
        }
        try {
            human.takeFood(furnace);
        } catch (Exception e) {
            try {
                collection.getBest().Laugh(human);
            } catch (NullPointerException ex) {
                System.err.println("Да не работает же!");
            }
        }
    }
}
//++