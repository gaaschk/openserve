package org.gsoft.openserv.repositories.security;

import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.security.Permission;
import org.gsoft.openserv.domain.security.SystemUser;
import org.gsoft.openserv.repositories.BaseRepository;
import org.gsoft.openserv.repositories.BaseSpringRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public class SystemUserRepository extends BaseRepository<SystemUser, Long>{
	@Resource
	private SystemUserSpringRepository systemUserSpringRepository;

	@Override
	protected BaseSpringRepository<SystemUser, Long> getSpringRepository() {
		return systemUserSpringRepository;
	}
	
	public SystemUser findByUsername(String username){
		return this.systemUserSpringRepository.findByUsername(username);
	}
	
	public List<Permission> findAllPermissionsForUser(String username){
		return this.systemUserSpringRepository.findAllPermissionsForUser(username);
	}
}

@Repository
interface SystemUserSpringRepository extends BaseSpringRepository<SystemUser, Long>{
	
	@Query("select user from SystemUser user where username = :username")
	public SystemUser findByUsername(@Param("username") String username);
	
	@Query("select distinct p from AssignedPermission ap, Permission p where ap.permissionID = p.permissionID " +
		   "and ap.principalID in (select principalID from SystemUser where username = :username) or ap.principalID in " +
           "(select ar.principalID from SystemRole ar, SystemUser su where ar in elements(su.assignedRoles) " + 
           "and su.username = :username)")
	public List<Permission> findAllPermissionsForUser(@Param("username") String username);
}
