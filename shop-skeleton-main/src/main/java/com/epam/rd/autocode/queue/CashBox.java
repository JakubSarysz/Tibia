package com.epam.rd.autocode.queue;

import java.util.Deque;
import java.util.LinkedList;


public class   CashBox {

	private int number;

	private Deque<Buyer> byers;

	private State state;

	public enum State {
		ENABLED, DISABLED, IS_CLOSING
	}

	public CashBox(int number) {
		this.number = number;
		this.byers = new LinkedList<>();
		this.state = State.ENABLED;
	}

	public Deque<Buyer> getQueue() {
		Deque<Buyer> copy = new LinkedList<>(byers);
		return copy;
	}

	public Buyer serveBuyer() {
		if (byers.isEmpty()) {
			return null;
		}
		Buyer servedBuyer = byers.pollFirst();
		if (byers.isEmpty() && state == State.IS_CLOSING) {
			state = State.DISABLED;
		}
		return servedBuyer;
	}

	public boolean inState(State state) {
		return this.state == state;
	}

	public boolean notInState(State state) {
		return this.state != state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public State getState() {
		return state;
	}

	public void addLast(Buyer byer) {
		byers.addLast(byer);
	}

	public Buyer removeLast() {
		return byers.pollLast();
	}

	@Override
	public String toString() {
		return null;
	}

}
