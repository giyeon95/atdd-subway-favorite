package nextstep.subway.fixture;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nextstep.member.domain.Member;
import nextstep.member.domain.RoleType;

@Getter
@RequiredArgsConstructor
public enum MockMember {
    ADMIN("admin@admin.com", "admin_password", 20, RoleType.ROLE_ADMIN),
    GUEST("user@user.com", "user_password", 20, RoleType.ROLE_MEMBER);

    private final String email;
    private final String password;
    private final int age;
    private final List<RoleType> roleTypes;

    MockMember(String email, String password, int age, RoleType... roleTypes) {
        this(email, password, age, List.of(roleTypes));
    }


    public static List<Member> getAllMembers() {
        return Arrays.stream(MockMember.values())
            .map(MockMember::toMember)
            .collect(Collectors.toUnmodifiableList());
    }

    public List<String> getAuthorities() {
        return roleTypes.stream()
                .map(Enum::name)
                .collect(Collectors.toUnmodifiableList());
    }

    public Member toMember() {
        return new Member(
            this.email,
            this.password,
            this.age,
            getAuthorities()
        );
    }
}
