package org.gsoft.phoenix.repositories;


import java.io.Serializable;

import org.gsoft.phoenix.domain.PhoenixDomainObject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T extends PhoenixDomainObject, ID extends Serializable> extends CrudRepository<T,ID>{
	T findOne(ID id);
	T save(T entity);
}