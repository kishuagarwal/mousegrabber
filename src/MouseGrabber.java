import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;


public class MouseGrabber {

	
	private static ServerSocket socket;
	private static Socket clientSocket;
	private static Robot robot;
	private static int currentX,currentY;
	private static int height,width;
	
	/**
	 * @param args
	 * @throws AWTException 
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws AWTException, InterruptedException, IOException {
		// TODO Auto-generated method stub
		
		openServer();
		process();
		closeServer();
		}

	private static void process() throws IOException, AWTException {
		// TODO Auto-generated method stub
		makeRobot();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		String request;
		request = reader.readLine();
		initializeXY(request); 
		System.out.println(width +" "+ height);
		while(true)
		{
			//System.out.println("Reading");
			request = reader.readLine();
			//System.out.println(request);
			if(request.equals("q"))
				return;
			if(request.length() == 0)
				continue;
			String cordinates[] = request.split(" ");
			//System.out.println(Arrays.toString(cordinates));
			int x = (int) (Double.parseDouble(cordinates[0])*width);
			int y = (int) (Double.parseDouble(cordinates[1])*height);
			
			processMouseMovement(x, y);
			
		}
	}
	
	private static void initializeXY(String request) {
		// TODO Auto-generated method stub
		width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		String r[] = request.split(" ");
		currentX = (int)((Double.parseDouble(r[0]))*width);
		currentY = (int)(Double.parseDouble(r[1])*height);
	}

	public static void openServer() throws IOException{
		//InetSocketAddress address = new Inet
		socket = new ServerSocket(10003);
		clientSocket = socket.accept();
		System.out.println("Client Connected");
	}
	
	public static void closeServer() throws IOException
	{
		socket.close();
		clientSocket.close();
	}
	
	private static void makeRobot() throws AWTException{
		robot = new Robot();

		
	}
	
	private static void processMouseMovement(int x,int y)
	{
		
		robot.mouseMove(x, y);
	}
	
	
}
