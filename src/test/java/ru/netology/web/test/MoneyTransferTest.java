package ru.netology.web.test;

import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    @Test
    void shouldTransferMoneyFromFirstToSecondCard() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        String idFirstCard = dashboardPage.getCardIdByIndex(0);
        String idSecondCard = dashboardPage.getCardIdByIndex(1);
        int balanceFirstCard = dashboardPage.getCardBalance(idFirstCard);
        int balanceSecondCard = dashboardPage.getCardBalance(idSecondCard);
        var transferPage = dashboardPage.transferTo(1);
        int sum = DataHelper.getRandomSum(balanceFirstCard);
        dashboardPage = transferPage.deposit(DataHelper.getTransferDataFromFirstToSecond(sum));
        int balanceFirstCardAfterTransfer = dashboardPage.getCardBalance(idFirstCard);
        int balanceSecondCardAfterTransfer = dashboardPage.getCardBalance(idSecondCard);
        assertEquals(balanceFirstCard - sum, balanceFirstCardAfterTransfer);
        assertEquals(balanceSecondCard + sum, balanceSecondCardAfterTransfer);
    }

    @Test
    void shouldTransferMoneyFromSecondToFirstCard() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        String idFirstCard = dashboardPage.getCardIdByIndex(0);
        String idSecondCard = dashboardPage.getCardIdByIndex(1);
        int balanceFirstCard = dashboardPage.getCardBalance(idFirstCard);
        int balanceSecondCard = dashboardPage.getCardBalance(idSecondCard);
        var transferPage = dashboardPage.transferTo(0);
        int sum = DataHelper.getRandomSum(balanceSecondCard);
        dashboardPage = transferPage.deposit(DataHelper.getTransferDataFromSecondToFirst(sum));
        int balanceFirstCardAfterTransfer = dashboardPage.getCardBalance(idFirstCard);
        int balanceSecondCardAfterTransfer = dashboardPage.getCardBalance(idSecondCard);
        assertEquals(balanceFirstCard + sum, balanceFirstCardAfterTransfer);
        assertEquals(balanceSecondCard - sum, balanceSecondCardAfterTransfer);
    }

}
