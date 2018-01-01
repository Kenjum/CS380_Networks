import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public final class WebServer {

	public static void main(String args[]) {
		try (ServerSocket ss = new ServerSocket(8080)) {
			while (true) {
				try (Socket socket = ss.accept();) {

					BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);

					String line = br.readLine();
					String websiteName = "www" + line.split(" ")[1];
					System.out.println(websiteName);
					File file = new File(websiteName);
					File file2 = new File("www/error.html");
					Scanner sc;
					try {
						if (file.exists()) {
							System.out.println("if happened");
							sc = new Scanner(file);
							pw.println("HTTP/1.1 200 OK");
							pw.println("Content-type: text/html");
							pw.println("Content-length: 124\n");
							while (sc.hasNextLine()) {
								pw.println(sc.next());
								pw.flush();
							}
							sc.close();
						} else {
							System.out.println("else happened");
							sc = new Scanner(file2);
							pw.println("HTTP/1.1 404 Not Found");
							pw.println("Content-type: text/html");
							pw.println("Content-length: 126\n");
							while (sc.hasNextLine()) {
								pw.println(sc.next());
								pw.flush();
							}
							sc.close();
						}
					} catch (FileNotFoundException e) {
						System.out.println("Unauthorized Access");
					}

					pw.close();
					br.close();
					socket.close();
				}
			}

		} catch (Exception e) {
			System.err.println(e + "\n");
		}
	}

}
