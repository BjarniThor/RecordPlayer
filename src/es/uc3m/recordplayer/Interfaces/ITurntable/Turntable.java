package es.uc3m.recordplayer.Interfaces.ITurntable;

import es.uc3m.eda.list.IList;
import es.uc3m.eda.list.IStack;
import es.uc3m.eda.list.singlelink.SList;
import es.uc3m.eda.list.singlelink.SStack;
import es.uc3m.recordplayer.Interfaces.IAxle.Axle;
import es.uc3m.recordplayer.Interfaces.IAxle.IAxle;
import es.uc3m.recordplayer.logic.Record;
import es.uc3m.recordplayer.logic.Rpm;
import es.uc3m.recordplayer.logic.Side;

public class Turntable implements ITurntable {
	
	private IAxle axle;
	private IStack<Side> playingRecords;
	private boolean playing;
	private Rpm rpm;
	
	
	public Turntable(){
		this.axle=new Axle();
		this.playingRecords=new SStack<Side>();
	}
	
	@Override
	public void start() {
		this.playing=true;		
	}

	@Override
	public void stop() {
		this.playing=false;		
	}

	@Override
	public boolean isStarted() {
		return this.playing;
	}

	@Override
	public void setRpm() {	
		this.rpm=this.playingRecords.top().getRecord().getRpm();		
	}

	@Override
	public Rpm getRpm() {
		return this.rpm;
	}

	@Override
	public void putRecord(Side side) {
		this.playingRecords.push(side);
		start();
	}

	
	///how to implement this method?
	@Override
	public IList<Record> removeRecords() {
		IList<Record> removedRecords=new SList<Record>();
		while(!this.axle.isEmpty()){		//<------is it correct like this?
			this.axle.dropRecord();
		}
		this.axle.unpinFromTurntable();
		while(this.playingRecords.isEmpty()==false){   //<------is it correct?
			removedRecords.addLast(this.playingRecords.pop().getRecord());
		}		
		return removedRecords;
	}

	@Override
	public boolean isEmpty() {
		return this.playingRecords.isEmpty();
	}

	@Override
	public boolean hasAxle() {
		return this.axle.isPinned();
	}

	@Override
	public void pinAxle(IAxle axle) {
		this.axle.pinOnTurntable(this);		
	}

	@Override
	public void unpinAxle() {
		this.axle.unpinFromTurntable();		
	}

	@Override
	public Record getTopRecord() {
		return this.playingRecords.top().getRecord();
	}

	//is it correct?
	@Override
	public int getTopSideIndex() {		
		int i;
		for (i=0; i<2; i++){
			if (getTopRecord().getSide(i).equals(this.playingRecords.top())){				
				break;
			}		
		}
		return i;
	}

}
