package org.gsoft.openserv.repositories.security;

import org.gsoft.openserv.domain.security.SystemRole;
import org.gsoft.openserv.repositories.BaseSpringRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SystemRoleRepository extends BaseSpringRepository<SystemRole, Long> {
	@Query("select role from SystemRole role where role.name = :roleName")
	public SystemRole findRoleByRoleName(@Param("roleName") String roleName);
}
