package com.tuzserik.github.shorties.background;

import com.tuzserik.github.shorties.serializing.CSV;
import com.tuzserik.github.shorties.serializing.JSON;
import net.sf.jsefa.DeserializationException;
import java.io.*;
import java.time.LocalDateTime;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.Iterator;
import java.util.Comparator;
import java.util.Scanner;


@SuppressWarnings("OptionalGetWithoutIsPresent")
public class Collection {
    private static PriorityBlockingQueue<Shorty> collection = new PriorityBlockingQueue<>();
    private static PriorityBlockingQueue<Shorty> recollection = new PriorityBlockingQueue<>();
    public Commands commands = new Commands();
    private Furnace furnace = new Furnace("Картошка", 100);
    private Human human = new Human("Human");
    private ObjectOutputStream output;

    public Collection(ObjectOutputStream output){
        this.output = output;
        commands.initialise("");
    }

    private String iDate(){
        final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        LocalDateTime date = LocalDateTime.now();
        for (Shorty x : collection) {
            if (x.getDate().isBefore(date)) {
                date = x.getDate();
            }
        }
        return sdf.format(date);
    }




    public class Commands {
        private Comparator<Shorty> food = Comparator.comparingInt(Shorty::getFoodCount);
        Shorty getMaxShorty(){
            return collection.stream().max(food).get();
        }

        public void initialise(String key){
            try {
                Scanner reader = new Scanner(new File("com/tuzserik/github/shorties/network/collectionStorage.csv"));
                while (reader.hasNext()){
                    LocalDateTime date = LocalDateTime.now();
                    String current = reader.nextLine();
                    if(!key.equals("-l")){
                         CSV.fromCSV(current).setDate(date);
                    }
                    collection.add(CSV.fromCSV(current));
                }
                reader.close();
            }catch (FileNotFoundException e){
                System.err.println("Файл не найден! Проверьте его наличие в пути, указанном в INPUTPATH");
            }
            catch (DeserializationException e){
                System.err.println("Ошибка представления данных в файле!");
            }
        }

        public void load(){
            initialise("-l");
        }

        public void add(String jsonObject){
            try{
                if(JSON.fromJSON(jsonObject).getDate()==null){
                    throw new NullPointerException();
                }
                collection.add(JSON.fromJSON(jsonObject));
            }
            catch (NullPointerException e){
                System.err.println("Неправильно задан объект!");
            }
        }

        public void remove(String jsonObject){
            collection.remove(JSON.fromJSON(jsonObject));
        }

        public void removeLast() {
            int i = collection.size();
            try {
                if (i == 0) {
                    throw new Exception();
                } else {
                    for (Iterator<Shorty> iterator = collection.iterator(); iterator.hasNext(); ) {
                        iterator.next();
                        if (i == 1) {
                            iterator.remove();
                        }
                        i--;
                    }
                }
            } catch (Exception e) {
                System.err.println("Коллекция пуста!");
            }
        }

        public String show(){
            return collection.toString();
        }

        public String info(){
            return ("Shorty"+"/n"+collection.size()+"/n"+iDate());
        }

        public void save(){
            try{
                BufferedOutputStream bfos=new BufferedOutputStream(new FileOutputStream("com/tuzserik/github/shorties/network/collectionStorage.csv", true));
                for (Shorty x : collection) {
                    byte[] b = CSV.toCSV(x).getBytes();
                    bfos.write(b);
                }
                bfos.close();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }

        public void simulate()throws IOException{
            new Simulation(commands, collection, recollection, furnace, human, output);
        }
    }
}
//+