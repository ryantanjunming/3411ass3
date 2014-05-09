package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;




public class GameIO {
	
	public Game g;
	public Socket socket;
	public PrintWriter out;
	public BufferedReader in;
	public Map<String, Method> methodMap;
	
	
	public GameIO(Game g, String[] args){
		
		this.g = g;
		
		try{
			methodMap =  new HashMap<String, Method>();
			methodMap.put("init", Game.class.getMethod("init"));
			methodMap.put("init", Game.class.getMethod("init"));
			
			
		}catch(NoSuchMethodException e){
			e.printStackTrace();
		}
		
		try{
			if(args[0] == "-p" && args[1] != null){
				int portnum = Integer.parseInt(args[1]);
				this.socket = new Socket(InetAddress.getLocalHost().getHostName(), portnum);
			    this.out = new PrintWriter(socket.getOutputStream(), true);
			    this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));    		
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void game_init(){
		
	}
	
	public void game_start(){
		
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
		
	}
}
