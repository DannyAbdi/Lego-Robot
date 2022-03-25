import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Bluetooth {

	private BufferedInputStream in = null;
	private DataOutputStream out = null;
	private static int MAX_READ = 30;
	private byte[] buffer = new byte[MAX_READ];
	
	public Bluetooth(BufferedInputStream in, DataOutputStream out) {
		this.in = in;
		this.out = out;
	}

	public String getMessage() {
		String s = "";
		try {
			if (in.available() > 0) {
				int read;
				try {
					read = in.read(buffer, 0, MAX_READ);
					for (int index= 0 ; index < read ; index++) {		
						s+= (char) buffer[index];
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return s;
	}
	
	public void sendMessage(String message) {
        if (message.length() > 0) {
        	byte[] send = message.getBytes();
        	try {
        		out.write(send);
        	} catch (IOException e) {
        		e.printStackTrace();
        	}
        }
    }
}
