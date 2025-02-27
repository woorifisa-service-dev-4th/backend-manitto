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
            System.out.println("이메일 형식이 올바르지 않음: " + email);
            return false;
        }

        // 중복 이메일 검사
        if (userRepository.findByEmail(email).isPresent()) {
            System.out.println("이미 존재하는 이메일: " + email);
            return false;
        }

        // 비밀번호 해싱
        String hashedPassword = Hasher.hash(password);
        User newUser = new User(username, email, hashedPassword);

        // DB에 저장
        userRepository.save(newUser);
        return true;
    }

    public User login(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            System.out.println("[로그인 시도] 이메일 존재: " + email);
            System.out.println("입력된 비밀번호: " + password);
            System.out.println("저장된 비밀번호: " + user.get().getPassword());

            if (user.get().getPassword().equals(Hasher.hash(password))) {
                System.out.println("[로그인 성공] " + email);
                return user.get();
            } else {
                System.out.println("[로그인 실패] 비밀번호 불일치: " + email);
            }
        } else {
            System.out.println("[로그인 실패] 존재하지 않는 이메일: " + email);
        }
        return null;
    }

    private boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId);
    }
}
