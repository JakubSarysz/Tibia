package com.epam.rd.autocode.queue;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

public class CashBox {
	private int number;
	private Deque<Buyer> queue;
	private State state;

	public enum State {
		ENABLED, DISABLED, IS_CLOSING
	}

	public CashBox(int number) {
		this.number = number;
		this.queue = new LinkedList<>();
		this.state = State.DISABLED;
	}

	public Deque<Buyer> getQueue() {
		return new ArrayDeque<>(queue);
	}

	public Buyer serveBuyer() {
		if (!queue.isEmpty()) {
			return queue.removeFirst();
		}
		return null;
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

	public void addLast(Buyer buyer) {
		queue.addLast(buyer);
	}

	public Buyer removeLast() {
		if (!queue.isEmpty()) {
			return queue.removeLast();
		}
		return null;
	}

	public int getNumber() {
		return number;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("#").append(number).append("[");

		if (state == State.DISABLED) {
			sb.append("-");
		} else if (state == State.ENABLED) {
			sb.append("+");
		} else if (state == State.IS_CLOSING) {
			sb.append("|");
		}
		sb.append("]");

		return sb.toString();
	}
}
