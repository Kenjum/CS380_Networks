
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public final class ChatClient {

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		Socket socket = new Socket("18.221.102.182", 38001);

		InputStream is = socket.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, "UTF-8");
		BufferedReader br = new BufferedReader(isr);

		Runnable read = () -> {
			String line = "";

			try {
				while (true) {
					line = br.readLine();
					if (line != null) {
						System.out.println(line);
					}
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		};

		String address = socket.getInetAddress().getHostAddress();
		OutputStream os = socket.getOutputStream();
		PrintStream out = new PrintStream(os, true, "UTF-8");

		Runnable write = () -> {
			while (true) {
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				String userInput = sc.nextLine();
				if(userInput.equals("exit")){
					System.exit(0);
				}
				out.printf("%s%n", userInput);
				
			}

		};

		Thread readThread = new Thread(read);
		Thread writeThread = new Thread(write);
		readThread.start();
		writeThread.start();
	}
}
