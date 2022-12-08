package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {

    private SelenideElement heading = $("[data-test-id=dashboard]");
    private ElementsCollection cards = $$(".list__item");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    private SelenideElement reloadButton = $("[data-test-id=reload]");

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public String getCardIdByIndex(int index) {
        return cards.get(index).$("div").getAttribute("data-test-id");
    }


    public int getCardBalance(String id) {
        for (int i = 0; i < cards.size(); i++) {
            if (getCardIdByIndex(i).equals(id)) {
                String text = cards.get(i).text();
                return extractBalance(text);
            }
        }
        throw new IllegalArgumentException("No element with id = " + id);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public TransferPage transferTo(int cardIndex) {
        cards.get(cardIndex).$("[data-test-id=action-deposit]").click();
        return new TransferPage();
    }
}
