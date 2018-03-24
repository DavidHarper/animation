/*
 * animation - a package for simple animations
 *
 * Copyright (C) 2018 David Harper at obliquity.com
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 * 
 * You should have received a copy of the GNU Library General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA  02111-1307, USA.
 *
 * See the COPYING file located in the top-level-directory of
 * the archive of this library for complete text of license.
 */

package com.obliquity.animation.neptune;

public class NeptuneModel {
	// Constants taken from:
	//
	// Bretagnon, P. and Francou, G. (1988)
	// Planetary theories in rectangular and spherical variables. VSOP87
	// solutions
	// Astron. Astrophys. 202, 309
	//
	// http://cdsweb.u-strasbg.fr/htbin/Cat?VI/81

	private final double lambda[][] = { { 5.48129294299, 75.02543121646 },
			{ 5.31188633047, 38.37687716731 } };

	private final double mainLambda[][] = {
			{ 0.00365981718, 1.89962189068, 73.29712585900 },
			{ 0.00068892609, 6.09292489045, 76.26607127560 },
			{ 0.00026468869, 3.14152087888, 71.81265315070 },
			{ 0.00021078897, 4.36059465144, 148.07872442630 },
			{ 0.00017818665, 1.74436982544, 36.64856292950 } };

	private final double mainRadius[][] = {
			{ 0.03440835545, 0.32836098991, 73.29712585900 },
			{ 0.00649321851, 4.52247298119, 76.26607127560 },
			{ 0.00243508222, 1.57086595074, 71.81265315070 },
			{ 0.00161858251, 2.79137863469, 148.07872442630 },
			{ 0.00093192359, 0.17437193645, 36.64856292950 } };

	private final double semimajoraxis[] = { 19.21264847881, 30.07013206102 };

	private double t;

	private double theta[] = new double[2];
	private double offset[] = new double[2];
	
	private NeptuneView view;
	
	public void setView(NeptuneView view) {
		this.view = view;
	}
	
	public void setTime(double t) {
		this.t = t;
		
		calculateAngles();
		
		if (view != null)
			view.repaint();
	}
	
	public double getTime() {
		return t;
	}
	
	private void calculateAngles() {
		double T = (t - 2000.0) / 1000.0;

		for (int i = 0; i < 2; i++)
			theta[i] = lambda[i][0] + lambda[i][1] * T;

		offset[0] = 0.0;

		for (int i = 0; i < mainLambda.length; i++) {
			double arg = mainLambda[i][1] + mainLambda[i][2] * T;

			offset[0] += mainLambda[i][0] * Math.cos(arg);
		}

		offset[0] *= 180.0 / Math.PI;

		offset[1] = 0.0;

		for (int i = 0; i < mainRadius.length; i++) {
			double arg = mainRadius[i][1] + mainRadius[i][2] * T;

			offset[1] += mainRadius[i][0] * Math.cos(arg);
		}

		offset[1] *= (180.0 / Math.PI) / semimajoraxis[0];
	}
	
	public double getSemiMajorAxis(int i) {
		return semimajoraxis[i];
	}
	
	public double getLongitude(int i) {
		return theta[i];
	}
	
	public double getOffset(int i) {
		return offset[i];
	}
}
