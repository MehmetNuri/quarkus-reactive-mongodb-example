package com.mehmetnuri.mapper;

import com.mehmetnuri.dto.UserDTO;
import com.mehmetnuri.entity.User;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "cdi")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "id", target = "id", qualifiedByName = "objectIdToString")
    UserDTO toDTO(User user);

    @Mapping(source = "id", target = "id", qualifiedByName = "stringToObjectId")
    User toEntity(UserDTO userDTO);

    @Named("objectIdToString")
    default String objectIdToString(ObjectId objectId) {
        return objectId != null ? objectId.toHexString() : null;
    }

    @Named("stringToObjectId")
    default ObjectId stringToObjectId(String id) {
        return id != null ? new ObjectId(id) : null;
    }
}
