package ru.netology.web.data;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.Random;

public class DataHelper {

    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    @Value
    public static class VerificationCode {
        String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    @Value
    public static class TransferData {
        int sum;
        String fromCard;
    }

    public static int getRandomSum(int max) {
        Random rn = new Random();
        int sum = rn.nextInt(max);
        return sum;
    }

    public static TransferData getTransferDataFromFirstToSecond(int sum) {
        return new TransferData(sum, "5559 0000 0000 0001");
    }

    public static TransferData getTransferDataFromSecondToFirst(int sum) {
        return new TransferData(sum, "5559 0000 0000 0002");
    }
}
