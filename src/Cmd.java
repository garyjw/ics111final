import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Cmd {
	ArrayList<String> action;//stores whether TURNTO, MOVETO
	ArrayList<String> params;
	
	int nCmds;
	
	
	Cmd(){
		nCmds=0;
		action=new ArrayList<String>();
		params=new ArrayList<String>();
	}
	
	boolean cmdRead(String fname) throws FileNotFoundException{
		Scanner fs = new Scanner(new FileReader(fname));
		
		while(fs.hasNext()){
			String act=fs.next();
			if(act.equals("MOVETO") || act.equals( "TURNTO") || act.equals("PLAY")){
				nCmds+=1;
				action.add(act);
				params.add(fs.nextLine().trim());
			}
			
		}
		
		fs.close();
		if( nCmds > 0){
			return true;
		}else{
			return false;
		}
	}
	
	String getAction(){
		if(nCmds>0){
			return action.get(0);
		}else{
			return "";
		}
	}
	String getParam(){
		if(nCmds>0){
			return params.get(0);
		}else{
			return "";
		}
	}
	boolean advance(){
		if(nCmds >0){
			nCmds--;
			action.remove(0);
			params.remove(0);
			
		}
		if(nCmds>0){
			return true;
		}else{
			return false;
		}
		
	} 
}
