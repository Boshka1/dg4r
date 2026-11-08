// Объявление пакета: говорит Java, что этот файл лежит в папке "utils"
package utils;

// Импортируем класс Pattern для работы с регулярными выражениями (поиском и проверкой по шаблону)
import java.util.regex.Pattern;

/**
 * Утилитный класс для валидации (проверки) данных.
 * Ключевое слово final означает, что от этого класса нельзя наследоваться.
 */
public final class ValidationUtils {

    // Приватный конструктор: запрещает создание объектов через new ValidationUtils().
    // Утилитные классы содержат только статические методы и не должны иметь экземпляров.
    private ValidationUtils() {
    }

    /**
     * Метод проверки: пустая ли строка (или null).
     * public - доступен из любого другого класса
     * static - метод класса (вызывается без создания объекта: ValidationUtils.isBlank(...))
     * boolean - возвращает true (истина) или false (ложь)
     * String value - входной параметр (строка, которую проверяем)
     */
    public static boolean isBlank(String value) {
        // Проверяем: если ссылка равна null (значение не задано)
        // ИЛИ (.trim() убирает пробелы по краям, а .isEmpty() проверяет, пуста ли оставшаяся строка)
        return value == null || value.trim().isEmpty();
    }

    /**
     * Метод проверки строки по регулярному выражению (шаблону).
     * String value - проверяемая строка (например телефон или ИНН)
     * String regex - регулярное выражение (правило/шаблон формата)
     */
    public static boolean matchesRegex(String value, String regex) {
        // Если строка пустая или null - проверка сразу провалена (false)
        if (isBlank(value)) {
            return false;
        }
        // Pattern.matches сравнивает строку с регулярным выражением.
        // value.trim() удаляет случайные пробелы в начале и конце перед проверкой
        return Pattern.matches(regex, value.trim());
    }

    /**
     * Метод проверки длины строки (должна укладываться в диапазон от min до max).
     * int min - минимальная допустимая длина символов
     * int max - максимальная допустимая длина символов
     */
    public static boolean checkLength(String value, int min, int max) {
        // Если строка пустая или null - возвращаем false
        if (isBlank(value)) {
            return false;
        }
        // Получаем длину строки без лишних пробелов по краям
        int length = value.trim().length();

        // Проверяем, что длина больше или равна min И меньше или равна max
        return length >= min && length <= max;
    }
}
