import java.io.*;
import java.net.*;
import java.util.*;

public class OSServer {
    private static Set<OSClientHandler> clientHandlers = new HashSet<>();

    public static void main(String[] args) throws IOException{
        ServerSocket s = new ServerSocket(1235);
        System.out.println("Connected to port: " + s.getLocalPort());
        while (true) {
            Socket clSocket = s.accept();
            System.out.println("Client connected with id:"+clSocket.getInetAddress());

            OSClientHandler cl = new OSClientHandler(clSocket);

            clientHandlers.add(cl);
            new Thread(cl).start();
        }
    }

    public static void showMessage(String m, OSClientHandler sender){
        for(OSClientHandler client : clientHandlers){
            if(client!=sender){
                client.sendMessages(m);
            }
        }
    }

    public static void removeCl(OSClientHandler cl) {
        clientHandlers.remove(cl);
    }
}

class OSClientHandler implements Runnable{
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public OSClientHandler(Socket socket){
        this.socket = socket;
    }
    
    @Override
    public void run(){
        try {
            out = new PrintWriter(socket.getOutputStream(),true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String m;
            while((m = in.readLine())!=null){
                System.out.println("Received: " + m);
                OSServer.showMessage(m, this);
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
            OSServer.removeCl(this);
        }

    }

    public void sendMessages(String m){
        out.println(m);
    }
}
