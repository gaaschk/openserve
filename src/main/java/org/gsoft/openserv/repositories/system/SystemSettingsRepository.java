package org.gsoft.openserv.repositories.system;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.system.SystemSettings;
import org.gsoft.openserv.repositories.BaseRepository;
import org.gsoft.openserv.repositories.BaseSpringRepository;
import org.springframework.stereotype.Repository;

@Repository
public class SystemSettingsRepository extends BaseRepository<SystemSettings, Long>{
	@Resource
	private SystemSettingsSpringRepository systemSettingsSpringRepository;

	@Override
	protected BaseSpringRepository<SystemSettings, Long> getSpringRepository() {
		return systemSettingsSpringRepository;
	}
}

@Repository
interface SystemSettingsSpringRepository extends BaseSpringRepository<SystemSettings, Long>{
}
