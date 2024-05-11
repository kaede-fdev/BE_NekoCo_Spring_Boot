package VJames.Development.NingnenCo_BE.Domain.DtosMapper;

import VJames.Development.NingnenCo_BE.Domain.Dtos.UserDto;
import VJames.Development.NingnenCo_BE.Domain.Entities.User;

import java.util.ArrayList;
import java.util.List;

public class UserDtoMapper {
    public static UserDto toUserDto (User user) {
        String disconnectTime = null;
        if(user.getDisconnectAt() != null) disconnectTime = user.getDisconnectAt().toString();
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .avatar(user.getAvatar())
                .username(user.getUsername())
                .appUserName(user.getAppUserName())
                .disconnectAt(disconnectTime)
                .status(user.getStatus())
                .role(user.getRole().toString())
                .build();
    }
    public static List<UserDto> toUserDtoList(List<User> users){
        List<UserDto> userDtos = new ArrayList<>();
        for(User user: users) {
            userDtos.add(toUserDto(user));
        }
        return userDtos;
    }
}
