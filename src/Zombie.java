import java.io.FileNotFoundException;

public class Zombie {
	private EZImage img;			// Member variable to store bug picture
	private int x, y, startx, starty;	// Member variables to store x, y, startx, starty
	private int destx, desty;			// Member variables to store destination values
	private long starttime;				// Member variable to store the start time
	private long duration;				// Member variable to store the duration
	private boolean interpolation;		// Member variable that is set to true if it is in the interpolation state

	// ********* ROTATE
	private float currRot= 0;
	private float destRot = 0;
	private float startRot = 0;
	private boolean interpolationRot;

	private boolean hasCmd;
	private Cmd cmds;
	

	// Constructor for Zombie takes 3 parameters
	public Zombie(String filename,int posx, int posy){

		// Set the current position of the bug
		x=posx;y=posy;

		// Create the image of the bug
		img = EZ.addImage(filename, posx, posy);
		img.scaleTo(0.5);
		// Move it to the starting position. This is actually redundant.
		img.translateTo(x,y);

		// Set interpolation mode to false initially
		interpolation = false;
		interpolationRot = false;
		
		//(1)initialize hasCmd, and Cmd object
		hasCmd = false;
		cmds = new Cmd();
		
	}
	public void readCmd(String fname) throws FileNotFoundException{
		//(2) Read a file using the cmds variable
		
		hasCmd = cmds.cmdRead(fname);
		
		
	}
	public void setCmd(){
		//(3) Go through cmds variable and set next command
		if(hasCmd()){
			
		String action = cmds.getAction();
		String parameters = cmds.getParam();
		
		String []params = parameters.split(" ");
		int x, y, ang, dur;
		
		switch(action){
		
		case "PLAY" :
			EZSound sound = EZ.addSound(params[0]);
			sound.play();
			break;
		case "MOVETO":
			x = Integer.parseInt(params[0]);
			y = Integer.parseInt(params[1]);
			dur = Integer.parseInt(params[2]);
			setDestination(x, y, dur);
			break;
		case "TURNTO":
			ang = Integer.parseInt(params[0]);
			dur = Integer.parseInt(params[1]);
			setRot(ang, dur);
			break;
		default:	
			
			}
		hasCmd=cmds.advance();
		}
		
		
	}


	// Set the destination by giving it 3 variables
	// Dur means duration and is measured in seconds
	public void setDestination(int posx, int posy, long dur){

		// Set destination position and duration
		// Convert seconds to miliseconds
		destx = posx; desty = posy; duration = dur*1000;

		// Get the starting time (i.e. time according to your computer)
		starttime = System.currentTimeMillis();

		// Set the starting position of your bug
		startx=x; starty=y;

		// Set interpolation mode to true
		interpolation = true;
		
	}

	// ********* Set our Rotation
	public void setRot(float s, float dur){
		destRot = s;
		starttime = System.currentTimeMillis();
		duration = (long) (dur*1000);
		startRot = currRot;
		interpolationRot = true;
	}

	// If youʻre in an interpolation state or have commands to process then return true, else false.
	public boolean moving() {
		//(4)return whether object is in process of moving or there are commands in the queue
		return (interpolation || interpolationRot || hasCmd);
	}

	public boolean hasCmd(){
		return hasCmd;
	}
	// This moves the bug based on the current time and elapsed time according to the interpolation equation
	public void go(){

		// If interpolation mode is true then do interpolation
		if (interpolation == true) {

			// Normalize the time (i.e. make it a number from 0 to 1)
			float normTime = (float) (System.currentTimeMillis() - starttime)/ (float) duration;

			// Calculate the interpolated position of the bug
			x = (int) (startx + ((float) (destx - startx) *  normTime));
			y = (int) (starty + ((float) (desty - starty) *  normTime));

			// If the difference between current time and start time has exceeded the duration
			// then the animation/interpolation is over.
			if ((System.currentTimeMillis() - starttime) >= duration) {

				// Set interpolation to false
				interpolation = false;

				// Move the bug all the way to the destination
				x = destx; y = desty;
			}
			// Donʻt forget to move the image itself.
			img.translateTo(x,y);	
		}else if (interpolationRot == true){
			// Normalize the time (i.e. make it a number from 0 to 1)
			float normTime = (float) (System.currentTimeMillis() - starttime)/ (float) duration;

			// Calculate the interpolated position of the bug
			currRot =  (startRot + ((float) (destRot - startRot) *  normTime));


			// If the difference between current time and start time has exceeded the duration
			// then the animation/interpolation is over.
			if ((System.currentTimeMillis() - starttime) >= duration) {

				// Set interpolation to false
				interpolationRot = false;

				// Scale the bug all the way to the destination
				currRot = destRot;
			}
			// Donʻt forget to move the image itself.
			img.rotateTo(currRot);	
		}else if (hasCmd){
			//(5) if there is a command left set it
			setCmd();
		}

	}
}
