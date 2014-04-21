package gui;

import java.util.ArrayList;

/**
 * 
 * @author Victor
 *
 */
public class ObserverList implements Subject{
	
	private ArrayList<Observer> observerList;
	
	public ObserverList() {
		observerList = new ArrayList<Observer>();
	}
	
	@Override
	public void register(Observer o) {
		observerList.add(o);
	}

	@Override
	public void unregister(Observer o) {
		observerList.remove(o);
	}

	@Override
	public void notifyObserver() {
		for(Observer o : observerList)
		{
			o.update();
		}
	}

}
