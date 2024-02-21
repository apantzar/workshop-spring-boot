package gr.infoteam.workshop_spring_boot.user.services;

import gr.infoteam.workshop_spring_boot.user.User;
import gr.infoteam.workshop_spring_boot.user.dtos.UserRequestDto;
import gr.infoteam.workshop_spring_boot.user.dtos.UserResponseDto;
import gr.infoteam.workshop_spring_boot.user.enums.Role;
import gr.infoteam.workshop_spring_boot.user.mappers.UserMapper;
import gr.infoteam.workshop_spring_boot.user.repositories.UserRepository;
import gr.infoteam.workshop_spring_boot.user_info.UserInfo;
import gr.infoteam.workshop_spring_boot.user_info.enums.JobRole;
import gr.infoteam.workshop_spring_boot.user_info.enums.Location;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordUtilService passwordUtilService;

    @Override
    public List<UserResponseDto> getAll() {
        return userRepository.findAll()
                .stream()
                .map(UserResponseDto::new)
                .toList();
    }

    @Override
    public UserResponseDto getById(UUID id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return new UserResponseDto(user);
    }

    @Override
    public UserResponseDto getByEmail(String email) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        return new UserResponseDto(user);
    }

    @Override
    public UserResponseDto create(UserRequestDto requestDto) throws NoSuchAlgorithmException {
        var entity = userMapper.mapDtoToEntity(requestDto);
        passwordUtilService.encryptPassword(entity);
        var savedEntity = userRepository.save(entity);

        return new UserResponseDto(savedEntity);
    }
}
