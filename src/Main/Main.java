package Main;

import IO.Collection;
import java.util.Scanner;

/*
import IO.JSON;
import java.util.Comparator;
import java.util.Date;
*/

public class Main {

        public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        Shorty x = null;
        Shorty y = null;
        Shorty z = null;
        Collection collection = new Collection();
        Furnace furnace = new Furnace("Картошка", 100);
        Human human = new Human("Human", 100, 100, "Rubaha", 100);

                /*
                {
                Shorty s1 = new Shorty("Striga1", 100, 100, "Rubaha", 100);
                s1.setDate(new Date());
                Shorty s2 = new Shorty("Striga2", 50, 50, "Rubaha", 50);
                s2.setDate(new Date());
                Shorty s3 = new Shorty("Striga3", 25, 25, "Rubaha", 25);
                s3.setDate(new Date());
                collection.commands.add(JSON.toJSON(s1));
                collection.commands.add(JSON.toJSON(s2));
                collection.commands.add(JSON.toJSON(s3));
                }
                */

        while (true){
            System.out.print("Enter something : ");
            String input = scanner.nextLine();

            if("q".equals(input)){
                collection.commands.save();
            break;
            }

            if("load".equals(input)){
                collection.commands.load();
            }

            if("show".equals(input)){
                collection.commands.show();
            }

            if("info".equals(input)){
                collection.commands.info();
            }

            if("remove_last".equals(input)){
                collection.commands.removeLast();
            }

            if(input.contains("add_if_max")){
                collection.commands.addIfMax(input.split(" ")[1]);
            }

            if(input.contains("add ")){
                collection.commands.add(input.split("\\{")[1].replace("\\}",""));
            }

            if(input.contains("remove ")){
                collection.commands.remove(input.split("\\{")[1].replace("\\}",""));
            }
        }

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
        } catch (Exception e){
            try{
            collection.getBest().Laugh(human);
        }catch (NullPointerException ex){
                System.err.println("Да не работает же!");
            }
        }
    }
}
//+