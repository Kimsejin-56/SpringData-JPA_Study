package study.data_jpa.repository;

import org.springframework.beans.factory.annotation.Value;

public interface UsernameOnly {

    //spEL 문법 적용 - DB에서 엔티티 필드를 다 조회 후 계산(최적화X)
    //@Value("#{target.username + ' ' + target.age + ' ' + target.team.name}")
    String getUsername();
}
