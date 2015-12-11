package plugins;

public class ToRemoveVowel implements Plugin {

	public static final char[] VOWELS = {'a', 'e', 'i', 'o', 'u', 'y',
										 'A', 'E', 'I', 'O', 'U', 'Y'};
	
	@Override
	public String getLabel() {
		return "Supprime les voyelles";
	}
	
	public boolean isVowels(char c) {
		for (int i = 0; i < VOWELS.length; i++) {
			if (c == VOWELS[i]) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String transform(String s) {
		StringBuilder ss = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			if (!isVowels(s.charAt(i))) {
				ss.append(s.charAt(i));
			}
		}
		return ss.toString();
	}
}
