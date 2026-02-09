package study.data_jpa.repository;

//중첩 구조 처리
public interface NestedClosedProjection {
    String getUsername();
    TeamInfo getTeam();

    interface TeamInfo {
        String getName();
    }
}

/**
 * root 엔티티는 최적화 가능
 *
 * root 엔티티 아니면
 *  - LEFT OUTER JOIN 처리
 *  - 모든 필드를 SELECT해서 엔티티로 조회한 다음에 계산
 *
 * 단순할 떄 사용하고, 복잡하면 QueryDSL 사용하자
 */