package com.fyregroup.openpos;

import java.util.HashMap;
import java.util.Map;

public class Order {

	// All items on the current order
	public Map<Product, Integer> currentItems = new HashMap<Product, Integer>();

	// Unique Order ID
	public int orderID;

	// Total number of voided products, going over a set amount could require
	// manager approval
	public int voidedProductCount;

	// Total value of voided products, going over a set amount could require manager
	// approval
	public int voidedTotalCount;

	public void addProduct(Product p) {
		if (currentItems.containsKey(p)) {
			int i = currentItems.get(p);
			i = i+1;
			currentItems.remove(p);
			currentItems.put(p, i);
		} else {
			currentItems.put(p, 1);
		}
	}
	
	public void addProduct(Product p, int i) {
		if (currentItems.containsKey(p)) {
			int x = currentItems.get(p);
			x = x+i;
			currentItems.remove(p);
			currentItems.put(p, x);
		} else {
			currentItems.put(p, i);
		}
	}

}
