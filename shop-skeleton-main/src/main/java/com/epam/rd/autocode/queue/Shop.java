package com.epam.rd.autocode.queue;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Shop {
	private int cashBoxCount;
	private List<CashBox> cashBoxes;

	public Shop(int count) {
		cashBoxes = new ArrayList<>(count);
		for (int i = 0; i < count; i++) {
			cashBoxes.add(new CashBox(i));
		}
		cashBoxCount = count;
	}

	public int getCashBoxCount() {
		return cashBoxCount;
	}

	public void addBuyer(Buyer buyer) {
		CashBox smallestQueueCashBox = findSmallestQueueCashBox();
		smallestQueueCashBox.addLast(buyer);
	}

	private CashBox findSmallestQueueCashBox() {
		CashBox smallestQueueCashBox = null;
		int minSize = Integer.MAX_VALUE;

		for (CashBox cashBox : cashBoxes) {
			if (cashBox.inState(CashBox.State.ENABLED)) {
				int size = cashBox.getQueue().size();
				if (size < minSize) {
					minSize = size;
					smallestQueueCashBox = cashBox;
				}
			}
		}

		if (smallestQueueCashBox == null) {
			throw new IllegalStateException("No enabled cash boxes found");
		}

		return smallestQueueCashBox;
	}

	public void tact() {
		// Step 1: Serve one buyer from each non-empty queue
		for (CashBox cashBox : cashBoxes) {
			cashBox.serveBuyer();
		}


		// Step 2: Balance the queues
		balance();

		// Step 3: Update the state of cashboxes that reached the end of their queues
		for (CashBox cashBox : cashBoxes) {
			if (cashBox.inState(CashBox.State.IS_CLOSING) && cashBox.getQueue().isEmpty()) {
				cashBox.setState(CashBox.State.DISABLED);
			}
		}

	}


	public void print() {
		cashBoxes.sort(Comparator.comparingInt(CashBox::getNumber));

		for (CashBox cashBox : cashBoxes) {
			StringBuilder sb = new StringBuilder();
			sb.append("#").append(cashBox.getNumber());

			CashBox.State state = cashBox.getState();
			if (state == CashBox.State.ENABLED) {
				sb.append("[+]");
			} else if (state == CashBox.State.DISABLED) {
				sb.append("[-]");
			} else if (state == CashBox.State.IS_CLOSING) {
				sb.append("[|]");
			}

			Deque<Buyer> queue = cashBox.getQueue();
			for (Buyer buyer : queue) {
				sb.append(buyer.toString());
			}

			System.out.println(sb.toString());
		}
	}

	public CashBox getCashBox(int cashBoxNumber) {
		if (cashBoxNumber >= 0 && cashBoxNumber < cashBoxes.size()) {
			return cashBoxes.get(cashBoxNumber);
		}
		throw new IllegalArgumentException("Invalid cashbox number");
	}

	public void setCashBoxState(int cashBoxNumber, CashBox.State state) {
		CashBox cashBox = getCashBox(cashBoxNumber);
		cashBox.setState(state);
	}

	private void balance() {

		long sum = tibia();

		Data cokolwiek = test();
		long max = metoda(cokolwiek, sum);

		Deque<Buyer> defectorBuyers = new LinkedList<>();
		for (CashBox cashBox : cashBoxes) {
			long size = cashBox.getQueue().size() - max;
			if (size > 0) {
				for (int i = 0; i < size; i++) {
					Buyer buyer = cashBox.removeLast();
					defectorBuyers.add(buyer);
				}
			}
		}

		for (CashBox cashBox : cashBoxes) {
			if (cashBox.inState(CashBox.State.ENABLED)) {
				while (cashBox.getQueue().size() < max && !defectorBuyers.isEmpty()) {
					cashBox.addLast(defectorBuyers.removeFirst());
				}
			}
		}
	}

	private  long tibia() {

		long sum = 0;
		for(int i = 0; i < cashBoxes.size() - 1; i++){
			sum += cashBoxes.get(i).getQueue().size();
		}
		return sum;
	}
private Data test() {
		long enabled = 0;
		long closed = 0;
		long isclosing = 0;
long closingQueues = 0;
		for(int i = 0 ; i < cashBoxes.size() - 1; i++){

			if(cashBoxes.get(i).inState(CashBox.State.ENABLED)){
				enabled++;
			}
			if(cashBoxes.get(i).inState(CashBox.State.IS_CLOSING)){
				isclosing++;
				closingQueues += cashBoxes.get(i).getQueue().size();
			}
			if(cashBoxes.get(i).inState(CashBox.State.DISABLED)){
				closed++;
			}
		}
		return new Data(enabled, closed, isclosing, closingQueues);
}

record Data(long enabled, long closed, long isclosing, long closingQueues){

}

long metoda(Data cokolwiek, long maximus) {
		if(cokolwiek.enabled != 0  ) {
			return maximus /  cokolwiek.enabled - cokolwiek.isclosing;
		}
		else{
			return maximus +1;
		}
}
}
