import java.io.*;
import java.net.*;
import java.nio.file.*;

public class Webserver {
    public static void main(String[] args) {
    
        try {
            ServerSocket ss = new ServerSocket(8080);
            while (true) {
                Thread conn = new Thread(new RequestHandler(ss.accept()));
                conn.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class RequestHandler implements Runnable {
    private Socket socket;

    public RequestHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());

            // Read the request line
            String requestLine = in.readLine();
            if (requestLine == null || !requestLine.startsWith("GET")) {
                out.print("HTTP/1.1 500 Internal Server Error\r\n\r\n");
                out.flush();
                return;
            }

            // Extract file name from request line
            // Find the indices of the spaces
            int firstSpace = requestLine.indexOf(" ");
            int secondSpace = requestLine.indexOf(" ", firstSpace + 1);

            // Extract the requested file path
            String requestedFile = requestLine.substring(firstSpace + 1, secondSpace);
            if (requestedFile.equals("/")) {
                requestedFile = "/index.html"; 
            }

            // Remove leading slash for local file path
            String filePath = "." + requestedFile;

            // Attempt to load and send the file
            try {
                byte[] fileContent = Files.readAllBytes(Paths.get(filePath));
                out.write("HTTP/1.1 200 OK\r\n");
                out.write("Content-type: text/html\r\n\r\n");
                socket.getOutputStream().write(fileContent);
                socket.getOutputStream().flush();
            } catch (NoSuchFileException e) {
                // Send 404 Not Found response
                out.print("HTTP/1.1 404 Not Found\r\n\r\n");
                out.flush(); 
            } catch (IOException e) {
                out.print("HTTP/1.1 500 Internal Server Error\r\n\r\n");
                out.flush();  
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
