// Импортируем наши классы моделей из пакета models
import models.Supplier;
import models.SupplierShort;

/**
 * Главный запускаемый класс программы (точка входа).
 * Демонстрирует работу всей иерархии, конструкторов, валидации и исключений.
 */
public class Main {

    // Метод main - это стартовая точка запуска любой Java-программы:
    // public - открыт для JVM
    // static - вызывается без создания объекта Main
    // void - ничего не возвращает
    // String[] args - аргументы командной строки
    public static void main(String[] args) {

        // Выводим приветственные строки в консоль
        System.out.println("=== Лабораторная работа №1 (ПИС) ===");
        System.out.println("Вариант 8: Фирма по продаже запчастей");
        System.out.println("Студент: Дражин Н.О.\n");

        // ---------------------------------------------------------------------
        // ШАГ 1: Создание полного объекта Supplier через основной конструктор
        // ---------------------------------------------------------------------
        Supplier fullSupplier = new Supplier(
                1,                                      // id
                "ООО \"Авто-Лидер\"",                   // name
                "7701234567",                           // inn (10 цифр)
                "1027700132195",                        // ogrn (13 цифр)
                "+7 (999) 123-45-67",                   // phone
                "sales@auto-leader.ru",                 // email
                "г. Москва, ул. Автомобильная, д. 10",  // address
                "Иванов Иван Иванович"                  // contactPerson
        );

        // ---------------------------------------------------------------------
        // ШАГ 2: Создание краткой версии SupplierShort на базе полного объекта
        // (демонстрация полиморфизма: предок принимает ссылку на потомка)
        // ---------------------------------------------------------------------
        SupplierShort shortFromFull = new SupplierShort(fullSupplier);

        // ---------------------------------------------------------------------
        // ШАГ 3: Создание полного объекта Supplier путем расширения краткого
        // ---------------------------------------------------------------------
        Supplier extendedSupplier = new Supplier(
                shortFromFull,
                "1027700132195",
                "support@auto-leader.ru",
                "г. Москва, ул. Складская, д. 5",
                "Смирнова Анна"
        );

        // ---------------------------------------------------------------------
        // ШАГ 4: Демонстрация принципов ООП и полиморфизма
        // ---------------------------------------------------------------------
        System.out.println("1. ДЕМОНСТРАЦИЯ ИЕРАРХИИ НАСЛЕДОВАНИЯ:");
        System.out.println("   Класс-родитель : " + SupplierShort.class.getSimpleName());
        System.out.println("   Класс-наследник: " + Supplier.class.getSimpleName());
        // Оператор instanceof проверяет, принадлежит ли объект к типу (или его наследнику)
        System.out.println("   Supplier является экземпляром SupplierShort? " + (fullSupplier instanceof SupplierShort));

        // Вывод полной карточки поставщика (автоматически вызывает fullSupplier.toString())
        System.out.println("\n2. ПОЛНОЕ ПРЕДСТАВЛЕНИЕ ОБЪЕКТА (toString):");
        System.out.println(fullSupplier);

        // Вывод краткой версии через специальный метод toShortString()
        System.out.println("\n3. КРАТКОЕ ПРЕДСТАВЛЕНИЕ ИЗ ПОЛНОГО ОБЪЕКТА (toShortString):");
        System.out.println("   " + fullSupplier.toShortString());

        // Вывод чистого объекта краткой модели SupplierShort
        System.out.println("\n4. ПРЕДСТАВЛЕНИЕ ОБЪЕКТА SUPPLIERSHORT (toString):");
        System.out.println("   " + shortFromFull);

        // ---------------------------------------------------------------------
        // ШАГ 5: Проверка перегруженных конструкторов (CSV и JSON)
        // ---------------------------------------------------------------------
        System.out.println("\n5. ПРОВЕРКА ПЕРЕГРУЗКИ КОНСТРУКТОРОВ:");

        // Парсинг строки CSV
        String csv = "2;АО \"ДетальКомплект\";2310123456;1032304152637;+7 (861) 222-33-44;info@detal.ru;г. Краснодар, ул. Северная, д. 45;Сидоров Алексей";
        Supplier sFromCsv = new Supplier(csv);
        System.out.println("   Объект из строки CSV : " + sFromCsv.toShortString());

        // Парсинг строки JSON
        String json = "{"
                + "\"id\": 3,"
                + "\"name\": \"ООО \\\"МоторПлюс\\\"\","
                + "\"inn\": \"7801987654\","
                + "\"ogrn\": \"1057812345678\","
                + "\"phone\": \"88005553535\","
                + "\"email\": \"support@motorplus.ru\","
                + "\"address\": \"г. Санкт-Петербург, Московский пр., д. 100\","
                + "\"contactPerson\": \"Васильев Николай\""
                + "}";
        Supplier sFromJson = new Supplier(json);
        System.out.println("   Объект из строки JSON: " + sFromJson.toShortString());

        // ---------------------------------------------------------------------
        // ШАГ 6: Проверка работы системы валидации и защиты
        // ---------------------------------------------------------------------
        System.out.println("\n6. ПРОВЕРКА ВАЛИДАЦИИ И ЗАЩИТЫ (попытка создать невалидный объект):");
        try {
            // Передаем некорректный ИНН ("12345")
            Supplier invalidSupplier = new Supplier(
                    99,
                    "ООО \"ТестОшибки\"",
                    "12345", // ОШИБКА: ИНН должен быть 10 или 12 цифр
                    "1027700132195",
                    "+79991112233",
                    "test@mail.ru",
                    "г. Тестовый, ул. Тестовая, 1",
                    "Тестов Тест"
            );
            System.out.println("   ОШИБКА ЗАЩИТЫ: невалидный объект был создан!");
        } catch (IllegalArgumentException e) {
            // Перехватываем исключение и печатаем текст ошибки
            System.out.println("   УСПЕШНО ЗАБЛОКИРОВАНО: " + e.getMessage());
        }

        // ---------------------------------------------------------------------
        // ШАГ 7: Сравнение объектов на равенство (equals и hashCode)
        // ---------------------------------------------------------------------
        System.out.println("\n7. СРАВНЕНИЕ ОБЪЕКТОВ (equals & hashCode):");
        System.out.println("   fullSupplier равен extendedSupplier? " + fullSupplier.equals(extendedSupplier));
        System.out.println("   fullSupplier равен sFromCsv? " + fullSupplier.equals(sFromCsv));
        System.out.println("   hashCode fullSupplier     : " + fullSupplier.hashCode());
        System.out.println("   hashCode extendedSupplier : " + extendedSupplier.hashCode());
    }
}
