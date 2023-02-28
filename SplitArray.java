import java.util.*;

public class SplitArray {
    /*
        Function used to check if there might exist such a sum,
        otherwise we cannot have a solution.
        In:
            - sumElements: int (the sum of elements from initial array)
            - noElements: int (the number of elements from initial array)
        Out: True or False
        Time Complexity: O(n)
    */
    public static Boolean isPossible(int sumElements, int noElements) {
        for (int posLength = 1; posLength <= noElements / 2; posLength++) {
            if (sumElements * posLength % noElements == 0) {
                return true;
            }
        }

        return false;
    }

    /*
        Main function for our problem.
        In: numbers: int[] (the initial array)
        Out: True or false
        Time Complexity: O(n^3)
    */
    public static boolean splitArraySameAverage(int[] numbers) {
        // if our initial array has only 1 element, best case scenario, return false
        if (numbers.length == 1) {
            return false;
        }

        int noElements = numbers.length;
        int sumElements = Arrays.stream(numbers).sum();

        // check if we have possible integer sums respecting the property
        if (!isPossible(sumElements, noElements)) {
            return false;
        }

        // initialize our list of sets
        List<Set<Integer>> sums = new ArrayList<>();
        for (int i = 0; i <= noElements / 2; i++) {
            sums.add(new HashSet<>());
        }
        sums.get(0).add(0); // the possible sum with 0 elements is 0

        // build the list storing the sums with i elements
        for (int num: numbers) {                            // for every element in the initial list
            for (int i = sums.size() - 1; i > 0; i--) {     // we try to add it to a partition with i elements
                for (int s: sums.get(i - 1)) {              // from a partition with i - 1 elements
                    sums.get(i).add(s + num);
                }
            }
        }

        // check for the existence of the sum in the group with a certain possible length
        for (int posLength = 1; posLength <= noElements / 2; posLength++) {
            if (sumElements * posLength % noElements == 0 && sums.get(posLength).contains(sumElements * posLength / noElements)) {
                return true;
            }
        }

        // if we didn't find any sum respecting the property, we return false
        return false;
    }

    public static void main(String[] args) {
        assert SplitArray.splitArraySameAverage(new int[] {1, 2, 3, 4, 5, 6, 7, 8});
        assert !SplitArray.splitArraySameAverage(new int[] {1, 2, 3});
    }
}
