package com.mehmetnuri.service;

import com.mehmetnuri.common.PageResult;
import com.mehmetnuri.dto.UserDTO;
import com.mehmetnuri.entity.User;
import com.mehmetnuri.mapper.UserMapper;
import com.mehmetnuri.repository.UserRepository;
import com.mehmetnuri.utils.PaginationUtils;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;
import org.jboss.logging.Logger;

import java.lang.reflect.Field;
import java.util.List;
import java.util.regex.Pattern;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    private static final Logger LOGGER = Logger.getLogger(UserService.class);

    public Uni<PageResult<UserDTO>> getAllUsers(int page, int size) {
        Uni<Long> countUni = userRepository.count();
        Uni<List<User>> usersUni = userRepository.findAll()
                .page(page, size)
                .list();

        return PaginationUtils.createPageResult(countUni, usersUni, UserMapper.INSTANCE::toDTO, page, size);
    }

    public Uni<UserDTO> getUserById(String id) {
        ObjectId objectId = new ObjectId(id);
        return userRepository.findById(objectId)
                .map(user -> {
                    if (user != null) {
                        return UserMapper.INSTANCE.toDTO(user);
                    } else {
                        return null;
                    }
                });
    }

    public Uni<PageResult<UserDTO>> getUsersByName(String name, int page, int size) {
        LOGGER.info("Searching users with name: " + name);
        Pattern pattern = Pattern.compile(name, Pattern.CASE_INSENSITIVE);
        Uni<Long> countUni = userRepository.count("{'name': {$regex: ?1}}", pattern.pattern());
        Uni<List<User>> usersUni = userRepository.find("{'name': {$regex: ?1}}", pattern.pattern())
                .page(page, size)
                .list();

        return PaginationUtils.createPageResult(countUni, usersUni, UserMapper.INSTANCE::toDTO, page, size);
    }

    public Uni<PageResult<UserDTO>> getUsersByAge(int age, int page, int size) {
        Uni<Long> countUni = userRepository.count("{'age': ?1}", age);
        Uni<List<User>> usersUni = userRepository.find("{'age': ?1}", age)
                .page(page, size)
                .list();

        return PaginationUtils.createPageResult(countUni, usersUni, UserMapper.INSTANCE::toDTO, page, size);
    }

    public Uni<PageResult<UserDTO>> getUsersByEmailDomain(String domain, int page, int size) {
        Pattern pattern = Pattern.compile(domain, Pattern.CASE_INSENSITIVE);
        Uni<Long> countUni = userRepository.count("{'email': {$regex: ?1}}", pattern.pattern());
        Uni<List<User>> usersUni = userRepository.find("{'email': {$regex: ?1}}", pattern.pattern())
                .page(page, size)
                .list();

        return PaginationUtils.createPageResult(countUni, usersUni, UserMapper.INSTANCE::toDTO, page, size);
    }

    public Uni<UserDTO> createUser(UserDTO userDTO) {
        User user = UserMapper.INSTANCE.toEntity(userDTO);
        user.prePersist();
        return userRepository.persist(user)
                .replaceWith(UserMapper.INSTANCE.toDTO(user));
    }

    public Uni<UserDTO> updateUser(String id, UserDTO updatedUserDTO) {
        LOGGER.info("Updating user with ID: " + id + " with data: " + updatedUserDTO);
        ObjectId objectId = new ObjectId(id);
        return userRepository.findById(objectId)
                .onItem().ifNotNull().transformToUni(user -> {
                    User userEntity = (User) user;
                    updateNonNullFields(userEntity, updatedUserDTO);
                    userEntity.preUpdate();
                    return userRepository.update(userEntity)
                            .replaceWith(UserMapper.INSTANCE.toDTO(userEntity));
                });
    }

    public Uni<Boolean> deleteUser(String id) {
        ObjectId objectId = new ObjectId(id);
        return userRepository.deleteById(objectId);
    }

    private void updateNonNullFields(User userEntity, UserDTO updatedUserDTO) {
        Field[] fields = UserDTO.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(updatedUserDTO);
                if (value != null) {
                    Field userField = User.class.getDeclaredField(field.getName());
                    userField.setAccessible(true);
                    userField.set(userEntity, value);
                }
            } catch (IllegalAccessException | NoSuchFieldException e) {
                LOGGER.error("Error updating field: " + field.getName(), e);
            }
        }
    }
}
