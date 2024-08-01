package com.example.filmlibrary.service;

import com.example.filmlibrary.DTO.FilmDTO;
import com.example.filmlibrary.DTO.RoleDTO;
import com.example.filmlibrary.DTO.UserDTO;
import com.example.filmlibrary.mapper.FilmMapper;
import com.example.filmlibrary.mapper.GenericMapper;
import com.example.filmlibrary.model.Film;
import com.example.filmlibrary.model.Order;
import com.example.filmlibrary.model.User;
import com.example.filmlibrary.repository.GenericRepository;
import com.example.filmlibrary.repository.OrderRepository;
import com.example.filmlibrary.repository.UserRepository;
import com.example.filmlibrary.utils.MailUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import lombok.extern.slf4j.Slf4j;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.filmlibrary.constants.MailConstants.*;

@Slf4j
@Service
public class UserService
        extends GenericService<User, UserDTO> {

    OrderRepository orderRepository;
    FilmMapper filmMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JavaMailSender javaMailSender;

    public UserService(GenericRepository<User> repository,
                       GenericMapper<User, UserDTO> mapper,
                       OrderRepository orderRepository,
                       FilmMapper filmMapper,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       JavaMailSender javaMailSender) {
        super(repository, mapper);
        this.orderRepository = orderRepository;
        this.filmMapper = filmMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.javaMailSender = javaMailSender;
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

    public void sendChangePasswordEmail(final UserDTO userDTO) {
        UUID uuid = UUID.randomUUID();
        log.info("Generated Token: {} ", uuid);

        userDTO.setChangePasswordToken(uuid.toString());
        update(userDTO);

        SimpleMailMessage mailMessage = MailUtils.createMailMessage(
                userDTO.getEmail(),
                MAIL_SUBJECT_FOR_REMEMBER_PASSWORD,
                MAIL_MESSAGE_FOR_REMEMBER_PASSWORD + uuid
        );

        javaMailSender.send(mailMessage);

    }

    public List<String> getUserEmailsWithDelayedRentDate() {
        return ((UserRepository) repository).getDelayedEmails();
    }

    public void changePassword(String uuid, String password) {
        UserDTO userDTO = mapper.toDTO(((UserRepository) repository).findUserByChangePasswordToken(uuid));
        userDTO.setChangePasswordToken(null);
        userDTO.setPassword(bCryptPasswordEncoder.encode(password));
        update(userDTO);
    }
}
