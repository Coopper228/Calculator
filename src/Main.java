import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Выберите режим:");
        System.out.println("1) Тестовые выражения");
        System.out.println("2) Ручной ввод");
        System.out.print("Ваш выбор: ");
        String mode = scanner.nextLine();

        switch (mode) {
            case "1":
                String[] testExpressions = {
                        "1 + 2",
                        "10 - 5",
                        "10 * 5",
                        "4 / 2",
                        "a + b",
                        "1.2 + 5,1",
                        "Hello World",
                        "10 / 0",
                        "0 + 10",
                        "Можно даже пробелов сколько угодно вставить.",
                        "     1       *          5   ",
                        "2 2 * 6",
                        "2 * 6   2",
                        "+1",
                        "2+",
                        "11 / 1",
                        "8 + 8 + 8"
                };

                for (String expr : testExpressions) {
                    try {
                        String result = calc(expr);
                        System.out.println("Математическое выражение: " + expr + " Результат: " + result);
                    } catch (Exception e) {
                        System.out.println("Математическое выражение: " + expr + " Ошибка: " + e.getMessage());
                    }
                }
                break;
            case "2":
                System.out.print("Математическое выражение: ");
                String input = scanner.nextLine();

                try {
                    String result = calc(input);
                    System.out.println("Результат: " + result);
                } catch (Exception e) {
                    System.err.println("Ошибка: " + e.getMessage());
                }
                break;
            default:
                System.err.println("Ошибка: неверный выбор режима");
                break;
        }
    }

    public static String calc(String input) {
        String inputString = input.trim();
        int operatorMark = -1;
        char operator = 0;
        int operand1;
        int operand2;

        // Проверка на недопустимые символы
        for (int i = 0; i < inputString.length(); i++) {
            char c = inputString.charAt(i);
            if (!(Character.isDigit(c) || c == '+' || c == '-' || c == '*' || c == '/' || c == ' ')) {
                throw new InvalidInputException("Обнаружен недопустимый символ: '" + c + "'. Разрешены только цифры, пробелы и операторы +, -, *, /");
            }
        }

        // Поиск оператора
        for (int i = 0; i < inputString.length(); i++) {
            char c = inputString.charAt(i);
            if (c == '+' || c == '-' || c == '*' || c == '/') {
                if (operatorMark != -1) {
                    throw new InvalidExpressionException("Калькулятор может выполнять вычисления только с двумя операндами и одним оператором. Обнаружено несколько операторов: '" + operator + "' и '" + c + "'");
                }
                operatorMark = i;
                operator = c;
            }
        }

        if (operatorMark == -1) {
            throw new InvalidExpressionException("Введите пожалуйста корректное математическое выражение. Калькулятор поддерживает сложение, вычитание, умножение, а также деление.");
        }

        // Извлечение первого операнда
        String temp = "";
        boolean numberStarted = false;
        for (int i = operatorMark - 1; i >= 0; i--) {
            char c = inputString.charAt(i);
            if (Character.isDigit(c)) {
                temp = c + temp;
                numberStarted = true;
            } else if (c == ' ') {
                if (numberStarted) {
                    throw new InvalidExpressionException("Пробел между числами");
                }
            } else {
                break;
            }
        }

        if (temp.isEmpty()) {
            throw new InvalidExpressionException("Нет первого операнда");
        }
        operand1 = Integer.parseInt(temp);
        if (operand1 < 1 || operand1 > 10) {
            throw new NumberOutOfRangeException("Операнды (числа) должны быть от 1 до 10 включительно. Твой первый операнд: " + operand1);
        }

        // Извлечение второго операнда
        temp = "";
        numberStarted = false;
        for (int i = operatorMark + 1; i < inputString.length(); i++) {
            char c = inputString.charAt(i);
            if (Character.isDigit(c)) {
                temp += c;
                numberStarted = true;
            } else if (c == ' ') {
                if (numberStarted) {
                    throw new InvalidExpressionException("Пробел между числами");
                }
            } else {
                break;
            }
        }

        if (temp.isEmpty()) {
            throw new InvalidExpressionException("Нет второго операнда");
        }
        operand2 = Integer.parseInt(temp);
        if (operand2 < 1 || operand2 > 10) {
            throw new NumberOutOfRangeException("Операнды (числа) должны быть от 1 до 10 включительно. Твой второй операнд: " + operand2);
        }

        // Вычисление результата
        return switch (operator) {
            case '+' -> String.valueOf(operand1 + operand2);
            case '-' -> String.valueOf(operand1 - operand2);
            case '*' -> String.valueOf(operand1 * operand2);
            case '/' -> String.valueOf(operand1 / operand2);
            default -> throw new IllegalStateException("Неожиданный оператор: " + operator);
        };
    }

    // Пользовательские исключения
    static class InvalidInputException extends RuntimeException {
        public InvalidInputException(String message) {
            super(message);
        }
    }

    static class InvalidExpressionException extends RuntimeException {
        public InvalidExpressionException(String message) {
            super(message);
        }
    }

    static class NumberOutOfRangeException extends RuntimeException {
        public NumberOutOfRangeException(String message) {
            super(message);
        }
    }
}