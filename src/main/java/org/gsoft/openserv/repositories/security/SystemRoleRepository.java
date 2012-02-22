package org.gsoft.openserv.repositories.security;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.security.SystemRole;
import org.gsoft.openserv.repositories.BaseRepository;
import org.gsoft.openserv.repositories.BaseSpringRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public class SystemRoleRepository extends BaseRepository<SystemRole, Long>{
	@Resource
	private SystemRoleSpringRepository systemRoleSpringRepository;

	@Override
	protected BaseSpringRepository<SystemRole, Long> getSpringRepository() {
		return systemRoleSpringRepository;
	}
	
	public SystemRole findRoleByRoleName(String roleName){
		return this.systemRoleSpringRepository.findRoleByRoleName(roleName);
	}
	
}

@Repository
interface SystemRoleSpringRepository extends BaseSpringRepository<SystemRole, Long> {
	@Query("select role from SystemRole role where role.name = :roleName")
	public SystemRole findRoleByRoleName(@Param("roleName") String roleName);
}
