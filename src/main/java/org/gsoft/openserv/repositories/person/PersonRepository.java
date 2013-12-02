package org.gsoft.openserv.repositories.person;

import org.gsoft.openserv.domain.Person;
import org.gsoft.openserv.repositories.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends BaseRepository<Person, Long> {
	@Query("select person from Person person where person.ssn = :ssn")
	public Person findPersonBySsn(@Param("ssn") String ssn);
}
