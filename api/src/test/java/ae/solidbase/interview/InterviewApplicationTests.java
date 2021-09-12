package ae.solidbase.interview;

import ae.solidbase.interview.user.model.User;
import ae.solidbase.interview.user.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
class InterviewApplicationTests {

    private final TestEntityManager entityManager;
    private final UserRepository userRepository;

    @Autowired
    public InterviewApplicationTests(TestEntityManager entityManager, UserRepository userRepository) {
        this.entityManager = entityManager;
        this.userRepository = userRepository;
    }

    @Test
    public void checkUserSize() {
        User usr = new User();
        usr.setName("Farooq");
        usr.setId(1111111l);
        entityManager.persist(usr);
        entityManager.flush();
        Iterable<User> users = userRepository.findAll();
        int userCount = 500;
        assertThat(users).hasSize(userCount);
    }

    @Test
    public void should_find_with_name() {
        List<User> userList = userRepository.findByName("Farooq");
        assertThat(userList).isNotEmpty();
    }


    @Test
    public void should_find_no_user_if_repository_is_empty() {
        Iterable<User> users = userRepository.findAll();

        assertThat(users).isEmpty();
    }

    @Test
    public void should_store_a_user() {
        User user = userRepository.save(new User("User title", "User desc"));

        assertThat(user).hasFieldOrPropertyWithValue("title", "User title");
        assertThat(user).hasFieldOrPropertyWithValue("description", "User desc");
    }

    @Test
    public void should_find_all_user() {
        User u1 = new User("User#1", "Email#1");
        entityManager.persist(u1);

        User u2 = new User("User#2", "Email#2");
        entityManager.persist(u2);

        User tut3 = new User("User#3", "Email#3");
        entityManager.persist(tut3);

        Iterable<User> users = userRepository.findAll();

        assertThat(users).hasSize(3).contains(u1, u2, tut3);
    }

    @Test
    public void should_find_user_by_id() {
        User u1 = new User("User#1", "Email#1");
        entityManager.persist(u1);

        User u2 = new User("User#2", "Email#2");
        entityManager.persist(u2);

        User foundTutorial = userRepository.findById(u2.getId()).get();

        assertThat(foundTutorial).isEqualTo(u2);
    }

    @Test
    public void should_find_Surname_user() {
        User u1 = new User("User#1", "Email#1");
        entityManager.persist(u1);

        User u2 = new User("User#2", "Email#2");
        entityManager.persist(u2);

        User u3 = new User("User#3", "Email#3");
        entityManager.persist(u3);

        Iterable<User> bySurnaem = userRepository.findBySurname(u1.getSurname());

        assertThat(bySurnaem).hasSize(2).contains(u1, u3);
    }

    @Test
    public void should_find_user_by_user_containing_string() {
        User u1 = new User("Spring Boot User#1", "Email#1");
        entityManager.persist(u1);

        User u2 = new User("Java User#2", "Email#2");
        entityManager.persist(u2);

        User u3 = new User("Spring Data JPA User#3", "Email#3");
        entityManager.persist(u3);

        Iterable<User> users = userRepository.findByNameContaining("ring");

        assertThat(users).hasSize(2).contains(u1, u3);
    }

    @Test
    public void should_update_user_by_id() {
        User u1 = new User("User#1", "Email#1");
        entityManager.persist(u1);

        User u2 = new User("User#2", "Email#2");
        entityManager.persist(u2);

        User updatedTut = new User("updated User#2", "updated Email#2");

        User user = userRepository.findById(u2.getId()).get();
        user.setName(updatedTut.getName());
        user.setSurname(updatedTut.getSurname());
        user.setEmail(updatedTut.getEmail());
        userRepository.save(user);

        User checkTut = userRepository.findById(u2.getId()).get();

        assertThat(checkTut.getId()).isEqualTo(u2.getId());
        assertThat(checkTut.getName()).isEqualTo(updatedTut.getName());
        assertThat(checkTut.getEmail()).isEqualTo(updatedTut.getEmail());
        assertThat(checkTut.getSurname()).isEqualTo(updatedTut.getSurname());
    }

    @Test
    public void should_delete_user_by_id() {
        User u1 = new User("User#1", "Email#1");
        entityManager.persist(u1);

        User u2 = new User("User#2", "Email#2");
        entityManager.persist(u2);

        User tut3 = new User("User#3", "Email#3");
        entityManager.persist(tut3);

        userRepository.deleteById(u2.getId());

        Iterable<User> users = userRepository.findAll();

        assertThat(users).hasSize(2).contains(u1, tut3);
    }

    @Test
    public void should_delete_all_user() {
        entityManager.persist(new User("User#1", "Email#1"));
        entityManager.persist(new User("User#2", "Email#2"));

        userRepository.deleteAll();

        assertThat(userRepository.findAll()).isEmpty();
    }
}
