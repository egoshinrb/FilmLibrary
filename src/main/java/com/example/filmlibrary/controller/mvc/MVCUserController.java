package com.example.filmlibrary.controller.mvc;

import com.example.filmlibrary.DTO.UserDTO;
import com.example.filmlibrary.service.UserService;
import com.example.filmlibrary.service.userdetails.CustomUserDetails;
import jakarta.security.auth.message.AuthException;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

import static com.example.filmlibrary.constants.UserRolesConstants.ADMIN;

@Controller
@Slf4j
@RequestMapping("/users")
public class MVCUserController {

    private final UserService userService;

    public MVCUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new UserDTO());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") UserDTO userDTO,
                               BindingResult bindingResult) {
        if (userDTO.getLogin().equalsIgnoreCase(ADMIN) || userService.getUserByLogin(userDTO.getLogin()) != null) {
            bindingResult.rejectValue("login", "error.login", "Такой логин уже существует");
            return "registration";
        }
        if (userService.getUserByEmail(userDTO.getEmail()) != null) {
            bindingResult.rejectValue("email", "error.email", "Такой e-mail уже существует");
            return "registration";
        }
        userService.create(userDTO);
        return "redirect:login";
    }

    @GetMapping("/remember-password")
    public String rememberPassword() {
        return "users/rememberPassword";
    }

    @PostMapping("/remember-password")
    public String rememberPassword(@ModelAttribute("changePasswordForm") UserDTO userDTO) {
        userDTO = userService.getUserByEmail(userDTO.getEmail());
        if (Objects.isNull(userDTO)) {
            return "Error!";
        } else {
            userService.sendChangePasswordEmail(userDTO);
            return "redirect:/login";
        }
    }

    @GetMapping("/change-password")
    public String changePassword(@PathParam(value = "uuid") String uuid,
                                 Model model) {
        model.addAttribute("uuid", uuid);
        return "users/changePassword";
    }

    @PostMapping("/change-password")
    public String changePassword(@PathParam(value = "uuid") String uuid,
                                 @ModelAttribute("changePasswordForm") UserDTO userDTO) {
        userService.changePassword(uuid, userDTO.getPassword());
        return "redirect:/login";
    }

    @GetMapping("/change-password/user")
    public String changePassword(Model model) {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDTO userDTO = userService.getOne(Long.valueOf(customUserDetails.getUserId()));
        UUID uuid = UUID.randomUUID();
        userDTO.setChangePasswordToken(uuid.toString());
        userService.update(userDTO);
        model.addAttribute("uuid", uuid);
        return "users/changePassword";
    }

    @GetMapping("/profile/{id}")
    public String userProfile(@PathVariable Integer id,
                              @ModelAttribute(value = "exception") String exception,
                              Model model) throws AuthException {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!Objects.isNull(customUserDetails.getUserId())) {
            if (!ADMIN.equalsIgnoreCase(customUserDetails.getUsername())) {
                if (!id.equals(customUserDetails.getUserId())) {
                    throw new AuthException(HttpStatus.FORBIDDEN + ": Ошибка при обновлении");
                }
            }
        }
        model.addAttribute("user", userService.getOne(Long.valueOf(id)));
        model.addAttribute("exception", exception);
        return "profile/viewProfile";
    }

    @GetMapping("/profile/update/{id}")
    public String updateProfile(@PathVariable Integer id,
                                Model model) throws AuthException {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!id.equals(customUserDetails.getUserId())) {
            throw new AuthException(HttpStatus.FORBIDDEN + ": Ошибка при обновлении.");
        }
        model.addAttribute("userForm", userService.getOne(Long.valueOf(id)));
        return "profile/updateProfile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute("userForm") UserDTO userDTOFromUpdateForm,
                                BindingResult bindingResult) {
        UserDTO userEmailDuplicated = userService.getUserByEmail(userDTOFromUpdateForm.getEmail());
        UserDTO foundUser = userService.getOne(userDTOFromUpdateForm.getId());
        if (userEmailDuplicated != null && !Objects.equals(userEmailDuplicated.getEmail(), foundUser.getEmail())) {
            bindingResult.rejectValue("email", "error.email", "Такой email уже существует");
            return "profile/updateProfile";
        }
        foundUser.setFirstName(userDTOFromUpdateForm.getFirstName());
        foundUser.setLastName(userDTOFromUpdateForm.getLastName());
        foundUser.setMiddleName(userDTOFromUpdateForm.getMiddleName());
        foundUser.setEmail(userDTOFromUpdateForm.getEmail());
        foundUser.setBirthDate(userDTOFromUpdateForm.getBirthDate());
        foundUser.setPhone(userDTOFromUpdateForm.getPhone());
        foundUser.setAddress(userDTOFromUpdateForm.getAddress());
        userService.update(foundUser);
        return "redirect:/users/profile/" + userDTOFromUpdateForm.getId();
    }
}