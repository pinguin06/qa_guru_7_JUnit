package tests;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static java.util.Arrays.asList;

public class SearchFilms {

    @ValueSource(strings = {"green", "drama"})
    @ParameterizedTest(name = "При поиске фильма по запросу {0} в результатах отображается текст {0}")
    void TestValueSource(String testData) {
        Selenide.open("https://www.film.ru");
        $("#quick_search_input").setValue(testData).pressEnter();
        $$("#movies_list").find(text(testData)).shouldBe(visible);
    }


    @CsvSource(value = {
            "green, Green Book",
            "drama, The Dramatics: A Comedy"
    })
    @ParameterizedTest(name = "При поиске фильма по запросу {0} в результатах отображается текст {1}")
    void TestCsvSource(String searchData, String expectedResult) {
        Selenide.open("https://www.film.ru");
        $("#quick_search_input").setValue(searchData).pressEnter();
        $$("#movies_list").find(text(expectedResult)).shouldBe(visible);
    }

    static Stream<Arguments> TestVeryComplexDataProvider() {
        return Stream.of(
                Arguments.of("green", asList("The Green Knight")),
                Arguments.of("drama", asList("The Dramatics: A Comedy"))
        );
    }

    @MethodSource(value = "TestVeryComplexDataProvider")
    @ParameterizedTest(name = "При поиске фильма по запросу {0} в результатах отображается текст {1}")
    void TestMethodSourse(String searchData, List<String> expectedResult) {
        Selenide.open("https://www.film.ru");
        $("#quick_search_input").setValue(searchData).pressEnter();
        $$("#movies_list").shouldHave(CollectionCondition.texts(expectedResult));
    }

    @EnumSource(Films.class)
    @ParameterizedTest(name = "Проверка аннотации @EnumSourse")
    void enumTest(Films films) {
        Selenide.open("https://www.film.ru");
        $("#quick_search_input").setValue(films.desc).pressEnter();
        $$("#movies_list").find(text(films.desc)).shouldBe(visible);
    }

    public String toString() {
        return super.toString();
    }

}