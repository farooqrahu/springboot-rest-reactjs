package ae.solidbase.interview.user.repository;

import ae.solidbase.interview.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "user", path = "user")
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByName(String name);
    Iterable<User> findByNameContaining(String ring);
    Iterable<User> findBySurname(String surname);
}
