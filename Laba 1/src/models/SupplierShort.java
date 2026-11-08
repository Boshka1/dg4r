// Объявление пакета: говорит компилятору, что класс находится в пакете "models"
package models;

// Импортируем наш утилитный класс ValidationUtils для переиспользования проверок
import utils.ValidationUtils;

// Импортируем служебный класс Objects для безопасного сравнения объектов (без риска NullPointerException)
import java.util.Objects;

/**
 * Базовый класс краткой версии сущности "Поставщик" (SupplierShort).
 * Служит суперклассом (родителем) для полного класса Supplier.
 */
public class SupplierShort {

    // =========================================================================
    // ПОЛЯ КЛАССА (ДАННЫЕ ОБЪЕКТА)
    // Модификатор protected означает: поле закрыто от внешнего мира (инкапсуляция),
    // но доступно напрямую внутри классов-наследников (например, в классе Supplier).
    // =========================================================================

    protected int id;         // Уникальный числовой идентификатор поставщика (первичный ключ)
    protected String name;    // Название фирмы / юридического лица (строка текста)
    protected String inn;     // ИНН (10 цифр для юрлиц, 12 для ИП)
    protected String phone;   // Контактный номер телефона поставщика

    // Константы регулярных выражений (шаблонов):
    // private - доступны только внутри этого класса
    // static - одна общая константа на весь класс (не дублируется в каждом объекте)
    // final - значение константы нельзя изменить после объявления
    private static final String INN_REGEX = "^\\d{10}$|^\\d{12}$";
    private static final String PHONE_REGEX = "^(\\+7|8)[\\s\\-]?\\(?\\d{3}\\)?[\\s\\-]?\\d{3}[\\s\\-]?\\d{2}[\\s\\-]?\\d{2}$";

    // =========================================================================
    // КОНСТРУКТОРЫ КЛАССА
    // =========================================================================

    /**
     * Конструктор 1 (основной): принимает все 4 поля краткой версии.
     * Задает начальное состояние через вызовы сеттеров, чтобы сразу сработала валидация!
     */
    public SupplierShort(int id, String name, String inn, String phone) {
        setId(id);       // Устанавливаем ID через сеттер (проверяется, что id > 0)
        setName(name);   // Устанавливаем имя через сеттер (проверяется длина)
        setInn(inn);     // Устанавливаем ИНН через сеттер (проверяется формат)
        setPhone(phone); // Устанавливаем телефон через сеттер (проверяется формат)
    }

    /**
     * Конструктор 2 (копирующий): создает краткий объект из другого объекта SupplierShort
     * или его потомка Supplier (благодаря принципу полиморфизма).
     */
    public SupplierShort(SupplierShort other) {
        // Проверяем: если нам передали null вместо объекта, выбрасываем ошибку
        if (other == null) {
            throw new IllegalArgumentException("Ошибка: переданный объект не может быть null.");
        }
        // Копируем поля из переданного объекта в текущий через сеттеры
        setId(other.getId());
        setName(other.getName());
        setInn(other.getInn());
        setPhone(other.getPhone());
    }

    /**
     * Конструктор 3 (из строки CSV): разбирает строку вида "id;name;inn;phone".
     */
    public SupplierShort(String shortCsv) {
        // Проверяем, что строка не пустая
        if (shortCsv == null || shortCsv.trim().isEmpty()) {
            throw new IllegalArgumentException("Ошибка: строка CSV не может быть пустой.");
        }
        // Разрезаем строку на части по символу точки с запятой
        String[] parts = shortCsv.split(";");

        // Проверяем, что получилось ровно 4 кусочка данных
        if (parts.length != 4) {
            throw new IllegalArgumentException("Ошибка: ожидалось 4 поля через ';', получено: " + parts.length);
        }

        try {
            // Преобразуем первую текстовую часть в целое число (ID)
            setId(Integer.parseInt(parts[0].trim()));
        } catch (NumberFormatException e) {
            // Если в строке вместо цифры был текст (например "abc"), ловим ошибку
            throw new IllegalArgumentException("Ошибка: не удалось преобразовать ID в число: " + parts[0]);
        }

        // Заполняем остальные строковые поля
        setName(parts[1].trim());
        setInn(parts[2].trim());
        setPhone(parts[3].trim());
    }

