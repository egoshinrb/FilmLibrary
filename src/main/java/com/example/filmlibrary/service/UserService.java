package com.example.filmlibrary.service;

import com.example.filmlibrary.dto.RoleDTO;
import com.example.filmlibrary.dto.UserDTO;
import com.example.filmlibrary.mapper.GenericMapper;
import com.example.filmlibrary.repository.GenericRepository;
import com.example.filmlibrary.repository.UserRepository;
import com.example.filmlibrary.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService
        extends GenericService<User, UserDTO> {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
  //  private final JavaMailSender javaMailSender;

    public UserService(GenericRepository<User> repository,
                       GenericMapper<User, UserDTO> mapper,
                       BCryptPasswordEncoder bCryptPasswordEncoder) { //JavaMailSender javaMailSender) {
        super(repository, mapper);
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        //this.javaMailSender = javaMailSender;
    }

    @Override
    public UserDTO create(UserDTO newObject) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(1L);
        newObject.setRole(roleDTO);
        newObject.setPassword(bCryptPasswordEncoder.encode(newObject.getPassword()));
//        newObject.setCreatedBy("REGISTRATION FORM");
//        newObject.setCreatedWhen(LocalDateTime.now());
        return mapper.toDTO(repository.save(mapper.toEntity(newObject)));
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

