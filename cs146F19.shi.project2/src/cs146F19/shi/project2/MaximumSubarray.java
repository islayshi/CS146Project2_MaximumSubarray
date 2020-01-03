package cs146F19.shi.project2;

public class MaximumSubarray {

	// The largest profit that can be made
	private int maxSum = 0;
	// The arrival index for the largest profit
	private int arrival = 0;
	// The departure index for the largest profit
	private int departure = 0;

	// Constructor
	public MaximumSubarray(int maxSum, int arrival, int departure) {
		this.maxSum = maxSum;
		this.arrival = arrival;
		this.departure = departure;
	}

	// GETTER METHODS

	// return maxSum
	public int getMaxSum() {
		return maxSum;
	}

	// return arrival
	public int getArrival() {
		return arrival;
	}

	// return departure
	public int getDeparture() {
		return departure;
	}

	// SETTER METHODS

	// set MaxSum to integer n
	public void setMaxSum(int n) {
		maxSum = n;
	}

	// set arrival to integer n
	public void setArrival(int n) {
		arrival = n;
	}

	// set departure to integer n
	public void setDeparture(int n) {
		departure = n;
	}

	// ALGORITHMS TO USE

	// Takes array of working days
	// returns MaximumSubarray: maximum profit of the days, arrival, departure
	// O(n^2) runtime
	public MaximumSubarray bruteForce(int days[]) {
		MaximumSubarray result;
		int maxYet = 0;
		int leftIndex = 0;
		int rightIndex = days.length - 1;
		int length = days.length;

		for (int i = 0; i < length; i++) {
			for (int j = i; j < length; j++) {
				maxSum = 0;
				for (int k = i; k <= j; k++) {
					maxSum += days[k];
					if (maxYet < maxSum) {
						leftIndex = i;
						maxYet = maxSum;
						rightIndex = j;
					}
				}
			}
		}
		result = new MaximumSubarray(maxYet, leftIndex, rightIndex);
		return result;

	}

	// Helper method for divide and conquer
	// Takes in integer array days, lower index, middle index, and higher index
	public static MaximumSubarray calculateCrossSum(int[] days, int l, int m, int h) {
		int temp = 0;
		int leftSum = 0;
		int rightSum = 0;
		MaximumSubarray crossed;

		// for returning arrival and departure
		int start = -1;
		int end = -1;

		for (int i = m - 1; i >= l; i--) {
			temp = temp + days[i];
			if (temp > leftSum) {
				leftSum = temp;
				start = i;
			}
		}

		// reset temp
		temp = 0;

		for (int i = m + 1; i <= h; i++) {
			temp = temp + days[i];
			if (temp > rightSum) {
				rightSum = temp;
				end = i;
			}
		}
		crossed = new MaximumSubarray(rightSum + leftSum + days[m], start, end);
		return crossed;
	}

	// Divide and conquer method
	// O(nlgn) time
	// Take in array, first and last index (in form of integer variables)
	// return MaximumSubarray with the largest profit, arrival and departure date
	public static MaximumSubarray divideAndConquer(int days[], int l, int h) {
		// The returning object
		MaximumSubarray max = new MaximumSubarray(0, 0, 0);
		// For recursive calls for the left side of the array
		MaximumSubarray leftSum;
		// For recurisive calls for the right side of the array
		MaximumSubarray rightSum;
		// For the cross sum
		MaximumSubarray crossSum;

		int m = (l + h) / 2;

		// Base case
		if (l == h) {
			max = new MaximumSubarray(days[l], l, h);
			return max;
		} else {

			// Maximum subarray sum in left half (Make a recursive call)
			// Maximum subarray sum in right half (Make a recursive call)
			// Maximum subarray sum such that the subarray crosses the midpoint
			leftSum = divideAndConquer(days, l, m);
			rightSum = divideAndConquer(days, m + 1, h);
			crossSum = calculateCrossSum(days, l, m, h);

			// Check which side works
			if (leftSum.getMaxSum() > rightSum.getMaxSum() && leftSum.getMaxSum() > crossSum.getMaxSum()) {
				return leftSum;
			} else if (rightSum.getMaxSum() > leftSum.getMaxSum() && rightSum.getMaxSum() > crossSum.getMaxSum()) {
				return rightSum;
			}

			// else, return crossSum
			return crossSum;
		}
	}

	// Takes array of working days
	// O(n) run time
	// returns MaximumSubarray: maximum profit of the days, arrival, departure
	public MaximumSubarray kadaneAlgorithm(int days[]) {
		MaximumSubarray max;
		int maxSum = 0;
		int maxTemp = 0;
		int arrive = -1;
		int depart = -1;
		int tempArrive = 0;

		for (int i = 0; i < days.length; i++) {
			maxTemp = maxTemp + days[i];

			if (maxTemp < 0) {
				maxTemp = 0;
				arrive = i + 1;
			}

			if (maxSum < maxTemp) {
				maxSum = maxTemp;
				depart = i;
				tempArrive = arrive;
			}
		}
		arrive = tempArrive;
		max = new MaximumSubarray(maxSum, arrive, depart);
		return max;
	}

}
