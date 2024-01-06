package com.appsdeveloperblog.app.ws.utils;

import com.appsdeveloperblog.app.ws.exceptions.MissingRequiredFieldException;
import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;
import com.appsdeveloperblog.app.ws.ui.model.response.ErrorMessages;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

/*
 *
 * @author: Sandeep prajapati
 *
 */
public class UserProfileUtils {

    public void validateRequiredField(UserDTO userDTO) throws MissingRequiredFieldException {

        if(userDTO.getFirstName() == null ||
                userDTO.getFirstName().isEmpty() ||
                userDTO.getLastName() == null ||
                userDTO.getLastName().isEmpty() ||
                userDTO.getEmail() == null ||
                userDTO.getEmail().isEmpty() ||
                userDTO.getPassword() == null ||
                userDTO.getPassword().isEmpty()
        ) {
            throw new MissingRequiredFieldException(
                    ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }
    }

    private final Random RANDOM = new SecureRandom();

    private String generateRandomString(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            String ALPHA_NUMERIC_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
            int character = (int)(RANDOM.nextDouble()* ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return new String(builder);
    }
    public String generateUserId() {
        return UUID.randomUUID().toString().replace("-","");
    }

    public String getSalt(int length){
        return generateRandomString(length);
    }


    public String generateSecurePassword(String password, String salt) {
        String returnValue = null;

        byte[] securePassword = hash(password.toCharArray(), salt.getBytes());
        returnValue = Base64.getEncoder().encodeToString(securePassword);

        return returnValue;
    }

    public byte[] hash(char[] password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, 10000, 256);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }
}
