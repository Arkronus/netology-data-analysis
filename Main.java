import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        Collection<People> peoples = Arrays.asList(
                new People("Вася", 16, Sex.MAN),
                new People("Петя", 23, Sex.MAN),
                new People("Елена", 42, Sex.WOMAN),
                new People("Иван Иванович", 69, Sex.MAN)
        );


        List<People> military = peoples.stream()
                .filter(people -> people.getSex().equals(Sex.MAN))
                .filter(people -> people.getAge()>=18 & people.getAge()<=27)
                .collect(Collectors.toList());

        for (People p : military) System.out.println(p.toString());

        Double avgMenAge = peoples.stream()
                .filter(people -> people.getSex().equals(Sex.MAN))
                .mapToInt(p -> p.getAge())
                .average().getAsDouble();
        System.out.println("Средний возраст: " + avgMenAge);

        Long working = peoples.stream()
                .filter(people -> people.getAge() >= 18 & people.getAge() <= 65)
                .count();
        System.out.println("Работоспособных людей: " + working);
    }
}
