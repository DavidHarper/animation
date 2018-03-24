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

import javax.swing.*;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class GalileanSatelliteView extends JPanel {
	private GalileanSatelliteModel model;
	
	private DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	private double scale;

	public GalileanSatelliteView(GalileanSatelliteModel model, double scale) {
		super(new BorderLayout());
		this.model = model;
		this.scale = scale;
		
		model.setView(this);
		
		setOpaque(true);
		setBackground(Color.white);
		setFont(new Font("SansSerif", Font.BOLD, 24));
	}

	protected void paintComponent(Graphics gOriginal) {
		Graphics g = gOriginal.create();
		
		Dimension size = getSize();

		int xc = size.width / 2;
		int yc = size.height / 2;

		g.setColor(getBackground());

		g.fillRect(0, 0, size.width, size.height);

		g.setColor(Color.black);

		int pRadius = (int) (model.getJupiterRadius() / scale);

		g.fillArc(xc - pRadius, yc - pRadius, 2 * pRadius + 1,
				2 * pRadius + 1, 0, 360);

		g.setColor(Color.red);

		for (int i = 0; i < 3; i++) {
			int radius = (int) (model.getSemiMajorAxis(i) / scale);
			g.drawArc(xc - radius, yc - radius, 2 * radius + 1,
					2 * radius + 1, 0, 360);
		}

		g.setColor(Color.blue);

		int sRadius = 5;

		for (int i = 0; i < 3; i++) {
			double radius = model.getSemiMajorAxis(i) / scale;
			double theta = model.getLongitude(i);

			double costheta = Math.cos(theta);
			double sintheta = Math.sin(theta);

			int x = xc + (int) (radius * costheta);
			int y = yc - (int) (radius * sintheta);

			g.fillArc(x - sRadius, y - sRadius, 2 * sRadius + 1,
					2 * sRadius + 1, 0, 360);
		}

		long ticks = model.getTime();
		
		java.util.Date date = new java.util.Date(ticks);
		String datestring = format.format(date);

		g.setColor(Color.black);

		g.drawString(datestring, 5, 30);
		
		g.dispose();
	}

}
