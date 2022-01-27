package hello.hellospring.respository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    // findByName(String name) -> JPQL select m from Member m where m.name = ?
    // findByNameAndId(String name, Long id) -> JPQL select m from Member m where m.name= ? and m.id= ?
    @Override
    Optional<Member> findByName(String name);
}
