package com.weicoder.gpu;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.aparapi.Kernel;
import com.aparapi.Range;
import com.weicoder.common.U;

public class Main {
	@Test
	public void test() {
		final float[] inA = new float[] { 1, 2, 3, 4, 5, 6, 7, 8 };
		final float[] inB = new float[] { 11, 12, 13, 14, 15, 16, 17, 18 };
		final float[] result = new float[inA.length];

		int n = 100000000;
		U.D.dura();
		for (int j = 0; j < n; j++)
			for (int i = 0; i < inA.length; i++) {
				result[i] = inA[i] + inB[i];
			}
		System.out.println("time=" + U.D.dura() + ";res=" + Arrays.toString(result));
		Kernel kernel = new Kernel() {
			@Override
			public void run() {
				int i = getGlobalId();
				result[i] = inA[i] + inB[i];
			}
		};
		for (int j = 0; j < n; j++) {
			Range range = Range.create(result.length);
			kernel.execute(range);
		}
		System.out.println("time=" + U.D.dura() + ";res=" + Arrays.toString(result));
		kernel.dispose();

//		KernelManagers.SEQUENTIAL_ONLY.bestDevice().forEach(result.length, id -> result[id] = intA[id]+inB[id]);
//		KernelManager.instance().bestDevice().createRange(result.length).getDevice() 
	}
}
