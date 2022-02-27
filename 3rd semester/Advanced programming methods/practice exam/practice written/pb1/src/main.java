//2 (3p). Given the following collection
//
//List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8,9,10,11,12,14,15);
//
//Using Java functional style (Java streams),
//
//please write a program that is doing the following operations in the following order:
//
//a)keep only the numbers which are multiple of  5 or  multiple of 2;
//
//b)transform each remaining number into a string, that consists of a prefix "N", the number and the suffix "R"
//
//(eg. 5 is transformed into "N5R")
//
//c)concatenate all the strings


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//public class main {
//    public static void main(String[] args) {
//        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8,9,10,11,12,14,15);
//
//        List<Integer> numbersFiltered = numbers.stream()
//                .filter(number -> number % 5 == 0 || number % 2 == 0)
//                        .collect(Collectors.toList());
//
//        List<String> newList = numbersFiltered.stream().map(number -> new String("N" + number.toString() + "R"))
//                .collect(Collectors.toList());
//
//        //transfrom an integer list into a string
////        String s = numbersFiltered.stream().map(e -> e.toString()).collect(Collectors.joining(""));
//
//        //transform a string list into string
//        String s = String.join(",", newList);
//
////        newList.forEach(number -> System.out.println(number));
//        System.out.println(s);
//    }
//}

public class main {
    public static void main(String[] args){
        List<Integer> numbers = Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,14,15);

        int sum = numbers.stream().filter(p -> p % 4 == 0).
                map(p -> p++).reduce(0, Integer::sum) %2;

//        String s = numbers.stream().
//                filter(p -> p % 2 == 0 || p % 3 == 0).
//                map(p -> new String("A" + p.toString() + "B")).
//                collect(Collectors.joining(", "));

//        String s = String.join(", ", filtered);

//        filtered.forEach(p -> System.out.println(p));
        System.out.println(sum);
    }
}
