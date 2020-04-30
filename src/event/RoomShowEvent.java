package event;

import frame.salesman.RoomShowFrame;

public class RoomShowEvent {
	RoomShowFrame rsf;
	public RoomShowEvent() {
		
	}
	public RoomShowEvent(RoomShowFrame rsf) {
		this();
		this.rsf = rsf;
	}
}
