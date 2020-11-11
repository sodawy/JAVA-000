import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class HelloNIO {
    public int a = 3;

    public static void main(String[] args) throws IOException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(40);
        ServerSocket serverSocket = new ServerSocket(8081);
        while (true) {
            Socket socket = serverSocket.accept();
            service(socket);
        }
    }

    static private void service(Socket socket) {
        try {
            Thread.sleep(5);
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
            String respText = "Hello NIO";
            pw.println("HTTP/1.1 200 OK");
            pw.println("Content-Type: text/html;charset=utf-8");
            pw.println("Content-Length: " + respText.getBytes().length);
            pw.println();
            pw.write(respText);

            pw.close();
            socket.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}