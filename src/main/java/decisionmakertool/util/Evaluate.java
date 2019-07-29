package decisionmakertool.util;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Evaluate {

   public static void main(String[] args) {
        Integer[] array = {30, 10, 20, 40};

        int multiplication = Arrays.stream(array).
                reduce(1, (x, y) -> x * y);
        System.out.println("Multi of Array: "+ multiplication);

        //sin identificador
        Optional<Integer> maxOptional = Arrays.stream(array).
                reduce(Integer::max);
        System.out.println("Max of Array: "+ maxOptional);

        //Con identificador
        List<Integer> listNum = Arrays.asList(10,15,25,20);
        int maxValue = listNum.stream().reduce(Integer.MIN_VALUE, Integer::max);
        System.out.println("Max of List: "+ maxValue);

        Arrays.stream(array).
                reduce(Integer::max).ifPresent(s -> System.out.println("Máximo de Array directo:" + s));

        List<String> list = Arrays.asList("Es ", "un ", "ejemplo");

        Optional<String> palabra  = list.stream().reduce((x, y) -> x + "," + y);
        System.out.println("List to Optional String: "+ palabra);

        list.stream().reduce((x, y) -> x.concat(y))
                .ifPresent(s -> System.out.println("List to String: "+ s));
    }
}
