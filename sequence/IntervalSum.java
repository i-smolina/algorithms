import java.util.Scanner;

/**
 * 
 * @author smolina irina
 * 
 * Дана последовательность натуральных чисел. 
 * Найти числа l, r, такие что сумма элементов в интервале [l, r] = S.
 * Задачу решить за линейной от n время. 
 * 
 * Алгоритм: будем считать частичные суммы от l до r. 
 * Если частичная сумма превзошла значение заданной суммы, 
 * тогда вычитаем из частичной суммы левые элементы, 
 * пока сумма не станет меньше либо равна заданной. 
 */
public class IntervalSum {
	public static void main(String[] args) {
		System.out.println("Enter sequence:");
		Scanner scanner = new Scanner(System.in);
		String s = scanner.nextLine();
		System.out.println("Enter sum:");
		int sum = scanner.nextInt();
		scanner.close();
		
		int[] seq = parseStr(s);
		getInterval(seq, sum);
	}
	
	/**
	 * get sequence of number from string
	 * @param s - source string
	 * @return array of number in sequence
	 */
	static int[] parseStr(String s) {
		String[] sarr = s.split("\\s+");
		int[] arr = new int[sarr.length];
		for (int i = 0; i < sarr.length; i++) {
			arr[i] = Math.abs(Integer.parseInt(sarr[i])); // only natural numbers!!!
		}
		return arr;
	}
	
	/**
	 * get the interval with a given sum
	 * @param seq - source sequence (natural numbers!!!)
	 * @param sum - sum of elements
	 */
	static void getInterval(int[] seq, int sum) {
		int partSum = 0;
		int l = 0;
		for (int r = 0; r < seq.length; r++) {
			partSum += seq[r];
			while (partSum > sum) {
				partSum -= seq[l++];
			}
			if (partSum == sum) {
				System.out.printf("l = %d, r = %d \n", l, r);
			}
		}
		System.out.println("Search finished");
	}
}
