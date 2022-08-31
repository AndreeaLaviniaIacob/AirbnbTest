import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class MainTest {
    @Before
    public void openApp() throws InterruptedException {

        open("https://www.airbnb.com/");
        Thread.sleep(2000);
    }

    @Test
    public void selectSomething() throws InterruptedException {
        $(By.xpath("//button[@class='f19g2zq0 dir dir-ltr']//div[@class='f1xx50dm dir dir-ltr']")).click();
        Thread.sleep(1000);

        $(By.id("bigsearch-query-location-input")).sendKeys("Spain");
        Thread.sleep(1000);
        $(By.xpath("//div[@role='option']/div[@class='_1825a1k']/div[@class='_r1t6ga']")).shouldHave(Condition.exactText("Spain")).click();
        Thread.sleep(1000);

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy ");
        Date date = new Date();
        String currentDate = dateFormat.format(date);
        System.out.println("Today date is: " + currentDate);

        Calendar advanceday = Calendar.getInstance();
        advanceday.setTime(new Date());
        advanceday.add(Calendar.DATE, 4);
        String output = dateFormat.format(advanceday.getTime());
        System.out.println("Date for 4 days in advance from today is: " + output);
        Thread.sleep(1000);

        Calendar yesterday = Calendar.getInstance();
        yesterday.setTime(new Date());
        yesterday.add(Calendar.DATE, -1);
        String yesterdayDate = dateFormat.format(yesterday.getTime());
        System.out.println("Yesterday date is: " + yesterdayDate);

        $(By.xpath("//div[@data-testid='calendar-day-" + currentDate.trim() + "']")).click();
        Thread.sleep(500);
        $(By.xpath("//div[@data-testid='calendar-day-" + output.trim() + "']")).click();
        Thread.sleep(500);

        $(By.xpath("//div[@data-testid='calendar-day-" + yesterdayDate.trim() + "']")).shouldHave(Condition.attributeMatching("data-is-day-blocked", "true"));

        $(By.id("tab--tabs--1")).shouldHave(Condition.exactText("I'm flexible")).click();
        Thread.sleep(3000);

        $(By.id("flexible_trip_lengths-weekend_trip")).shouldHave(Condition.exactText("Weekend")).click();
        Thread.sleep(3000);

        $(By.id("tab--tabs--0")).shouldHave(Condition.exactText("Choose dates")).click();
        Thread.sleep(3000);

        $(By.xpath("//button[@data-testid='structured-search-input-search-button']")).click();
        Thread.sleep(2000);

        $(By.xpath("//div[@data-test-class='ExploreSectionWrapper']//div[@class=' dir dir-ltr']/div/div[1]")).hover();
        Thread.sleep(1000);

        $(By.xpath("//button[@data-veloute='map/markers/BasePillMarker']/div[@class=' dir dir-ltr']/div")).shouldHave(Condition.attribute("style", "background-color: var(--f-k-smk-x); border-radius: 28px; box-shadow: rgba(255, 255, 255, 0.18) 0px 0px 0px 1px inset, rgba(0, 0, 0, 0.18) 0px 2px 4px; color: var(--f-mkcy-f); height: 28px; padding: 0px 8px; position: relative; transform: scale(1); transform-origin: 50% 50%;"));
        Thread.sleep(1000);
        $(By.xpath("//button[@data-veloute='map/markers/BasePillMarker']/div[@class=' dir dir-ltr']/div")).shouldHave(Condition.attribute("style", "background-color: var(--f-k-smk-x); border-radius: 28px; box-shadow: rgba(255, 255, 255, 0.18) 0px 0px 0px 1px inset, rgba(0, 0, 0, 0.18) 0px 2px 4px; color: var(--f-mkcy-f); height: 28px; padding: 0px 8px; position: relative; transform: scale(1); transform-origin: 50% 50%;")).click();
        Thread.sleep(2000);

        $(By.xpath("//div[@aria-describedby='carousel-description']//button[@aria-label='Close']")).click();
        Thread.sleep(1000);

        $(By.xpath("//span[@class='t1psh3xd dir dir-ltr']")).shouldHave(Condition.exactText("Filters")).click();
        Thread.sleep(2000);

        ElementsCollection listOfPlaces = $$(By.xpath("//div[@class='c1mdj8k7 cwko1i1 dir dir-ltr']/div"));
        for (int index = 0; index < listOfPlaces.size(); index++) {
            if (listOfPlaces.get(index).getText().contains("Entire place") || listOfPlaces.get(index).getText().contains("Private room")) {
                listOfPlaces.get(index).click();
            }
        }

        ElementsCollection listOfLanguages = $$(By.xpath("//div[@class='c1mdj8k7 cwko1i1 dir dir-ltr']/div"));
        for (int index = 0; index < listOfLanguages.size(); index++) {
            //System.out.println(listOfPlaces.get(index).getText());
            if (listOfLanguages.get(index).getText().contains("Japanese")) {
                listOfLanguages.get(index).click();
            }
        }
        Thread.sleep(2000);

        $(By.xpath("//footer/a[@aria-live='polite']")).click();
        Thread.sleep(3000);

        $(By.xpath("//div[@data-plugin-in-point-id='EXPLORE_STRUCTURED_PAGE_TITLE']//div[@aria-hidden='true']")).shouldHave(Condition.exactText("3")).is(visible);

        int pageNumber;
        ElementsCollection pages = $$(By.xpath("//nav[@aria-label='Search results pagination']/div/a"));
        pageNumber = pages.size();
        System.out.println(pageNumber);
        int sum = 0;
        for (int index = 0; index < pageNumber; index++) {
            ElementsCollection collection = $$(By.xpath("//div[@class='giajdwt g14v8520 dir dir-ltr']/div"));
            Assert.assertTrue(collection.get(0).exists());

            Thread.sleep(2000);

            int count = 0;
            count = $$(By.xpath("//div[@class='giajdwt g14v8520 dir dir-ltr']/div")).size();

            sum = sum + count;

            if ($(By.xpath("//a[@aria-label='Next']")).exists()) {
                $(By.xpath("//a[@aria-label='Next']")).click();
            } else {
                break;
            }
        }
        System.out.println("Number of stays is: " + sum);
        String stringFiltered = $(By.xpath("//h1[@elementtiming='LCP-target']/span")).getText();
        String number = stringFiltered.substring(0, stringFiltered.indexOf(" "));
        int numberToCompare = Integer.valueOf(number);
        if (numberToCompare == sum) {
            System.out.println("Number of properties filtered correspond to the ones displayed");
        } else throw new InterruptedException("Numbers don't match");
    }

    @After
    public void closeWindow() {
        closeWebDriver();
    }
}
