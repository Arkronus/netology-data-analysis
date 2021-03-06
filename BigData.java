import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

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
        Long militaryCount = countMilitary(peoples, false);
        System.out.println("Кол-во военнообязанных: " + militaryCount);

//      Найти средний возраст среди мужчин и вывести его в консоль.
        Double avgMenAge = calcAverageMenAge(peoples, false);
        System.out.println("Средний возраст: " + avgMenAge);

//      Найти кол-во потенциально работоспособных людей в выборке (т.е. от 18 лет и учитывая, что женщины выходят на пенсию в 60 лет, а мужчины - в 65).
        Long working = countWorking(peoples, false);
        System.out.println("Работоспособных: " + working);

        long stopTime = System.nanoTime();
        double processTime = (double) (stopTime - startTime) / 1_000_000_000.0;
        System.out.println("Process time: " + processTime + " s");


        System.out.println("Parallel streams");
        startTime = System.nanoTime();

//      Выбрать мужчин-военнообязанных и вывести их количество в консоль.
        militaryCount = countMilitary(peoples, true);
        System.out.println("Кол-во военнообязанных: " + militaryCount);

//      Найти средний возраст среди мужчин и вывести его в консоль.
        avgMenAge = calcAverageMenAge(peoples, true);
        System.out.println("Средний возраст: " + avgMenAge);

//      Найти кол-во потенциально работоспособных людей в выборке (т.е. от 18 лет и учитывая, что женщины выходят на пенсию в 60 лет, а мужчины - в 65).
        working = countWorking(peoples, true);
        System.out.println("Работоспособных: " + working);

        stopTime = System.nanoTime();
        processTime = (double) (stopTime - startTime) / 1_000_000_000.0;
        System.out.println("Process time: " + processTime + " s");
    }

    private static Long countWorking(List<People> peoples, boolean parallel) {
        Stream<People> peopleStream = parallel ? peoples.parallelStream() : peoples.stream();
        return peopleStream
                .filter(People::canWork)
                .count();
    }

    private static Long countMilitary(List<People> peoples, boolean parallel) {
        Stream<People> peopleStream = parallel ? peoples.parallelStream() : peoples.stream();
        return peopleStream
                .filter(people -> people.getSex().equals(Sex.MAN))
                .filter(people -> people.getAge() >= 18 & people.getAge() <= 27)
                .count();
    }

    private static Double calcAverageMenAge(Collection<People> peoples, boolean parallel) {
        Stream<People> peopleStream = parallel ? peoples.parallelStream() : peoples.stream();

        return peopleStream
                .filter(people -> people.getSex().equals(Sex.MAN))
                .mapToInt(People::getAge)
                .average().getAsDouble();

    }
}
