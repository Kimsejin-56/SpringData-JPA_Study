package study.data_jpa.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.transaction.annotation.Transactional;
import study.data_jpa.dto.UsernameOnlyDto;
import study.data_jpa.entity.Member;
import study.data_jpa.entity.Team;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class RestFeatureTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    /**
     * 동적 쿼리가 편리해 보이지만
     * 매칭 조건이 너무 단순하고, LEFT 조인이 안되는 치명적 단점 존재(사용 에메호함)
     */
    @Test
    public void basic() {
        //given
        Team teamA = new Team("teamA");
        em.persist(teamA);
        em.persist(new Member("m1", 0, teamA));
        em.persist(new Member("m2", 0, teamA));

        em.flush();
        em.clear();

        //when
        //Probe 생성
        Member member = new Member("m1");
        Team team = new Team("teamA"); //내부조인 teamA로 가능
        member.setTeam(team);

        //ExampleMatcher 생성, age 프로퍼티는 무시
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("age");

        Example<Member> example = Example.of(member, matcher);

        List<Member> result = memberRepository.findAll(example);

        //then
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void projections() {
        //given
        Team teamA = new Team("teamA");
        em.persist(teamA);

        Member m1 = new Member("m1", 0, teamA);
        Member m2 = new Member("m2", 0, teamA);
        em.persist(m1);
        em.persist(m2);

        em.flush();
        em.clear();

        //when
        List<NestedClosedProjection> result = memberRepository.findProjectionsByUsername("m1", NestedClosedProjection.class);

        //then
        for (NestedClosedProjection nestedClosedProjection : result) {
            System.out.println("nestedClosedProjection.username = " + nestedClosedProjection.getUsername());
            System.out.println("nestedClosedProjection.Team.name = " + nestedClosedProjection.getTeam().getName());
        }
        assertThat(result.size()).isEqualTo(1);
    }
}
