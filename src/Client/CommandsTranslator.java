package Client;

import java.io.*;
import java.net.Socket;

class CommandsTranslator {
    private Pitcher pitcher;
    private BufferedInputStream fromFile;
    private BufferedOutputStream toFile;

    CommandsTranslator(Socket socket){
        pitcher = new Pitcher(socket);
        try
        {
            fromFile = new BufferedInputStream(new FileInputStream("collectionStorage.csv"), 1024);
            toFile = new BufferedOutputStream(new FileOutputStream("collectionStorage.csv"),1024);
        } catch (FileNotFoundException e){System.err.println("Фаил не был найден!");}
    }



    void simpleCommand(String s) throws IOException {
        InputStream command = new ByteArrayInputStream(s.getBytes());
        pitcher.pitch(command);
    }

    void complexCommand(String s) throws IOException {
        InputStream command = new ByteArrayInputStream(s.getBytes());
        toFile.write(pitcher.complexPitch(command).read());
    }

    void importCommand(String s) throws IOException {
        InputStream command = new SequenceInputStream(new ByteArrayInputStream(s.getBytes()),fromFile);
        pitcher.pitch(command);
    }
}
//+