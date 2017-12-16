import java.util.ArrayList;

public class Attacks {

	EZImage image; //Member function that stores an EZImage
	Attacks atk; //Member function that stores an Attack
	EZSound sound; //Member function that stores a sound
	Zombie anim; //Member function that store the animation
	
	//Constructor that makes an EZImage and EZSound
	Attacks(String filename, int x, int y, String soundname){
		image = EZ.addImage(filename, x, y);
		sound = EZ.addSound(soundname);
	}
	
	
	boolean checkClick(int x, int y,Attacks filename, Zombie animation){
		atk = filename;
		anim = animation;
	
		//check if coordinates are in image, if so play animation and return true
		if(image.isPointInElement(x, y)){
			while(anim.moving()){
			anim.go();
			EZ.refreshScreen();
			}
			return true;
		
		}
			// otherwise return false
		else{
			return false;
		}
	}

	
}
