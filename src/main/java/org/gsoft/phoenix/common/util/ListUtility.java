package org.gsoft.phoenix.common.util;

import java.util.Iterator;
import java.util.List;

public class ListUtility {
	public static <E extends Object> List<E> addAll(List<E> toFill, Iterable<E> iterable){
		if(iterable == null)return null;
		Iterator<E> listIter = iterable.iterator();
		while(listIter.hasNext()){
			toFill.add(listIter.next());
		}
		return toFill;
	}
}
