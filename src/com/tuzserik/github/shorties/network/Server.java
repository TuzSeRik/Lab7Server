package com.tuzserik.github.shorties.network;

import com.tuzserik.github.shorties.background.Collection;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;

public class Server {
    public static void main(String[] args){
        ServerSocketChannel serverSocket = null;
        ObjectOutputStream output = null;
        Collection collection = new Collection(output);

        try {
            serverSocket = ServerSocketChannel.open();
            serverSocket.bind(new InetSocketAddress("localhost", 2038));
            serverSocket.configureBlocking(true);
            System.out.println("Сервер запущен!");
        } catch (IOException e) {
            System.err.println("Не удалось запустить сервер :(");
            System.exit(-1);
        }

        while (true) {
            try {
                new Catcher(serverSocket.accept(), collection, output);
            } catch (IOException e){
                e.printStackTrace();
                System.exit(-2);
            }
        }
    }
}
//++