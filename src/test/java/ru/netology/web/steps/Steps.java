package ru.netology.web.steps;

import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.TransferPage;
import ru.netology.web.page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Steps {
    private DashboardPage dashboardPage;

    @Пусть("пользователь залогинен с именем {string} и паролем {string}")
    public void loginUser(String name, String password) {
        open("http://localhost:9999");
        LoginPage loginPage = new LoginPage();
        DataHelper.AuthInfo authInfo = new DataHelper.AuthInfo(name, password);
        VerificationPage verificationPage = loginPage.validLogin(authInfo);
        DataHelper.VerificationCode verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @Когда("пользователь переводит {int} рублей с карты с номером {string} на свою 1 карту с главной страницы")
    public void transferMoney(int amount, String cardFrom) {
        TransferPage transferPage = dashboardPage.transferTo(0);
        DataHelper.TransferData transferData = new DataHelper.TransferData(amount, cardFrom);
        dashboardPage = transferPage.deposit(transferData);
    }

    @Тогда("баланс его 1 карты из списка на главной странице должен стать {int} рублей")
    public void verifyBalance(int amountAfterTransfer) {
        String idFirstCard = dashboardPage.getCardIdByIndex(0);
        int balanceFirstCard = dashboardPage.getCardBalance(idFirstCard);
        assertEquals(amountAfterTransfer, balanceFirstCard);
    }
}
