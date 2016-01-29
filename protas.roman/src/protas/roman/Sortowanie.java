package protas.roman;

public class Sortowanie {
	private double [][] numbers;
	private double [][] helper;
	private int number;

	public void sort(double [][] values) {
		this.numbers = values;
		number = values.length;
		this.helper = new double [number][values[0].length];
		sortuj(0, number - 1);
	}
	private void sortuj(int low, int high) {
		// Check if low is smaller then high, if not then the array is sorted
		if (low < high) {
			// Get the index of the element which is in the middle
			int middle = (low + high) / 2;
			// Sort the left side of the array
			sortuj(low, middle);
			// Sort the right side of the array
			sortuj(middle + 1, high);
			// Combine them both
			objednac(low, middle, high);
		}
	}
	private void objednac(int low, int middle, int high) {
		// Copy both parts into the helper array
		for (int i = low; i <= high; i++) {
			helper[i] = numbers[i];
		}
		int i = low;
		int j = middle + 1;
		int k = low;
		// Copy the smallest values from either the left or the right side back
		// to the original array
		while (i <= middle && j <= high) {
			if (Math.abs(helper[i][2]) <= Math.abs(helper[j][2])) {
				numbers[k] = helper[i];
				i++;
			} else {
				numbers[k] = helper[j];
				j++;
			}
			k++;
		}
		// Copy the rest of the left side of the array into the target array
		while (i <= middle) {
			numbers[k] = helper[i];
			k++;
			i++;
		}
	}
}
