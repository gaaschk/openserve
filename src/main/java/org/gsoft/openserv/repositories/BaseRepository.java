package org.gsoft.openserv.repositories;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.gsoft.openserv.domain.OpenServDomainObject;
import org.gsoft.openserv.util.ListUtility;

import com.mysema.query.types.Predicate;

public abstract class BaseRepository<T extends OpenServDomainObject, ID extends Serializable> {
	protected abstract BaseSpringRepository<T, ID> getSpringRepository();
	
	public T findOne(ID id){
		return this.getSpringRepository().findOne(id);
	}
	
	public List<T> findAll(){
		return ListUtility.addAll(new ArrayList<T>(), this.getSpringRepository().findAll());
	}
	
	protected List<T> findAll(Predicate predicate){
		return ListUtility.addAll(new ArrayList<T>(), this.getSpringRepository().findAll(predicate));
	}

	public T save(T t){
		return this.getSpringRepository().save(t);
	}
	
	public void delete(T t){
		this.getSpringRepository().delete(t);
	}
}
