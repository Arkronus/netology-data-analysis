import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BigData {
    public static void main(String[] args) {

        final long POPULATION = 12_000_000;

        List<String> names = Arrays.asList("Иванов", "Петров", "Сидоров");
        List<People> peoples = new ArrayList<>();
        for (int i = 0; i < POPULATION; i++) {
            peoples.add(new People(names.get(
                    new Random().nextInt(names.size())),
                    new Random().nextInt(100),
                    Sex.randomSex()));
        }

        System.out.println("Simple streams");
        long startTime = System.nanoTime();

//      Выбрать мужчин-военнообязанных и вывести их количество в консоль.
        Long militaryCount = peoples.stream()
                .filter(people -> people.getSex().equals(Sex.MAN))
                .filter(people -> people.getAge()>=18 & people.getAge()<=27)
                .count();
        System.out.println("Кол-во военнообязанных: " + militaryCount);
//      Найти средний возраст среди мужчин и вывести его в консоль.
        Double avgMenAge = peoples.stream()
                .filter(people -> people.getSex().equals(Sex.MAN))
                .mapToInt(p -> p.getAge())
                .average().getAsDouble();
        System.out.println("Средний возраст: " + avgMenAge);

//      Найти кол-во потенциально работоспособных людей в выборке (т.е. от 18 лет и учитывая, что женщины выходят на пенсию в 60 лет, а мужчины - в 65).
        Long working = peoples.stream()
                .filter(people -> people.getAge() >= 18 & ((people.getAge() <= 65 & people.getSex().equals(Sex.MAN)) ||
                        (people.getAge() <= 60 & people.getSex().equals(Sex.WOMAN))))
                .count();
        System.out.println("Работоспособных: " + working);

        long stopTime = System.nanoTime();
        double processTime = (double) (stopTime - startTime) / 1_000_000_000.0;
        System.out.println("Process time: " + processTime + " s");


        System.out.println("Parallel streams");
        startTime = System.nanoTime();

//      Выбрать мужчин-военнообязанных и вывести их количество в консоль.
        militaryCount = peoples.parallelStream()
                .filter(people -> people.getSex().equals(Sex.MAN))
                .filter(people -> people.getAge()>=18 & people.getAge()<=27)
                .count();
        System.out.println("Кол-во военнообязанных: " + militaryCount);
//      Найти средний возраст среди мужчин и вывести его в консоль.
        avgMenAge = peoples.parallelStream()
                .filter(people -> people.getSex().equals(Sex.MAN))
                .mapToInt(p -> p.getAge())
                .average().getAsDouble();
        System.out.println("Средний возраст: " + avgMenAge);

//      Найти кол-во потенциально работоспособных людей в выборке (т.е. от 18 лет и учитывая, что женщины выходят на пенсию в 60 лет, а мужчины - в 65).
        working = peoples.parallelStream()
                .filter(people -> people.getAge() >= 18 & ((people.getAge() <= 65 & people.getSex().equals(Sex.MAN)) ||
                        (people.getAge() <= 60 & people.getSex().equals(Sex.WOMAN))))
                .count();
        System.out.println("Работоспособных: " + working);

        stopTime = System.nanoTime();
        processTime = (double) (stopTime - startTime) / 1_000_000_000.0;
        System.out.println("Process time: " + processTime + " s");
    }
}
