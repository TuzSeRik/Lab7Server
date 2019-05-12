import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

public class Catcher extends Thread {
    private SocketChannel channel;
    private FileChannel fchannel;
    private ByteBuffer buffer;
    private Collection collection;

    Catcher(Socket socket, Collection collection) {
        try {
            this.collection = collection;
            channel = channel.bind(socket.getLocalSocketAddress());
            try
            {
                fchannel = FileChannel.open(Paths.get("collectionStorage.csv"));
            }catch (Exception e){
                System.err.println("Файл хранилища отсутствует!");
            }
            setDaemon(true);
            setPriority(NORM_PRIORITY);
            start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            channel.read(buffer);
            String input = StandardCharsets.UTF_8.decode(buffer).toString();

            if (input.equals("show")) {
                channel.write(StandardCharsets.UTF_8.encode(collection.commands.show()));
            }

            if (input.equals("info")) {
                channel.write(StandardCharsets.UTF_8.encode(collection.commands.info()));
            }

            if (input.equals("load")) {
                collection.commands.load();
                channel.write(StandardCharsets.UTF_8.encode("Коллекция по-умолчанию загружена!"));
            }

            if (input.equals("initialize")) {
                collection.commands.initialise("");
                channel.write(StandardCharsets.UTF_8.encode("Коллекция инициализированна!"));
            }

            if (input.equals("start")) {
                Simulation simulation = new Simulation(collection, channel);
            }

            if (input.equals("remove_last")) {
                collection.commands.removeLast();
                channel.write(StandardCharsets.UTF_8.encode("Последний элемент удален!"));
            }

            if (input.contains("add ")) {
                String[] strings = input.split(" ");
                collection.commands.add(strings[1]);
                channel.write(StandardCharsets.UTF_8.encode("Элемент успешно записан!"));
            }

            if (input.contains("add_if_max ")) {
                String[] strings = input.split(" ");
                collection.commands.addIfMax(strings[1]);
                channel.write(StandardCharsets.UTF_8.encode("Элемент успешно записан(или не записан)!"));
            }

            if (input.contains("remove ")) {
                String[] strings = input.split(" ");
                collection.commands.remove(strings[1]);
                channel.write(StandardCharsets.UTF_8.encode("Элемент успешно удален!"));
            }

            if (input.contains("import")) {
                channel.read(buffer);
                String[] strings = StandardCharsets.UTF_8.decode(buffer).toString().split(" ");
                fchannel.write(StandardCharsets.UTF_8.encode(strings[1]));
                channel.write(StandardCharsets.UTF_8.encode("Коллекция успешно импортирована!"));
            }

            if (input.equals("overwrite")) {
                collection.commands.save();
                channel.write(StandardCharsets.UTF_8.encode("Коллекция успешно сохранена как стандартная!"));
            }

            if (input.equals("save")) {
                fchannel.read(buffer);
                channel.write(ByteBuffer.allocate(64*1024).put(buffer).put(StandardCharsets.UTF_8.encode("Коллекция успешно сохранена на клиенте!")));
            }

            if (input.equals("quitAll")) {
                channel.write(StandardCharsets.UTF_8.encode("Сервер завершил работу!"));
                throw new EOFException();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
//++