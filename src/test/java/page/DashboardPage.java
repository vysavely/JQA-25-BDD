package page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;
import lombok.val;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static java.awt.SystemColor.text;

public class DashboardPage {
    private ElementsCollection cards = $$(".list__item div");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";
    private SelenideElement heading = $("[data-test-id=dashboard]");

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public int getCardBalance(DataHelper.CardInfo cardInfo) {
        var text = cards.findBy(text(cardInfo.getCardNumber().substring(15))).getText();
        return extractBalance(text);
    }

    public TransferPage selectCardToTransfer(DataHelper.CardInfo cardInfo) {
        cards.findBy(attribute("data-test-id", cardInfo.getTestId())).$("button").click();
        return new TransferPage();
    }

    public int extractBalance(String text) {
        var start = text.indexOf(balanceStart);
        var finish = text.indexOf(balanceFinish);
        var value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }
}