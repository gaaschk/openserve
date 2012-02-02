package org.gsoft.openserv.repositories;


import java.io.Serializable;

import org.gsoft.openserv.domain.OpenServDomainObject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T extends OpenServDomainObject, ID extends Serializable> extends CrudRepository<T,ID>{
	T findOne(ID id);
	T save(T entity);
}