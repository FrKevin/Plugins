package plugins;

public class ToCodeCesar13 implements Plugin{
	
	public static final int PAS = 13;
	
	@Override
	public String getLabel() {
		return "Code Cesar 13";
	}
	
	public char crypt(char c) {
		char begin;
		int modulo;
		if(c >= 'a' && c <='z') {
			begin = 'a';
			modulo = 26;
		}
		else if(c >= 'A' && c <= 'Z') {
			begin = 'A';
			modulo = 26;
		}
		else if(c >= '0' && c <= '9') {
			begin = '0';
			modulo = 10;
		}
		else {
			return c;
		}
		return (char) (begin + ((c - begin + PAS) % modulo)); 
	}
	
	@Override
	public String transform(String s) {
		StringBuilder ss = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			ss.append(crypt(s.charAt(i)));
		}
		return ss.toString();
	}

}
