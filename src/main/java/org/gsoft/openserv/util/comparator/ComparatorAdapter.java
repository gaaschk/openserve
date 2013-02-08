package org.gsoft.openserv.util.comparator;

import java.util.Comparator;

public class ComparatorAdapter<T> implements Comparator<T>{
	private Comparator comparator;
	
	public ComparatorAdapter(Comparator comparator){
		this.comparator = comparator;
	}
	
	@Override
	public int compare(T o1, T o2) {
		return comparator.compare(o1, o2);
	}
	
}
