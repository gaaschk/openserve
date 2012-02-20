package org.gsoft.openserv.repositories;

import org.gsoft.openserv.domain.Person;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends BaseSpringRepository<Person, Long> {
	public Person findPersonBySsn(String ssn);
}
