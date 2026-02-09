package study.data_jpa.repository;

import org.springframework.beans.factory.annotation.Value;

// 스프링 데이터 JPA가 구현체 제공
public interface UsernameOnly {

    //spEL 문법 적용 - DB에서 엔티티 필드를 다 조회 후 계산(최적화X)
    //@Value("#{target.username + ' ' + target.age + ' ' + target.team.name}")
    String getUsername();
}


