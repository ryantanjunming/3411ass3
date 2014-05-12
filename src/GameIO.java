

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


public class GameIO{
	
	public Game g;
	public Socket socket;
	public PrintWriter out;
 	public BufferedReader in;
	public Map<String, Method> methodMap;
	
	public GameIO(Game g, String[] args){
		this.g = g;
		
//		try{
//			methodMap =  new HashMap<String, Method>();
//			methodMap.put("init", this.getClass().getMethod("game_init"));
//			methodMap.put("start", this.getClass().getMethod("game_start", String.class));
//			methodMap.put("second_move", this.getClass().getMethod("game_second_move", String.class,String.class));			
//			methodMap.put("third_move", this.getClass().getMethod("game_third_move",String.class,String.class,String.class));
//			methodMap.put("last_move", this.getClass().getMethod("game_last_move", String.class));
//			methodMap.put("win", this.getClass().getMethod("game_win"));
//			methodMap.put("loss", this.getClass().getMethod("game_loss"));
//			methodMap.put("draw", this.getClass().getMethod("game_draw"));
//			methodMap.put("end", this.getClass().getMethod("game_end"));
//			
//		}catch(NoSuchMethodException e){
//			e.printStackTrace();
//		}
//		
		try{
			
			if(args[0].equals("-p") && args[1] != null){
				int portnum = Integer.parseInt(args[1]);
				
				 socket = new Socket(InetAddress.getLocalHost().getHostName(), portnum);
				 out = new PrintWriter(socket.getOutputStream(), true);
				 in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
			}else{
				System.out.println("ERROR: Enter -p <port number>");
			}
		}catch(ConnectException e){
			System.out.println("\nWARNING: Connection not established, "
					+ "Run Server with same port.\n");
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		
		}
	}
	
	public void run(){
		while(!g.isFinished){
			String readIn = null;
			try {
				readIn = in.readLine();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (readIn == null) continue;
			String call = null;
			String servArgs[] = null;
			if (readIn.contains("(")) {
				call = readIn.substring(0, readIn.indexOf('('));
				servArgs = readIn.substring(readIn.indexOf('(')+1,readIn.indexOf(')')).split(",");
			} else {
				call = readIn.substring(0, readIn.indexOf('.'));
			}
			if (call != null) {
				System.out.print(call);
				if (servArgs != null) {
					for (String s : servArgs) {
						System.out.print(" " + s);
					}
				}
				System.out.println();
			}
//			methodMap.put("init", this.getClass().getMethod("game_init"));
//			methodMap.put("start", this.getClass().getMethod("game_start", String.class));
//			methodMap.put("second_move", this.getClass().getMethod("game_second_move", String.class,String.class));			
//			methodMap.put("third_move", this.getClass().getMethod("game_third_move",String.class,String.class,String.class));
//			methodMap.put("last_move", this.getClass().getMethod("game_last_move", String.class));
//			methodMap.put("win", this.getClass().getMethod("game_win"));
//			methodMap.put("loss", this.getClass().getMethod("game_loss"));
//			methodMap.put("draw", this.getClass().getMethod("game_draw"));
//			methodMap.put("end", this.getClass().getMethod("game_end"));
			if (call.equalsIgnoreCase("init")) {
				game_init();
			} else if (call.equalsIgnoreCase("start")) {
				game_start();
			} else if (call.equalsIgnoreCase("second_move")) {
				assert(servArgs.length == 2);
				game_second_move(servArgs[0], servArgs[1]);
			} else if (call.equalsIgnoreCase("third_move")) {
				assert(servArgs.length == 3);
				game_third_move(servArgs[0], servArgs[1], servArgs[2]);
			} else if (call.equalsIgnoreCase("next_move")) {
				assert(servArgs.length == 1);
				game_next_move(servArgs[0]);
			} else if (call.equalsIgnoreCase("last_move")) {
				assert(servArgs.length >= 1);
				game_last_move(servArgs[0]);
			} else if (call.equalsIgnoreCase("win")) {
				game_win();
			} else if (call.equalsIgnoreCase("loss")) {
				game_loss();
			} else if (call.equalsIgnoreCase("draw")) {
				game_draw();
			} else if (call.equalsIgnoreCase("end")) {
				game_end();
			}
		}
	}
	
	
	public void game_init(){
		g.init();
	}
	

	public void game_start() {
		// do nothing
	}
	
	public void game_second_move(String a, String b){
		this.g.changeCurPiece();
		this.g.setCurBoard(Integer.parseInt(a)-1);
		this.g.addPiece(Integer.parseInt(b));
	}
	
	public void game_third_move(String a, String b, String c){
		this.g.setCurBoard(Integer.parseInt(a)-1);
		this.g.addPiece(Integer.parseInt(b));
		this.g.addPiece(Integer.parseInt(c));
	}
	
	public void game_next_move(String move) {
		this.g.addPiece(Integer.parseInt(move));
	}
	
	public void game_last_move(String move){
		this.g.addPiece(Integer.parseInt(move));
		this.g.setToFinished();
	}
	
	public void game_win(){
		this.g.setToFinished();
	}
	
	public void game_loss(){
		this.g.setToFinished();
	}
	
	public void game_draw(){
		this.g.setToFinished();
	}
	
	public void game_end(){
		this.g.setToFinished();
	}
	
	public void makeMove(int move) {
		out.println(move);
	}
}
