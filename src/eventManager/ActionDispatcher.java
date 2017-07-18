package eventManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import application.App;


public class ActionDispatcher implements ActionListener {
	
	private App application;
	private String event;
	
	/* Constructor so that we can actually process events when they occur */
	public ActionDispatcher(App app, String eventName)
	{
		application = app;
		event = eventName;
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {
		
		application.getEventProcessor().processEvent(event);

	}
	
}
