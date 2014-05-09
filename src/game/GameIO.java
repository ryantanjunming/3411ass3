package game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


public class GameIO {
	
	public Game g;
	public Socket socket;
	public ObjectOutput out;
 	public ObjectInputStream in;
	public Map<String, Method> methodMap;
	
	public GameIO(Game g, String[] args){
		
		this.g = g;
		
		try{
			methodMap =  new HashMap<String, Method>();
			methodMap.put("init", this.getClass().getMethod("game_init"));
			methodMap.put("start", this.getClass().getMethod("game_start"));
			methodMap.put("second_move", this.getClass().getMethod("game_second_move"));
			methodMap.put("third_move", this.getClass().getMethod("game_third_move"));
			methodMap.put("last_move", this.getClass().getMethod("game_last_move"));
			methodMap.put("win", this.getClass().getMethod("game_win"));
			methodMap.put("loss", this.getClass().getMethod("game_loss"));
			methodMap.put("draw", this.getClass().getMethod("game_draw"));
			methodMap.put("end", this.getClass().getMethod("game_end"));
			
		}catch(NoSuchMethodException e){
			e.printStackTrace();
		}
		
		try{
			
			if(args[0].equals("-p") && args[1] != null){
				int portnum = Integer.parseInt(args[1]);
				this.socket = new Socket(InetAddress.getLocalHost().getHostName(), portnum);
				this.out = new ObjectOutputStream(this.socket.getOutputStream());
				this.out.flush();
				this.in = new ObjectInputStream(this.socket.getInputStream());
				
			}else{
				System.out.println("ERROR: Enter -p <port number>");
			}
		}catch(ConnectException e){
			System.out.println("\nWARNING: Connection not established, "
					+ "Run Server with same port.\n");
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		
		}finally{
		
			try{
				this.in.close();
				this.out.close();
				this.socket.close();
			}
			catch(IOException ioException){
				ioException.printStackTrace();
			}
		}
		
	}
	
	public void io_run() throws IOException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, ClassNotFoundException{
		while(!g.isFinished){
			String readIn = (String) in.readObject();
			String call = readIn.substring(0, readIn.indexOf('('));
			String servArgs[] = readIn.substring(readIn.indexOf('('),readIn.indexOf(')')).split(",");
			
			if(servArgs.length==1){
				methodMap.get(call).invoke(this, servArgs[1]);
			}else if(servArgs.length==2){
				methodMap.get(call).invoke(this, servArgs[1], servArgs[2]);
			}else{
				methodMap.get(call).invoke(this);
			}
		}
	}
	
	
	public void game_init(){
		g.init();
	}
	
	public void game_start(Character representation){
		
	}
	
	public void game_second_move(){
		
	}
	
	public void game_third_move(){
		
	}
	
	public void game_last_move(){
		
	}
	
	public void game_win(){
		
	}
	
	public void game_loss(){
		
	}
	
	public void game_draw(){
		
	}
	
	public void game_end(){
		this.g.setToFinished();
	}
	
	public void makeMove(int move) {
		try {
			this.out.writeObject(move);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
