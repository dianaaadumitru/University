//2 (3p). Given the following collection
//
//List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8,9,10,11,12,14,15,16);
//
//Using Java functional style (Java streams),
//
//please write a program that is doing the following operations in the following order:
//
//a)eliminates all the numbers which are not multiple of  4;
//
//b)transform each remaining number into its succesor (eg. 4 is transformed into 5);
//
//c)compute the sum modulo 2 of the remaining numbers (eg. (9 +5) mod 2=0)

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class main {
    public static void main(String[] args){
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8,9,10,11,12,14,15,16);
        List<Integer> filteredNumbers = numbers.stream().filter(number -> number % 4 == 0)
                .map(number -> number + 1)
                .collect(Collectors.toList());

        List<Integer> newList = filteredNumbers.stream().map(number -> number + 1)
                        .collect(Collectors.toList());

//        Integer sum = filteredNumbers.stream().reduce(0, Integer::sum);
        Integer sum = filteredNumbers.stream().mapToInt(Integer::intValue).sum();

//        filteredNumbers.forEach(number -> System.out.println(number));
        System.out.println(sum % 2);

    }
}
