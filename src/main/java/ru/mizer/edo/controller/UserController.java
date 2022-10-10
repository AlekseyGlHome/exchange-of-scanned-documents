package ru.mizer.edo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.mizer.edo.model.dto.NewUserDto;
import ru.mizer.edo.model.dto.UserDto;
import ru.mizer.edo.service.UserService;
import ru.mizer.edo.validation.UserValidator;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserValidator userValidator;

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('user:moderate')")
    public String getAllUser(Principal principal, Model model) {
        UserDto user = userService.getUserByName(principal.getName());
        model.addAttribute("users", userService.getAll());
        model.addAttribute("user", user);
        return "usersList";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAuthority('user:moderate')")
    public String newUser(Principal principal, Model model) {
        //UserDto user = userService.getUserByName(principal.getName());
        model.addAttribute("editUser", new NewUserDto());
        //model.addAttribute("user", user);

        return "new";
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('user:moderate')")
    public String add(Principal principal, @Valid @ModelAttribute("editUser") NewUserDto newUserDto, BindingResult result) {
        userValidator.validate(newUserDto, result);
        //if (userService.getCountByName(newUserDto.getName()) > 0) {
        //    result.addError(new FieldError("user", "name", "Имя не уникально"));
        //}
        if (result.hasErrors()) {
            return "new";
        }

        userService.add(newUserDto);
        return "redirect:/user/list";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('user:moderate')")
    public String getById(Principal principal, Model model, @PathVariable Integer id) {
        //UserDto user = userService.getUserByName(principal.getName());
        model.addAttribute("editUser", userService.getById(id));
        //model.addAttribute("user", user);

        return "edit";
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('user:moderate')")
    public String edit(Principal principal, @Valid @ModelAttribute("editUser") NewUserDto newUserDto, BindingResult result) {
//        result = userService.userVerification(newUserDto, result);
        userValidator.validate(newUserDto, result);
        if (result.hasErrors()) {
            return "edit";
        }

        boolean isEditActiveUser = userService.edit(newUserDto, principal.getName());
        if (isEditActiveUser) {
            return "redirect:/logout";
        }
        return "redirect:/user/list";
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('user:moderate')")
    public String delete(@PathVariable Integer id) {
        userService.deleteById(id);
        return "redirect:/user/list";
    }

}
