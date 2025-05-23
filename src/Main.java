public class Main {

    public static void main(String[] args) {
        // Я не уверен, что без этого метода нельзя обойтись, но с ним появляется кнопка Run Main.java
        // Всё, что нужно для проверки работы calc - вызвать его в этом методе

    }

    public static String calc(String input){
        String inputString = input.trim();
        int operatorMark = -1;                              // Позиция операнда в изначальной строке
        char operator = 0;                                  // Оператор
        int operand1;                                       // Первый операнд
        int operand2;                                       // Второй операнд

        //Проверка на всякий мусор
        for (int i = 0; i < inputString.length(); i++) {
            char c = inputString.charAt(i);
            if (!(Character.isDigit(c) || c == '+' || c == '-' || c == '*' || c == '/' || c == ' ')) {
                throw new IllegalArgumentException("Обнаружен недопустимый символ: '" + c + "'. Дорогой Юзер, разрешены только цифры, пробелы и операторы +, -, *, /");
            }
        }

        //Поиск операнда и его позиции в строке
        for (int i = 0; i < inputString.length(); i++){
            char c = inputString.charAt(i);
            if (c == '+' || c == '-' || c == '*' || c == '/'){
                if (operatorMark != -1){
                    throw new IllegalArgumentException("Калькулятор может выполнять вычисления только с двумя операндами и одним оператором");
                }
                operatorMark = i;
                operator = c;
            }
        }
        //Эксепшн, если нет оператора +; -; *; /
        if (operatorMark == -1){
            throw new IllegalArgumentException("Уважаемый Юзер, введите пожалуйста корректное математическое выражение. Калькулятор поддерживает сложение, вычитание, умножение, а также деление.");
        }

        //Поскакали в обратную сторону искать первый операнд
        String temp = "";
        boolean numberStarted = false;
        for (int i = operatorMark - 1; i >= 0; i--) {
            char c = inputString.charAt(i);
            if (Character.isDigit(c)) {
                temp = c + temp;
                numberStarted = true;
            } else if (c == ' ') {
                if (numberStarted) {
                    throw new IllegalArgumentException("А как программа должна понимать такой операнд? Она не кушает такие невкусные строки");
                }
            } else {
                break;
            }
        }
        operand1 = Integer.parseInt(temp);
        temp = "";              //Обнулили
        numberStarted = false;  //Это тоже
        if (operand1 < 1 || operand1 > 10){
            throw new IllegalArgumentException("Операнды (числа) должны быть от 1 до 10 включительно, дорогой Юзер");
        }



        //А теперь направо, будем искать второй операнд
        for (int i = operatorMark + 1; i < inputString.length(); i++) {
            char c = inputString.charAt(i);
            if (Character.isDigit(c)) {
                temp += c;
                numberStarted = true;
            } else if (c == ' ') {
                if (numberStarted) {
                    throw new IllegalArgumentException("А как программа должна понимать такой операнд? Она не кушает такие невкусные строки");
                }
            } else {
                break;
            }
        }
        operand2 = Integer.parseInt(temp);
        if (operand2 < 1 || operand2 > 10){
            throw new IllegalArgumentException("Операнды (числа) должны быть от 1 до 10 включительно, дорогой Юзер");
        }

        //Долгожданный результат
        String answer = "";
        switch (operator){
            case '+' -> answer += operand1 + operand2;
            case '-' -> answer += operand1 - operand2;
            case '*' -> answer += operand1 * operand2;
            case '/' -> answer += operand1 / operand2;
        }
        return answer;
    }
}