package cs146F19.shi.project2;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import org.junit.Test;

public class MaximumSubarrayTest {

	// Trivial Test Cases
	// Getter methods
	// Use this object for trivial test cases
	public MaximumSubarray max = new MaximumSubarray(24, 1, 2);

	@Test
	public void testGetMaxSum() {
		assertEquals(24, max.getMaxSum());
	}

	@Test
	public void testGetArrival() {
		assertEquals(1, max.getArrival());
	}

	@Test
	public void testGetDeparture() {
		assertEquals(2, max.getDeparture());
	}

	// Setter methods
	@Test
	public void testSetMaxSum() {
		max.setMaxSum(10);
		assertEquals(10, max.getMaxSum());
	}

	@Test
	public void testSetArrival() {
		max.setArrival(11);
		assertEquals(11, max.getArrival());
	}

	@Test
	public void testSetDeparture() {
		max.setDeparture(12);
		assertEquals(12, max.getDeparture());
	}

	//Read maxSumtest.txt and use each of the three functions to see if they run correctly
	@Test
	public void MainTest() throws IOException {

		MaximumSubarray max = new MaximumSubarray(0, 0, 0);

		try {
			Scanner scanner = new Scanner(new File("data/maxSumtest.txt"));

			for (int i = 0; i < 10; i++) {
				int j = 0;
				int[] result = new int[100];
				while (j < 100) {
					result[j] = scanner.nextInt();
					j++;
				}

				/* ANSWERS ARE THE LAST THREE INDEXES. USE THEM TO COMPARE FOR THE OUTPUT */

				// CORRECT ANSWERS
				int profitAnswer = scanner.nextInt();
				int arrivalAnswer = scanner.nextInt();
				int departureAnswer = scanner.nextInt();
				MaximumSubarray correctAnswers = new MaximumSubarray(profitAnswer, arrivalAnswer, departureAnswer);

				// CALL AND TEST BRUTE FORCE FUNCTION
				max = max.bruteForce(result);

				assertEquals(correctAnswers.getMaxSum(), max.getMaxSum());
				assertEquals(correctAnswers.getArrival(), max.getArrival());
				assertEquals(correctAnswers.getDeparture(), max.getDeparture());

				// CALL AND TEST DIVIDE AND CONQUER FUNCTION
				max = max.divideAndConquer(result, 0, result.length - 1);
				assertEquals(correctAnswers.getMaxSum(), max.getMaxSum());
				assertEquals(correctAnswers.getArrival(), max.getArrival());
				assertEquals(correctAnswers.getDeparture(), max.getDeparture());

				// CALL AND TEST KADANE ALGORITHM FUNCTION
				max = max.kadaneAlgorithm(result);

				assertEquals(correctAnswers.getMaxSum(), max.getMaxSum());
				assertEquals(correctAnswers.getArrival(), max.getArrival());
				assertEquals(correctAnswers.getDeparture(), max.getDeparture());

			}

			// CLOSE THE SCANNER
			scanner.close();

		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound");
		}

	}

	// takes integer num for amount of elements
	// Print out times in nanoseconds, run through each function
	// 10 times and return average times in nanoseconds
	public void testRandom(int num) {
		MaximumSubarray max = new MaximumSubarray(0, 0, 0);

		Random ran = new Random();

		// For Brute Force
		long startTime1 = 0;
		long before1 = 0;
		long after1 = 0;

		// for Divide and Conquer
		long startTime2 = 0;
		long before2 = 0;
		long after2 = 0;

		// For Kadane
		long startTime3 = 0;
		long before3 = 0;
		long after3 = 0;

		System.out.println("**** TESTING " + num + " ELEMENTS ****");
		for (int i = 0; i < 10; i++) {
			// create random array a[]
			int[] arr = new int[num];
			for (int j = 0; j < num; j++) {
				arr[j] = ran.nextInt();
			}

			// BRUTE FORCE TIMES
			before1 = System.nanoTime();
			max.bruteForce(arr);
			after1 = System.nanoTime();
			startTime1 = startTime1 + (after1 - before1);

			// DIVIDE AND CONQUER TIMES
			before2 = System.nanoTime();
			max.divideAndConquer(arr, 0, arr.length - 1);
			after2 = System.nanoTime();
			startTime2 = startTime2 + (after2 - before2);

			// KADANE ALGORITHM TIMES
			before3 = System.nanoTime();
			max.kadaneAlgorithm(arr);
			after3 = System.nanoTime();
			startTime3 = startTime3 + (after3 - before3);
		}

		startTime1 = startTime1 / 10;
		System.out.println("BRUTE FORCE TIME: " + startTime1);

		startTime2 = startTime2 / 10;
		System.out.println("DIVIDE AND CONQUER TIME: " + startTime2);

		startTime3 = startTime3 / 10;
		System.out.println("KADANE ALGORITHM TIME: " + startTime3);
		System.out.println();

	}

	//Calls upon testRandom for input sizes of random 
	//integers of 50, 100, 200, 500, and 1000 elements
	@Test
	public void testWithNum() {
		testRandom(50);
		testRandom(100);
		testRandom(200);
		testRandom(500);
		testRandom(1000);
	}

}
