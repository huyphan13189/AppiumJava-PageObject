package utilities;

import java.io.IOException;


public class CommandLine {
	public static void runACommandLine(String cmd){
		ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c",cmd);
		builder.redirectErrorStream(true);
		try {
			builder.start();
			System.out.println("Run command line: " + cmd);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
