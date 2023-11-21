package com.example.Service;

import com.example.Dto.UserDto;
import com.example.Entity.UserEntity;
import com.example.Repository.UserRepository;
import com.example.UserNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<UserEntity> getAllUser(String fullname){
        if (StringUtils.isEmpty(fullname))
            return userRepository.findAll();
        else
            return userRepository.findAllByFullname(fullname);
    }
    public UserDto getDetail(Integer id) {
        UserDto userDto = new UserDto();
        UserEntity userEntity = userRepository.findById(id).get();
        if (userEntity != null)
            BeanUtils.copyProperties(userEntity, userDto);
        else
            return null;
        return userDto;
    }
    public UserDto getDetailByManv(String manv){
        UserDto userDto = new UserDto();
        UserEntity userEntity = userRepository.findFirstByManv(manv);
        if (userEntity != null)
            BeanUtils.copyProperties(userEntity, userDto);
        else
            return null;
        return userDto;
    }
    public UserDto addUser(UserDto userDto){
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto, userEntity);
        userRepository.save(userEntity);
        userDto.setId(userEntity.getId());
        return userDto;
    }

    public UserDto updateUser(UserDto userDto){
        UserEntity userEntity = userRepository.findById(userDto.getId()).get();
        BeanUtils.copyProperties(userDto, userEntity);
        userRepository.save(userEntity);
        return userDto;
    }

    public void deleteUser(Integer id){
        Long count = userRepository.countById(id);
        if(count== null || count == 0){
            throw new UserNotFoundException("could not find any users with Id"+ id);
        }
        userRepository.deleteById(id);
    }

}
