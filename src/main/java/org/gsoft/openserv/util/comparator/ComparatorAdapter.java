package org.gsoft.openserv.util.comparator;

import java.util.Comparator;

public class ComparatorAdapter<T> implements Comparator<T>{
	@SuppressWarnings("rawtypes")
	private Comparator comparator;
	
	public ComparatorAdapter(@SuppressWarnings("rawtypes") Comparator comparator){
		this.comparator = comparator;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int compare(T o1, T o2) {
		return comparator.compare(o1, o2);
	}
	
}
