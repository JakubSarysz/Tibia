package com.epam.rd.autocode.queue;

import java.util.ArrayList;
import java.util.List;

import com.epam.rd.autocode.queue.CashBox.State;


public class Shop {

	private int cashBoxCount;

	private List<CashBox> cashBoxes;

	public Shop(int count) {
		this.cashBoxCount = count;
		this.cashBoxes = new ArrayList<>(count);
		for (int i = 0; i < count; i++) {
			cashBoxes.add(new CashBox(i));
		}
	}

	public int getCashBoxCount() {
		return 0;
	}

	private static int getTotalBuyersCount(List<CashBox> cashBoxes) {
		return 0;
	}

	public void addBuyer(Buyer buyer) {
		int minIndex = 0;
		int minSize = cashBoxes.get(0).getQueue().size();

		for (int i = 1; i < cashBoxes.size(); i++) {
			int size = cashBoxes.get(i).getQueue().size();
			if (size < minSize) {
				minIndex = i;
				minSize = size;
			}
		}

		cashBoxes.get(minIndex).addLast(buyer);
	}

	public void tact() {

	}

	public static int[] getMinMaxSize(List<CashBox> cashBoxes) {
		return null;
	}

	public void setCashBoxState(int cashBoxNumber, State state) {
		cashBoxes.get(cashBoxNumber).setState(state);
	}

	public CashBox getCashBox(int cashBoxNumber) {
		return cashBoxes.get(cashBoxNumber);
	}

	public void print() {
		for (CashBox cashBox : cashBoxes) {
			System.out.println(cashBox);
		}
		System.out.println("==============");
	}

}
