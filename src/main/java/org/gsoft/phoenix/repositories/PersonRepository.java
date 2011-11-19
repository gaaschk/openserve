package org.gsoft.phoenix.repositories;

import org.gsoft.phoenix.domain.Person;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends BaseRepository<Person, Long> {
	public Person findPersonBySsn(String ssn);
}
