package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationAndAuthorizationTest {

    AuthenticationAndAuthorization authenticationAndAuthorization;

    @BeforeEach
    void setUp() throws UserNameAlreadyExistsException {
        authenticationAndAuthorization = new AuthenticationAndAuthorization();
        authenticationAndAuthorization.addUser("anna", "losen");
        authenticationAndAuthorization.addUser("berit", "123456");
        authenticationAndAuthorization.addUser("kalle", "password");

        String userAnnaToken = authenticationAndAuthorization.userList.get("anna").getToken();
        String userBeritToken = authenticationAndAuthorization.userList.get("berit").getToken();
        String userKalleToken = authenticationAndAuthorization.userList.get("kalle").getToken();

        authenticationAndAuthorization.giveUserRightsInSystem(userAnnaToken, "ACCOUNT", List.of("READ"));
        authenticationAndAuthorization.giveUserRightsInSystem(userBeritToken, "ACCOUNT", List.of("READ", "WRITE"));
        authenticationAndAuthorization.giveUserRightsInSystem(userKalleToken, "ACCOUNT", List.of("READ"));

    }

    @Test
    void test_logg_in_success() throws WrongTokenReturnException {
        String tokenInReturn = authenticationAndAuthorization.loggIn("anna", "losen");

        assertEquals(authenticationAndAuthorization.userList.get("anna").getToken(), tokenInReturn);
    }

    @Test
    void test_logg_in_fail_because_of_wrong_token_return() {
        WrongTokenReturnException wrongTokenReturnException = assertThrows(WrongTokenReturnException.class, () ->
                authenticationAndAuthorization.loggIn("anna", "losenn"));

        assertEquals("No token found", wrongTokenReturnException.getMessage());
    }

    @Test
    void test_check_if_token_is_valid_success() throws WrongTokenReturnException {
        String tokenInReturn = authenticationAndAuthorization.loggIn("anna", "losen");
        boolean isTokenValid = authenticationAndAuthorization.validateToken(tokenInReturn);

        assertTrue(isTokenValid);
    }

    @Test
    void test_check_users_rights_in_program_success() throws WrongTokenReturnException, WrongResourceNameException {
        String tokenInReturn = authenticationAndAuthorization.loggIn("berit", "123456");
        List<String> userRights = authenticationAndAuthorization.getUsersRightsInProgram(tokenInReturn, "ACCOUNT");

        assertEquals(List.of("READ", "WRITE"), userRights);
    }

    @Test
    void test_check_users_rights_fail_because_of_wrong_resource_name() throws WrongTokenReturnException {
        String tokenInReturn = authenticationAndAuthorization.loggIn("berit", "123456");
        WrongResourceNameException wrongResourceNameException = assertThrows(WrongResourceNameException.class, () ->
                authenticationAndAuthorization.getUsersRightsInProgram(tokenInReturn, "WrongResourceName"));

        assertEquals("No resource with that name found", wrongResourceNameException.getMessage());
    }

    @Test
    void test_add_new_user_fail_because_username_already_exists() {
        UserNameAlreadyExistsException userNameAlreadyExistsException = assertThrows(UserNameAlreadyExistsException.class, () ->
                authenticationAndAuthorization.addUser("anna", "uniquePassword"));

        assertEquals("Username already exists", userNameAlreadyExistsException.getMessage());
    }
}
