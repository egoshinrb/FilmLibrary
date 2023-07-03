package com.example.filmlibrary.source.service;

import com.example.filmlibrary.source.DTO.FilmDTO;
import com.example.filmlibrary.source.DTO.RoleDTO;
import com.example.filmlibrary.source.DTO.UserDTO;
import com.example.filmlibrary.source.mapper.FilmMapper;
import com.example.filmlibrary.source.mapper.GenericMapper;
import com.example.filmlibrary.source.model.Film;
import com.example.filmlibrary.source.model.Order;
import com.example.filmlibrary.source.model.User;
import com.example.filmlibrary.source.repository.GenericRepository;
import com.example.filmlibrary.source.repository.OrderRepository;
import com.example.filmlibrary.source.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService
        extends GenericService<User, UserDTO> {

    OrderRepository orderRepository;
    FilmMapper filmMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(GenericRepository<User> repository,
                       GenericMapper<User, UserDTO> mapper,
                       OrderRepository orderRepository,
                       FilmMapper filmMapper,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        super(repository, mapper);
        this.orderRepository = orderRepository;
        this.filmMapper = filmMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDTO create(UserDTO newObject) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(1L);
        newObject.setRole(roleDTO);
        newObject.setPassword(bCryptPasswordEncoder.encode(newObject.getPassword()));
        newObject.setCreatedBy("REGISTRATION FORM");
        newObject.setCreatedWhen(LocalDateTime.now());
        return mapper.toDTO(repository.save(mapper.toEntity(newObject)));
    }

    public List<FilmDTO> getAllfilmsByUserId(Long userID) {
        UserDTO userDTO = getOne(userID);
        List<Long> listOrdersId = userDTO.getOrdersIds();
        List<Film> listFilms = new ArrayList<>();
        for (Long id : listOrdersId) {
            Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("заказ не найден"));
            listFilms.add(order.getFilm());
        }
        return filmMapper.toDTOs(listFilms);
    }

    public UserDTO getUserByLogin(final String login) {
        return mapper.toDTO(((UserRepository) repository).findUserByLogin(login));
    }

    public UserDTO getUserByEmail(final String email) {
        return mapper.toDTO(((UserRepository) repository).findUserByEmail(email));
    }

    public boolean checkPassword(String password, UserDetails foundUser) {
        return bCryptPasswordEncoder.matches(password, foundUser.getPassword());
    }

//    public void sendChangePasswordEmail(final UserDTO userDTO) {
//        UUID uuid = UUID.randomUUID();
//        log.info("Generated Token: {} ", uuid);
//
//        userDTO.setChangePasswordToken(uuid.toString());
//        update(userDTO);
//
//        SimpleMailMessage mailMessage = MailUtils.createMailMessage(
//                userDTO.getEmail(),
//                MailConstants.MAIL_SUBJECT_FOR_REMEMBER_PASSWORD,
//                MailConstants.MAIL_MESSAGE_FOR_REMEMBER_PASSWORD + uuid
//        );
//
//        javaMailSender.send(mailMessage);
//
//    }

    public void changePassword(String uuid, String password) {
        UserDTO userDTO = mapper.toDTO(((UserRepository) repository).findUserByChangePasswordToken(uuid));
        userDTO.setChangePasswordToken(null);
        userDTO.setPassword(bCryptPasswordEncoder.encode(password));
        update(userDTO);
    }


//    public List<String> getUserEmailsWithDelayedRentDate() {
//        return ((UserRepository) repository).getDelayedEmails();
//    }

}