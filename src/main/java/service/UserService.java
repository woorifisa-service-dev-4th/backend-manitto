package service;

import domain.User;
import repository.UserRepository;
import util.Hasher;
import java.util.Optional;
import java.util.regex.Pattern;

public class UserService {
    private final UserRepository userRepository = new UserRepository();
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");

    public boolean signup(String username, String email, String password) {
        // 이메일 유효성 검사
        if (!isValidEmail(email)) {
            return false;
        }

        // 중복 이메일 검사
        if (userRepository.findByEmail(email) != null) {
            return false;
        }

        // 비밀번호 해싱
        String hashedPassword = Hasher.hash(password);
        User newUser = new User(username, email, hashedPassword);

        // DB에 저장
        userRepository.save(newUser);
        return true;
    }

    public boolean login(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            System.out.println("로그인 실패: 존재하지 않는 이메일");
            return false;
        }

        String hashedPassword = Hasher.hash(password);
        System.out.println("입력한 비밀번호 해싱값: " + hashedPassword);
        System.out.println("DB 저장된 해싱값: " + user.getPassword());

        return hashedPassword.equals(user.getPassword());
    }


    private boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    public Optional<User> getUserByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }
}
