package view;

import java.util.Observer;

public interface View extends Observer {

	public void start();
	
	public void showMessage(String message);
	
	public void end();
}
