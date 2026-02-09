package study.data_jpa.dto;

public interface MemberProjection {
    Long getId();
    String getUsername();
    String getTeamName();
}
/**
 * 스프링 데이터 JPA 기반 네이티브 쿼리(권장X, 가끔 사용)
 *  - 페이징 지원
 *  - 반환 타입
 *   - Object[]
 *   - Tuple
 *   - DTO(스프링 데이터 인터페이스 Projections 지원)
 *  - 제약
 *   - Sort 파라미터를 통한 정렬이 정상 동작하지 않을 수 있음(믿지 말고 직접 처리)
 *   - JPQL처럼 애플리케이션 로딩 시점에 문법 확인 불가
 *   - 동적 쿼리 불가
 *    - 할려면 하이버네이트 직접 활용하거나 외부 라이브러리 사용 (JdbcTemplate, myBatis, jooq)
 */
