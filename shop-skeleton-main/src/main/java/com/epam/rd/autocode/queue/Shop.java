package com.epam.rd.autocode.queue;

import java.util.*;

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
		int totalBuyers = 0;
		int enabledCashBoxes = 0;
		int closingQueues = 0;

		// Step 1: Calculate the total number of buyers, enabled cash boxes, and closing queues
		for (CashBox cashBox : cashBoxes) {
			totalBuyers += cashBox.getQueue().size();

			if (cashBox.inState(CashBox.State.ENABLED)) {
				enabledCashBoxes++;
			}

			if (cashBox.inState(CashBox.State.IS_CLOSING)) {
				closingQueues++;
			}
		}

		// Step 2: Determine the maximum and minimum number of buyers per enabled cash box
		int maxBuyersPerCashBox = totalBuyers / enabledCashBoxes;
		int minBuyersPerCashBox = maxBuyersPerCashBox;

		int remainingBuyers = totalBuyers % enabledCashBoxes;
		if (remainingBuyers > 0) {
			maxBuyersPerCashBox++;
		}

		// Step 3: Perform the balancing of queues
		Queue<Buyer> defectorBuyers = new LinkedList<>();

		for (CashBox cashBox : cashBoxes) {
			int queueSize = cashBox.getQueue().size();

			if (queueSize > maxBuyersPerCashBox) {
				int excessBuyers = queueSize - maxBuyersPerCashBox;
				for (int i = 0; i < excessBuyers; i++) {
					Buyer buyer = cashBox.removeLast();
					defectorBuyers.add(buyer);
				}
			}
		}

		for (CashBox cashBox : cashBoxes) {
			if (cashBox.inState(CashBox.State.ENABLED)) {
				while (cashBox.getQueue().size() < minBuyersPerCashBox && !defectorBuyers.isEmpty()) {
					cashBox.addLast(defectorBuyers.remove());
				}
			}
		}
	}

}
