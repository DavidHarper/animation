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

package com.obliquity.animation.galilean;

public class GalileanSatelliteModel {
	// Constants taken from:
	//
	// Lieske, J.H., 1998, "Galilean satellite ephemerides E5",
	// Astron. Astrophys. Suppl. Ser. 129, 205
	//
	// Lieske, J.H., 1977, "Theory of Motion of Jupiter's Galilean Satellites",
	// Astron. Astrophys. 56, 333
	
	private final double lambda[][] = { { 106.077187, 203.48895579033 },
			{ 175.731615, 101.37472473479 }, { 120.558829, 50.31760920702 } };

	private final double epoch = 2443000.5;

	private final double unixEpoch = 2440587.5;

	private final double semimajoraxis[] = { 421769.0, 671079.0, 1070428.0 };

	private final double jupiterRadius = 71398.0;

	private double theta[] = new double[3];
	
	private long ticks;
	
	private GalileanSatelliteView view;
	
	public void setView(GalileanSatelliteView view) {
		this.view = view;
	}
	
	public void setTime(long ticks) {
		this.ticks = ticks;
		calculateAngles();
		
		if (view != null)
			view.repaint();
	}
	
	public long getTime() {
		return ticks;
	}

	private void calculateAngles() {
		double t = (double)ticks/1000.0;
		
		double jd = unixEpoch + t / 86400.0;

		double T = (jd - epoch);

		for (int i = 0; i < 3; i++) {
			double thetaDegrees = lambda[i][0] + lambda[i][1] * T;
			thetaDegrees %= 360.0;
			theta[i] = Math.PI * thetaDegrees / 180.0;
		}
	}

	public double getJupiterRadius() {
		return jupiterRadius;
	}
	
	public double getSemiMajorAxis(int i) {
		return semimajoraxis[i];
	}
	
	public double getLongitude(int i) {
		return theta[i];
	}
}
