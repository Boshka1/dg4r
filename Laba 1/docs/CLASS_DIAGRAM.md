# Лабораторная работа №1. Пункт 10: Диаграмма классов UML

## Диаграмма классов предметной области (Вариант 8: Поставщик автозапчастей)

Ниже представлена полная диаграмма классов, отображающая структуру созданных классов, модификаторы доступа (`+` public, `-` private, `#` protected), типы данных, методы валидации, перегруженные конструкторы и связь наследования.

---

### 1. UML Диаграмма (Mermaid)

```mermaid
classDiagram
    class ValidationUtils {
        <<utility>>
        -ValidationUtils()
        +isBlank(String value)$ boolean
        +matchesRegex(String value, String regex)$ boolean
        +checkLength(String value, int min, int max)$ boolean
    }

    class SupplierShort {
        #int id
        #String name
        #String inn
        #String phone
        -String INN_REGEX$
        -String PHONE_REGEX$
        +SupplierShort(int id, String name, String inn, String phone)
        +SupplierShort(SupplierShort other)
        +SupplierShort(String shortCsv)
        +validateId(int id)$ boolean
        +validateName(String name)$ boolean
        +validateInn(String inn)$ boolean
        +validatePhone(String phone)$ boolean
        +getId() int
        +setId(int id) void
        +getName() String
        +setName(String name) void
        +getInn() String
        +setInn(String inn) void
        +getPhone() String
        +setPhone(String phone) void
        +toString() String
        +equals(Object o) boolean
        +hashCode() int
    }

    class Supplier {
        -String ogrn
        -String email
        -String address
        -String contactPerson
        -String OGRN_REGEX$
        -String EMAIL_REGEX$
        -String CONTACT_PERSON_REGEX$
        +Supplier(int id, String name, String inn, String ogrn, String phone, String email, String address, String contactPerson)
        +Supplier(SupplierShort supplierShort, String ogrn, String email, String address, String contactPerson)
        +Supplier(String data)
        -initFromCsv(String csvLine) void
        -initFromJson(String json) void
        -parseSimpleJson(String json)$ Map
        +validateOgrn(String ogrn)$ boolean
        +validateEmail(String email)$ boolean
        +validateAddress(String address)$ boolean
        +validateContactPerson(String contactPerson)$ boolean
        +getOgrn() String
        +setOgrn(String ogrn) void
        +getEmail() String
        +setEmail(String email) void
        +getAddress() String
        +setAddress(String address) void
        +getContactPerson() String
        +setContactPerson(String contactPerson) void
        +toString() String
        +toShortString() String
        +equals(Object o) boolean
        +hashCode() int
    }

    Supplier --|> SupplierShort : Наследование (extends)
    Supplier ..> ValidationUtils : Зависимость (использование)
    SupplierShort ..> ValidationUtils : Зависимость (использование)
```

---

### 2. Описание элементов диаграммы

1. **`SupplierShort` (Базовый класс / Краткая модель):**
   * Хранит ключевые идентифицирующие атрибуты поставщика (`id`, `name`, `inn`, `phone`).
   * Поля объявлены с модификатором `#` (`protected`), что делает их доступными в классе-наследнике без нарушения инкапсуляции от внешнего мира.
   * Содержит статические методы валидации базовых полей.
   * Предоставляет перегруженные конструкторы: базовый, копирующий (из другого `SupplierShort` или `Supplier`) и из краткой строки CSV.

2. **`Supplier` (Класс-наследник / Полная модель):**
   * Наследует `id`, `name`, `inn`, `phone` и их геттеры/сеттеры/валидаторы от `SupplierShort`.
   * Добавляет приватные поля (`-` `private`): `ogrn`, `email`, `address`, `contactPerson`.
   * В конструкторах вызывает `super(...)` для бесконфликтной и единой инициализации базовой части.
   * Реализует нетривиальные перегрузки конструктора для парсинга полного CSV и JSON.
   * Переопределяет `toString()` (полный отчет), `toShortString()` (делегирование в суперкласс) и `equals`/`hashCode`.

3. **`ValidationUtils` (Утилитный класс):**
   * Имеет приватный конструктор, запрещающий создание объектов.
   * Содержит статические методы (`$`), устраняющие дублирование логики регулярных выражений и проверки строк в классах предметной области.
