package org.gsoft.openserv.util.collections;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SortedList<E> extends AbstractList<E>{
	private Comparator<E> comparator;
	private ArrayList<E> backingList;
	private boolean reversed = false;
	
	private ArrayList<E> getBackingList(){
		if(this.backingList == null){
			this.backingList = new ArrayList<>();
		}
		return backingList;
	}
	
	public SortedList(Comparator<E> comparator){
		this.comparator = comparator;
	}
	
	public SortedList(Comparator<E> comparator, boolean reversed){
		this.comparator = comparator;
	}

	@Override
	public E get(int index) {
		return this.getBackingList().get(index);
	}
	
	@Override
	public boolean add(E e){
		boolean success = this.getBackingList().add(e);
		if(success){
			Collections.sort(this.getBackingList(), this.comparator);
			if(this.reversed)Collections.reverse(this.getBackingList());
		}
		return success;
	}
	
	@Override
	public E remove(int index) {
		E removed = this.getBackingList().remove(index);
		Collections.sort(this.getBackingList(), this.comparator);
		if(this.reversed)Collections.reverse(this.getBackingList());
		return removed;
	}

	@Override
	public int size() {
		return this.getBackingList().size();
	}
}
