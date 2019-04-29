package Client;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

class Pitcher {
    private Socket socket;

    Pitcher(Socket socket) {
        this.socket = socket;
    }



    void pitch(InputStream command) throws IOException {
        socket.getOutputStream().write(command.read());
    }

    InputStream complexPitch(InputStream command) throws IOException{
        socket.getOutputStream().write(command.read());
        return socket.getInputStream();
    }
}
//+