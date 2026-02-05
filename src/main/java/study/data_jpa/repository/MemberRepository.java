package study.data_jpa.repository;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.data_jpa.dto.MemberDto;
import study.data_jpa.entity.Member;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> , MemberRepositoryCustom{
    /**
     * 간단한 방식 - 메서드 이름으로 쿼리 생성
     * 권장 X - NamedQuery
     * 권장 O + 복잡한 방식 - Query로 레포지토리 메서드에 쿼리 정의
     */

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    @Query(name = "Member.findByUsername")
        // 생략 가능 우선순위가 Named 쿼리 찾아서 실행 후에 없으면 메서드 이름으로 쿼리 생성
    List<Member> findByUsername(@Param("username") String username);

    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findMember(@Param("username") String username, @Param("age") int age);

    @Query("select new study.data_jpa.dto.MemberDto(m.id, m.username, t.name) " +
            "from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.username in :names")
    List<Member> findMembers(@Param("names") Collection names);

    List<Member> findListByUsername(String name); // 컬렉션

    Member findMemberByUsername(String name); //단건

    Optional<Member> findOptionalByUsername(String name); //단건 Optional

    Page<Member> findByAge(int age, Pageable pageable); //count 쿼리 사용
    //Slice<Member> findByAge(int age, Pageable pageable); //count 쿼리 사용 안함
    //List<Member> findByAge(int age, Pageable pageable); //count 쿼리 사용 안함

    //count 쿼리를 분리 가능
    @Query(value = "select m from Member m",
            countQuery = "select count(m) from Member m")
    Page<Member> findMemberAllCountBy(Pageable pageable);


    /**
     * 벌크성 수정은 DB에 직접 데이터를 수정해서 영속성 컨택스트와 데이터 불일치가 발생 O
     * - 영속성 컨택스트 초기화 시키고 엔티티를 조회하기
     */
    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    //공통 메서드 오버라이딩
    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();

    //JPQL + 엔티티 그래프
    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();

    //메서드 이름으로 패치 조인 적용
    @EntityGraph(attributePaths = {"team"})
    List<Member> findEntityGraphByUsername(@Param("username") String username);

    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByUsername(String username);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUsername(String username);


}


