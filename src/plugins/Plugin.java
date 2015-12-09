package plugins;

public interface Plugin {
	
	public String getLabel();
	
	public String transform(String s);
}
