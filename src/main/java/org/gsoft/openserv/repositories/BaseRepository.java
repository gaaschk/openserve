package org.gsoft.openserv.repositories;


import java.io.Serializable;

import org.gsoft.openserv.domain.PersistentDomainObject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T extends PersistentDomainObject, ID extends Serializable> extends CrudRepository<T,ID>{
	T findOne(ID id);
}