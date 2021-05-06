package org.ksga.springboot.springsecuritydemo.controller.rest;

import org.ksga.springboot.springsecuritydemo.payload.dto.UserDto;
import org.ksga.springboot.springsecuritydemo.payload.request.UserRequest;
import org.ksga.springboot.springsecuritydemo.payload.response.Response;
import org.ksga.springboot.springsecuritydemo.security.service.UserDetailsImpl;
import org.ksga.springboot.springsecuritydemo.service.AccountService;
import org.ksga.springboot.springsecuritydemo.utils.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/account")
public class AccountRestController {

    @Autowired
    private AccountService accountService;

    @PatchMapping("/{id}/close")
    public Response<String> closeAccount(@PathVariable long id,
                                         @ApiIgnore @CurrentUser UserDetailsImpl userDetails) {
        if (userDetails != null) {
            if (userDetails.getId() == id) {
                boolean closed = accountService.closeAccount(id);
                if (closed) {
                    return Response
                            .<String>ok()
                            .setErrors("Account Close Successfully.");
                } else {
                    return Response
                            .<String>ok()
                            .setErrors("Account Close Fail.");
                }
            }
        }
        return Response
                .<String>unauthorized()
                .setErrors("Unauthorized");
    }

    @PatchMapping("/{id}/update-profile")
    public Response<String> updateAccount(@PathVariable long id,
                                          @RequestBody UserRequest userRequest,
                                          @ApiIgnore @CurrentUser UserDetailsImpl userDetails) {
        if (userDetails != null) {
            if (userDetails.getId() == id) {
                UserDto userDto = new UserDto()
                        .setId(id)
                        .setImageUrl(userRequest.getImageUrl())
                        .setUsername(userRequest.getUsername())
                        .setFullname(userRequest.getFullname());
                userDto.setFullname("This is my full ");
                boolean updated = accountService.updateAccount(userDto);
                if (updated) {
                    return Response
                            .<String>ok()
                            .setPayload("Update Profile Success");
                } else {
                    return Response
                            .<String>exception()
                            .setPayload("Update Profile Fail");
                }
            } else {
                return Response
                        .<String>unauthorized()
                        .setPayload("Update Profile Fail. UnAuthorized");
            }
        }
        return Response
                .<String>unauthorized()
                .setPayload("Update Profile Fail. UnAuthorized");
    }

}
