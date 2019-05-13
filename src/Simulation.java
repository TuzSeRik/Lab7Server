import Background.Furnace;
import Background.Human;
import Background.Shorty;
import java.io.IOException;
import java.io.ObjectOutputStream;

class Simulation {
    private Shorty x = null;
    private Shorty y = null;
    private Shorty z = null;
    private Collection collection;
    private Furnace furnace;
    private Human human;

    Simulation(Collection collection) {
        furnace = new Furnace("Картошка", 100);
        human = new Human("Human", 100, 100, "Rubaha", 100);
    }



    void simulate(ObjectOutputStream os) throws IOException {
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
    }
}
//++