package ru.qatools.school.vorobushek.resources;

import net.anthavio.phanbedder.Phanbedder;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.qatools.school.vorobushek.service.DatabaseProvider;

import org.openqa.selenium.phantomjs.PhantomJSDriver;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


/**
 * Created by yurik
 * 23.11.14.
 */
public class ITIndexResourceTest {

    private static HashMap<String,List<String>> TestUser;

    static
    {
        TestUser = new HashMap<>();

        TestUser.put("IvanGoncharov"
                , Arrays.asList(
                "Фантазия напоминает паровую машину такой силы, что может и котел лопнуть."
                , "Мудрыми мы становимся, добыв умом и наблюдениями истины, приложив их к жизни и создав гармонию идей и действительности."
                , "Людям всегда будет что находить, открывать, изобретать, потому что сам источник этих знаний неисчерпаем."
                , "Жить для себя не жить, а пассивно существовать: нужно бороться."
                , "Невозможна жизнь без борьбы, в борьбе заключается счастье."
                , "Одновременно быть умным и в то же время искренним очень трудно, а особенно это касается чувств…"
                , "Не возможно исполнить до конца свой долг, не полюбивши его."
                , "В дружбе не существует ни рабов, ни повелителей. Она для равных."
                , "Он не верит в неизменную и вечную любовь, как и в домовых и нам этого делать не советует."));

        TestUser.put("FyodorDostoyevsky"
                , Arrays.asList(
                "Хозяин земли Русской ­ есть один лишь Русский, так было и всегда будет."
                ));

        TestUser.put("MikhailBulgakov"
                , Arrays.asList(
                "... никогда и ничего не просите! Никогда и ничего, и в особенности у тех, " +
                "кто сильнее вас. Сами предложат и сами всё дадут! " +
                "(Воланд) (Из романа «Мастер и Маргарита»)"
                ,"... он не был многословен на этот раз. Единственное, что он сказал, это, " +
                "что в числе человеческих пороков одним из самых главных он считает трусость. " +
                "(Афраний, об Иешуа) (Из романа «Мастер и Маргарита»)"
                ,"... разруха не в клозетах, а в головах. " +
                "(Из произведения «Собачье сердце»)"
                ,"А, может быть, Зинка взяла? " +
                "(Из произведения «Собачье сердце»)"
                ,"Бить будете, папаша?! " +
                "(Из произведения «Собачье сердце»)"
        ));

        TestUser.put("AlexanderPushkin"
                , Arrays.asList(
                "Кто жил и мыслил, тот не может в душе не презирать людей " +
                 "(Евгений Онегин. Роман в стихах)"
                ,"Под старость жизнь такая гадость…"
                ,"прощай, и если навсегда, то навсегда прощай. (Байрон)"
                ,"Он чином от ума избавлен."
                ,"Мы почитаем всех нулями, А единицами — себя."
                ,"Пора пришла, она влюбилась"
        ));

        TestUser.put("IvanTurgenev"
                , Arrays.asList(
                "Добро по указу - не добро."
                ,"Древние греки недаром говорили, что последний и высший дар богов человеку - чувство меры"
                ,"Женщина не только способна понять самопожертвование: она сама умеет пожертвовать собой."
                ,"Исключения только подтверждают правила."
        ));

    }

    private PhantomJSDriver driver;

    private String baseUrl = "http://localhost:8080";

    @Before
    public void openHomePage() {
        File phantomjs = Phanbedder.unpack();
        DesiredCapabilities dcaps = new DesiredCapabilities();
        dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, phantomjs.getAbsolutePath());
        driver = new PhantomJSDriver(dcaps);


    }

    @Test
    public void test() {

        driver.get(baseUrl);

        final String hello = driver.getPageSource();
        DatabaseProvider.getLogger().info(hello);

        DatabaseProvider.getLogger().info("TEST INDEX!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }
}
