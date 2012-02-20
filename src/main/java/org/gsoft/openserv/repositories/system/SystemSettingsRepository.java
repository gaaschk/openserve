package org.gsoft.openserv.repositories.system;

import org.gsoft.openserv.domain.system.SystemSettings;
import org.gsoft.openserv.repositories.BaseSpringRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemSettingsRepository extends BaseSpringRepository<SystemSettings, Long>{

}
