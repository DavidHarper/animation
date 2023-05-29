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

import com.obliquity.animation.core.AbstractAnimationController;

public class NeptuneController extends AbstractAnimationController {
	private double t;
	private double dt;

	private NeptuneModel model;
	private NeptuneView view;
	
	public NeptuneController(NeptuneModel model, NeptuneView view, int timerDelay, int timerSpeed, double t0, double dt) {
		super(timerDelay, timerSpeed);
		
		this.dt = dt;
		this.model = model;
		this.view = view;
		
		this.t = t0;
		
		model.setTime(t);
	}

	public void advanceTime(boolean reverse, boolean fast) {
		double timestep = (fast ? 5.0 : 1.0) * dt;

		if (reverse)
			timestep = -timestep;

		t += timestep;
		
		model.setTime(t);
		view.repaint();
	}
}
