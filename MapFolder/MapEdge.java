
public class MapEdge {
	int num;
	int length;
	int angle;
	String name;
	String code;
	String direction;
	public MapEdge(int num, int length, int angle, String direction, String code, String name) {
		this.num = num;
		this.length = length;
		this.angle = angle;
		this.name = name;
		this.code = code;
		this.direction = direction;
	}
	
	public boolean canSkateboard() {
		return code.charAt(1) < 96;
	}
	
	public boolean setX(String code) {
		if (code.charAt(1) == 'x') {
			code = "("+code+")";
			return true;
		}
		return false;
	}
	
}
