package nextstep.subway.auth.domain;

public interface User {
    boolean checkPassword(String password);
}