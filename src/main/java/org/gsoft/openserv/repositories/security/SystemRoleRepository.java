package org.gsoft.openserv.repositories.security;

import org.gsoft.openserv.domain.security.SystemRole;
import org.gsoft.openserv.repositories.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SystemRoleRepository extends BaseRepository<SystemRole, Long> {
	@Query("select role from SystemRole role where role.name = :roleName")
	public SystemRole findRoleByRoleName(@Param("roleName") String roleName);
}
