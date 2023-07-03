package com.epam.rd.autocode.queue;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;


public class   CashBox {

	private int number;

	private Deque<Buyer> buyers;

	private State state;

	public enum State {
		ENABLED, DISABLED, IS_CLOSING
	}

	public CashBox(int number) {
		this.number = number;
		this.buyers = new ArrayDeque<>();
		this.state = State.DISABLED;
	}

	public Deque<Buyer> getQueue() {
		return new ArrayDeque<>(buyers);
	}

	public Buyer serveBuyer() {
		if (buyers.isEmpty()) {
			return null;
		}
		Buyer servedBuyer = buyers.pollFirst();
		if(buyers.isEmpty() && state == State.IS_CLOSING) {
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
		buyers.addLast(byer);
	}

	public Buyer removeLast() {
		return buyers.pollLast();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("#").append(number).append(state == State.DISABLED ? "[-]" : "[+]");
		for (Buyer buyer : buyers) {
			sb.append(" ").append(buyer.toString());
		}
		return sb.toString();
	}

}
