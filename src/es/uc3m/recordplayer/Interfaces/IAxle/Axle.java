package es.uc3m.recordplayer.Interfaces.IAxle;

import es.uc3m.eda.list.IQueue;
import es.uc3m.eda.list.singlelink.SQueue;
import es.uc3m.recordplayer.Interfaces.ITurntable.ITurntable;
import es.uc3m.recordplayer.logic.Record;

public class Axle implements IAxle{
	private IQueue<Record> waitingRecords;
	private boolean pinned;
	private ITurntable turntable;
	private char sideIndex;
	private int size;
	
	//default constructor
	public Axle(){
		this.turntable=null;
		this.waitingRecords= new SQueue<Record>();
		this.size=0;
	}
	
	@Override
	public void pinOnTurntable(ITurntable turntable) {
		this.turntable=turntable;
		this.pinned=true;
		
	}

	@Override
	public void unpinFromTurntable() {
		if (this.isEmpty()){
			this.turntable=null;
			this.pinned=false;
		}
	}

	@Override
	public void putRecord(Record record, char sideIndex) {
		if(this.isPinned() && (this.isFull()==false)){
			this.waitingRecords.enqueue(record);
			this.size++;
		}
		
	}

	@Override
	public void dropRecord() {
		if(size>0){
			this.turntable.putRecord(this.waitingRecords.dequeue(), this.sideIndex);
			size--;
		}
	}

	@Override
	public boolean isPinned() {
		return this.pinned;
	}

	@Override
	public boolean isFull() {		
		return (this.size==5);
	}

	@Override
	public boolean isEmpty() {
		return this.waitingRecords.isEmpty();
	}

}
