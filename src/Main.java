import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.Random;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		EZ.initialize(700, 400); //Initializes the screen
		Random rand = new Random(); //Creates a random generator
		
		
		//Creates a background and pokemon
		EZ.addImage("battleground.png", 350, 200);
		EZ.addImage("gengar.png", 200, 300);
		EZ.addImage("nidorino.png", 500, 200);
		
		//Creates 4 text boxes, 1 for instructions, 2 & 3 for description, cbtLog for combat text
		EZText texbox1 = EZ.addText(150, 100, "Click on a move to Attack!", Color.WHITE, 16);
		EZText texbox2 = EZ.addText(150, 300, "Gengar Lv.1", Color.WHITE, 16);
		EZText textbox3 = EZ.addText(500, 150, "Training Nidorino Lv.Over 9000", Color.WHITE, 16);
		EZText cbtLog = EZ.addText(150, 375, "", Color.WHITE, 12);
		
		//Creates the clickable objects using the attack class
		Attacks fire = new Attacks("flamethrower.png", 450, 300,"burning.wav");
		Attacks ghost = new Attacks("shadowball.png", 577, 300, "creepy.wav");
		Attacks electric = new Attacks("thunderbolt.png", 450, 358, "thunder.wav");
		Attacks rock = new Attacks("rockthrow.png", 577, 358, "smash.wav");
		
	
		//Variables to store the x and y coords of the mouse
		 int clickX = 0;
		 int clickY = 0;
		
		while(true){
			clickX = EZInteraction.getXMouse();
			clickY = EZInteraction.getYMouse();
			
			
			if (EZInteraction.wasMouseLeftButtonReleased()){
				//Creates animations of fire, rock, shadow, and electric and moves it off screen then gives it a script to read
				Zombie fire1 = new Zombie("fire.png", -100, -100);
				fire1.readCmd("fire_script");
				
				Zombie rock1 = new Zombie("rock.png", -100, -100);
				rock1.readCmd("rock_script");
				
				Zombie shadow1 = new Zombie("shadow.png", -100, -100);
				shadow1.readCmd("ghost_script");
				
				Zombie electric1 = new Zombie("lightning.png", -100, -100);
				electric1.readCmd("lightning_script");
				
				//Checks to see if the clickables have been clicked, If clicked the animaton will play
				fire.checkClick(clickX, clickY, fire, fire1);
				rock.checkClick(clickX, clickY, rock, rock1);
				ghost.checkClick(clickX, clickY, ghost, shadow1);
				electric.checkClick(clickX, clickY, electric, electric1);
				
				//Cycles between 4 messages in the combat log after each attack
				if(rand.nextInt(4)==0){
					cbtLog.setMsg("The training Nidorino took it like a champ!");
					}
					if(rand.nextInt(4)==1){
						cbtLog.setMsg("Nidorino looks hurt!");
					}
					if(rand.nextInt(4)==2){
						cbtLog.setMsg("Nidorino thinks you're a wuss!");
					}
					if(rand.nextInt(4)==3){
						cbtLog.setMsg("Weak the blood!");
					}
				
			}
			//Refreshes screen
			EZ.refreshScreen();
		}
	}

}
