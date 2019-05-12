import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class Main {
    public static void main(String[] args){
        ServerSocket serverSocket = null;
        Collection collection = new Collection();

        try {
            serverSocket = new ServerSocket(2038, 0, InetAddress.getByName("localhost"));
            System.out.println("Сервер запущен!");
        } catch (IOException e) {
            System.err.println("Не удалось запустить сервер :(");
        }

        while (true) {
            try {
                new Catcher(serverSocket.accept(), collection);
            } catch (IOException e){
                try{
                    serverSocket.close();
                }catch (IOException ignored){}
            }
        }
    }
}
//++