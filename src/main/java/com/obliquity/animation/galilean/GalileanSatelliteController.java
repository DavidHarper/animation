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

import java.util.Date;

import com.obliquity.animation.core.AbstractAnimationController;

public class GalileanSatelliteController extends AbstractAnimationController {
	private int dt;

	private long ticks;

	private GalileanSatelliteModel model;
	private GalileanSatelliteView view;
	
	public GalileanSatelliteController(GalileanSatelliteModel model, GalileanSatelliteView view, int timerDelay, int timerSpeed, int dt) {
		super(timerDelay, timerSpeed);
		
		this.dt = dt;
		this.model = model;
		this.view = view;
		
		Date now = new Date();
		ticks = now.getTime();

		model.setTime(ticks);
	}

	public void advanceTime(boolean reverse, boolean fast) {
		long timestep = (long) ((fast ? 5000 : 1000) * dt);

		if (reverse)
			timestep = -timestep;

		ticks += timestep;

		model.setTime(ticks);
		view.repaint();
	}
}