    // =========================================================================
    // СТАТИЧЕСКИЕ МЕТОДЫ ВАЛИДАЦИИ (МЕТОДЫ КЛАССА)
    // Вызываются без создания объекта: SupplierShort.validateInn("7701234567")
    // =========================================================================

    // Проверка ID: должен быть строго положительным числом (> 0)
    public static boolean validateId(int id) {
        return id > 0;
    }

    // Проверка названия: не пустое, длина от 2 до 255 символов
    public static boolean validateName(String name) {
        return ValidationUtils.checkLength(name, 2, 255);
    }

    // Проверка ИНН: строка из 10 или 12 цифр
    public static boolean validateInn(String inn) {
        return ValidationUtils.matchesRegex(inn, INN_REGEX);
    }

    // Проверка телефона: формат номера телефона РФ
    public static boolean validatePhone(String phone) {
        return ValidationUtils.matchesRegex(phone, PHONE_REGEX);
    }

    // =========================================================================
    // ГЕТТЕРЫ И СЕТТЕРЫ (МЕТОДЫ ИНКАПСУЛЯЦИИ)
    // =========================================================================

    // Геттер: возвращает текущее значение поля id
    public int getId() {
        return id;
    }

    // Сеттер: меняет значение id, предварительно проверяя его валидность
    public void setId(int id) {
        if (!validateId(id)) {
            // Если валидация вернула false — выбрасываем исключение и прерываем операцию
            throw new IllegalArgumentException("Ошибка: ID должен быть > 0. Получено: " + id);
        }
        this.id = id; // this.id — поле объекта, id — аргумент метода
    }

    // Геттер: возвращает название фирмы
    public String getName() {
        return name;
    }

    // Сеттер: меняет название фирмы с валидацией
    public void setName(String name) {
        if (!validateName(name)) {
            throw new IllegalArgumentException("Ошибка: некорректное имя компании (от 2 до 255 символов).");
        }
        this.name = name.trim(); // сохраняем очищенное от концевых пробелов значение
    }

    // Геттер: возвращает ИНН
    public String getInn() {
        return inn;
    }

    // Сеттер: меняет ИНН с валидацией
    public void setInn(String inn) {
        if (!validateInn(inn)) {
            throw new IllegalArgumentException("Ошибка: некорректный ИНН (должен быть 10 или 12 цифр). Получено: " + inn);
        }
        this.inn = inn.trim();
    }

    // Геттер: возвращает номер телефона
    public String getPhone() {
        return phone;
    }

    // Сеттер: меняет номер телефона с валидацией
    public void setPhone(String phone) {
        if (!validatePhone(phone)) {
            throw new IllegalArgumentException("Ошибка: некорректный формат телефона. Получено: " + phone);
        }
        this.phone = phone.trim();
    }

    // =========================================================================
    // МЕТОДЫ КЛАССА OBJECT (ПЕРЕОПРЕДЕЛЕНИЕ)
    // =========================================================================

    /**
     * Метод toString: возвращает красивую краткую строку с описанием объекта.
     * Аннотация @Override сообщает компилятору, что мы переопределяем метод базового класса Object.
     */
    @Override
    public String toString() {
        // String.format формирует строку по шаблону: %d - число, %s - строка
        return String.format("SupplierShort [#%d]: %s | ИНН: %s | Тел: %s", id, name, inn, phone);
    }

    /**
     * Метод equals: сравнивает текущий объект (this) с переданным (o) на равенство.
     */
    @Override
    public boolean equals(Object o) {
        // Если сравниваем объект сам с собой (один и тот же адрес в памяти) - они равны
        if (this == o) return true;

        // Если передан null или объекты принадлежат разным классам - они не равны
        if (o == null || getClass() != o.getClass()) return false;

        // Приводим тип Object к типу SupplierShort (Downcasting)
        SupplierShort that = (SupplierShort) o;

        // Сравниваем уникальные поля: ID (через == для чисел) и ИНН (через Objects.equals для строк)
        return id == that.id && Objects.equals(inn, that.inn);
    }

    /**
     * Метод hashCode: возвращает числовой хэш объекта для хэш-таблиц.
     * Обязан вычисляться по тем же полям, что и метод equals (по id и inn)!
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, inn);
    }
}
