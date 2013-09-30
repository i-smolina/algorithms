import java.util.Scanner;

/**
 * 
 * @author smolina irina
 * 
 *  Дано число, представленное n цифрами в десятичной записи без ведущих нулей. 
 *  Из числа требуется вычеркнуть ровно k цифр так, чтобы результат был бы максимальным.
 * 
 *  Алгоритм: Нам надо удалить k цифр. Для этого мы выбираем из первых (слева) k цифр наибольшую по значению, 
 *  если таких несколько выбираем самую левую и стираем все цифры что стоят перед ней. 
 *  Теперь нам надо стереть меньшее число цифр, 
 *  и мы повторяем эту процедуру снова, пока не сможем больше стирать цифры.
 */

public class DeletionDigits {
	public static void main(String[] args) {
		System.out.println("Enter number: ");
		Scanner scanner = new Scanner(System.in);
		String s = scanner.next();
		System.out.println("Enter k: ");
		int k = scanner.nextInt();
		scanner.close();
		
		int[] number = getArrDigits(s);
		long n = strikeoutDigits(number, k);
		System.out.println(n);
	}
	
	/**
	 * get array of digits from string
	 * @param s - string contains number 
	 * @return array of digits
	 */
	static int[] getArrDigits(String s) {
		int n = s.length();
		int[] arr = new int[n]; // array of digits
		for (int i = 0; i < n; i++) {
			arr[i] = Character.digit(s.charAt(i), 10);
		}
		return arr;
	}
	
	/**
	 * strike out k digits in the number that the number will be maximum
	 * @param number
	 * @param k
	 * @return
	 */
	static long strikeoutDigits(int number[], int k) {
		int n = number.length; 	// length of number
		int m = 0;  			// index of maximum digit in the window
		int gr = 0;				// left border of the window
		int j = 0;  			// current index in the window
		
		while (k != 0 && gr + k + 1 < n) {
			// find maximum digit in the window
			for (m = j = gr; j < gr + k + 1; j++) {
				if (number[j] > number[m]) 
					m = j;
			}
			// delete digits before maximum digit
			for (j = gr; j < m; j++, k--) {
				number[j] = -1;
			}
			gr = m + 1; // move the window
		}
		// if the window came to the end of number
		while (k-- != 0) number[++gr] = -1;
				
		// get number
		StringBuilder sb = new StringBuilder();
		for (int a = 0; a < number.length; a++) {
			if (number[a] != -1)
				sb.append(number[a]);
		}
		return Long.parseLong(sb.toString());
	}
}
