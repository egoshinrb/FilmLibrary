package com.example.filmlibrary.source.mapper;

import com.example.filmlibrary.source.DTO.UserDTO;
import com.example.filmlibrary.source.model.User;
import com.example.filmlibrary.source.model.GenericModel;
import com.example.filmlibrary.source.repository.OrderRepository;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class UserMapper extends GenericMapper<User, UserDTO> {

    private final OrderRepository orderRepository;

    public UserMapper(ModelMapper modelMapper, OrderRepository orderRepository) {
        super(User.class, UserDTO.class, modelMapper);
        this.orderRepository = orderRepository;
    }

    @PostConstruct
    @Override
    protected void setupMapper() {
        modelMapper.createTypeMap(User.class, UserDTO.class)
                .addMappings(m -> m.skip(UserDTO::setOrdersIds)).setPostConverter(toDTOConverter());
        modelMapper.createTypeMap(UserDTO.class, User.class)
                .addMappings(m -> m.skip(User::setOrders)).setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapSpecificFields(UserDTO source, User destination) {
        if (!Objects.isNull(source.getOrdersIds())) {
            destination.setOrders(orderRepository.findAllById(source.getOrdersIds()));
        }
        else {
            destination.setOrders(Collections.emptyList());
        }
    }

    @Override
    protected void mapSpecificFields(User source, UserDTO destination) {
        destination.setOrdersIds(getIds(source));
    }

    @Override
    protected List<Long> getIds(User entity) {
        return Objects.isNull(entity) || Objects.isNull(entity.getOrders())
                ? null
                : entity.getOrders().stream()
                .map(GenericModel::getId)
                .collect(Collectors.toList());
    }
}