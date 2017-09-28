
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public final class EchoServer {

	public static void main(String[] args) throws Exception {
		ServerSocket serverSocket = new ServerSocket(22222);
		Runnable read = () -> {
			while (true) {
				try (Socket socket = serverSocket.accept()) {

					InputStream is = socket.getInputStream();
					InputStreamReader isr = new InputStreamReader(is, "UTF-8");
					BufferedReader br = new BufferedReader(isr);

					String address = socket.getInetAddress().getHostAddress();
					System.out.printf("Client connected: %s%n", address);
					OutputStream os = socket.getOutputStream();
					PrintStream out = new PrintStream(os, true, "UTF-8");
					out.printf("Hi %s, thanks for connecting!%n", address);
					try {
						while (true) {
							out.printf("Server> %s%n", br.readLine());
						}
					} catch (IOException e) {
						System.out.printf("Client disconnected: %s%n", address);
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};

		Thread readThread = new Thread(read);
		Thread read2Thread = new Thread(read);
		readThread.start();
		read2Thread.start();

	}
}
