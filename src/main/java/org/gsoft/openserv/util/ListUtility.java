package org.gsoft.openserv.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ListUtility {
	public static <E extends Object> List<E> addAll(List<E> toFill, Iterable<E> iterable){
		if(iterable == null)return null;
		Iterator<E> listIter = iterable.iterator();
		while(listIter.hasNext()){
			toFill.add(listIter.next());
		}
		return toFill;
	}
	
	public static <E extends Object, F extends Comparable<F>> void sortListOfMaps(List<Map<E,F>> unsorted, final E key){
		Collections.sort(unsorted, new Comparator<Map<E,F>>(){
			@Override
			public int compare(Map<E, F> arg0, Map<E, F> arg1) {
				if(arg0 == arg1){
					return 0;
				}
				else if(arg0 == null){
					return -1;
				}
				else if(arg1 == null){
					return 1;
				}
				else if(arg0.get(key) == arg1.get(key)){
					return 0;
				}
				else if(arg0.get(key) == null){
					return -1;
				}
				else if(arg1.get(key) == null){
					return 1;
				}
				else{
					return arg0.get(key).compareTo(arg1.get(key));
				}
			}
		});
	}
	
	public static <E extends Object, F extends Comparable<F>> void sortListOfMaps(List<Map<E,F>> unsorted, final E key, final Comparator<F> comparator){
		Collections.sort(unsorted, new Comparator<Map<E,F>>(){
			@Override
			public int compare(Map<E, F> arg0, Map<E, F> arg1) {
				if(arg0 == arg1){
					return 0;
				}
				else if(arg0 == null){
					return -1;
				}
				else if(arg1 == null){
					return 1;
				}
				else if(arg0.get(key) == arg1.get(key)){
					return 0;
				}
				else if(arg0.get(key) == null){
					return -1;
				}
				else if(arg1.get(key) == null){
					return 1;
				}
				else{
					return comparator.compare(arg0.get(key), arg1.get(key));
				}
			}
		});
	}
}
