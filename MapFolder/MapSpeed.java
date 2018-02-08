
public class MapSpeed {
	double speed;
	String code;
	boolean skateboard;
	int time;
	String walkStatus;
	
	public MapSpeed(String code, boolean skateboard) {
		this.code = code;
		this.skateboard = skateboard;
		if (skateboard == false) {
			code = code.toLowerCase();
		}
		
		speed = 272.0/60;
		switch (code.charAt(1)) {
		case 'u' :
			speed *= 0.9;
			walkStatus = "Walk up";
			break;
		case 'd' :
			speed *= 1.1;
			walkStatus = "Walk down";
			break;
		case 'U' :
			speed *= 1.1;
			walkStatus = "Coast up";
			break;
		case 'F' :
			speed *= 2.0;
			walkStatus = "Glide";
			break;
		case 'D' :
			speed *= 5.0;
			walkStatus = "Coast down";
			break;
		case 's' :
			speed *= 0.5;
			walkStatus = "Go up";
			break;
		case 't' :
			speed *= 0.9;
			walkStatus = "Go down";
			break;
		case 'x' :
			speed *= 2.0;
			walkStatus = "Glide";
			break;
		default:
			walkStatus = "Walk";	
			break;
			
			
		}
		if (skateboard == false) {
			walkStatus = "Walk ";
		}
	}
		
	public String toTime(int length) {
		time = (int)(length/speed);
		if (time < 60) return ""+time+" seconds";
		else {
			double min = time / 60 + (int)(Math.round((time % 60 / 6)))/10.0;
//			double second = time % 60 / 6;
			return ""+min+" minutes";
		}
			
	}
	
	public String canSkateboard() {
		if (skateboard && code.charAt(1) > 96 && code.charAt(1) != 'x') {
			return "no skateboards allowed, ";
		}
		return "";
	}
	
	public int getTime(int length) {
		return (int)(length/speed);
	}
}
