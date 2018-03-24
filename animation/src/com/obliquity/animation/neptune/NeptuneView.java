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

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;

import javax.swing.JPanel;

import java.text.DecimalFormat;

public class NeptuneView extends JPanel {
	private Color uranusColour = Color.green.darker().darker();
	private Color neptuneColour = Color.blue;
	
	private NeptuneModel model;
	
	private double orbitScale;
	private double offsetScale;
	
	private DecimalFormat format = new DecimalFormat("####.00");

	public NeptuneView(NeptuneModel model, double orbitScale, double offsetScale) {
		super(new BorderLayout());
		
		this.model = model;
		this.orbitScale = orbitScale;
		this.offsetScale = offsetScale;
		
		model.setView(this);
		
		setOpaque(true);
		setBackground(Color.white);
		setFont(new Font("SansSerif", Font.BOLD, 24));
	}

	protected void paintComponent(Graphics gOriginal) {
		Graphics g = gOriginal.create();
		
		Dimension size = getSize();

		int xc = size.width / 4;
		int yc = size.height / 2;

		g.setColor(getBackground());

		g.fillRect(0, 0, size.width, size.height);

		g.setColor(Color.yellow);

		int pRadius = 10;

		g.fillArc(xc - pRadius, yc - pRadius, 2 * pRadius + 1,
				2 * pRadius + 1, 0, 360);

		g.setColor(Color.red);

		for (int i = 0; i < 2; i++) {
			int radius = (int) (model.getSemiMajorAxis(i) * orbitScale);
			g.drawArc(xc - radius, yc - radius, 2 * radius + 1,
					2 * radius + 1, 0, 360);
		}

		int sRadius = 5;

		for (int i = 0; i < 2; i++) {
			double radius = model.getSemiMajorAxis(i) * orbitScale;
			
			double theta = model.getLongitude(i);

			double costheta = Math.cos(theta);
			double sintheta = Math.sin(theta);

			int x = xc + (int) (radius * costheta);
			int y = yc - (int) (radius * sintheta);

			if (i == 0)
				g.setColor(uranusColour);
			else
				g.setColor(neptuneColour);

			g.fillArc(x - sRadius, y - sRadius, 2 * sRadius + 1,
					2 * sRadius + 1, 0, 360);
		}
		
		FontMetrics fm = g.getFontMetrics();
		
		g.setColor(Color.black);
		
		String caption = "Orbits of Uranus and Neptune";
		
		int w = fm.stringWidth(caption);
		
		int textHeight = fm.getHeight();
		
		int yCaption = size.height - 10 - textHeight;
		
		g.drawString(caption, xc - w/2, yCaption);

		xc = (3 * size.width) / 4;

		int dx = (int) (offsetScale * 0.25);

		int dy = dx;

		g.setColor(Color.yellow);

		g.drawArc(xc - dx, yc - dy, 2 * dx + 1, 2 * dy + 1, 0, 360);

		int xpoints[] = new int[3];
		int ypoints[] = new int[3];

		xpoints[0] = xc;
		ypoints[0] = yc + dy + 20;

		xpoints[1] = xc - 6;
		ypoints[1] = ypoints[0] - 10;

		xpoints[2] = xc + 6;
		ypoints[2] = ypoints[1];

		g.fillPolygon(xpoints, ypoints, 3);

		g.setColor(neptuneColour);

		double deltaTheta = model.getLongitude(1) - model.getLongitude(0);

		double deltaX = model.getSemiMajorAxis(1) * Math.cos(deltaTheta)
				- model.getSemiMajorAxis(0);
		double deltaY = model.getSemiMajorAxis(1) * Math.sin(deltaTheta);

		deltaTheta = Math.atan2(deltaY, deltaX);

		double cdt = Math.cos(deltaTheta);
		double sdt = Math.sin(deltaTheta);

		double xa = (double) (dy + 20);
		double ya = 0.0;

		double xb = xa * cdt + ya * sdt;
		double yb = -xa * sdt + ya * cdt;

		xpoints[0] = xc + (int) yb;
		ypoints[0] = yc - (int) xb;

		xa = (double) (dy + 10);
		ya = 6.0;

		xb = xa * cdt + ya * sdt;
		yb = -xa * sdt + ya * cdt;

		xpoints[1] = xc + (int) yb;
		ypoints[1] = yc - (int) xb;

		ya = -6.0;

		xb = xa * cdt + ya * sdt;
		yb = -xa * sdt + ya * cdt;

		xpoints[2] = xc + (int) yb;
		ypoints[2] = yc - (int) xb;

		g.fillPolygon(xpoints, ypoints, 3);

		g.setColor(Color.black);

		g.drawLine(xc - dx, yc, xc + dx, yc);

		g.drawLine(xc, yc - dy, xc, yc + dy);

		for (int tick = 1; tick < 6; tick++) {
			int ticksize = (tick < 5) ? 3 : 5;

			dx = (int) (offsetScale * .05 * (double) tick);

			dy = dx;

			g.drawLine(xc - dx, yc - ticksize, xc - dx, yc + ticksize);

			g.drawLine(xc + dx, yc - ticksize, xc + dx, yc + ticksize);

			g.drawLine(xc - ticksize, yc - dy, xc + ticksize, yc - dy);

			g.drawLine(xc - ticksize, yc + dy, xc + ticksize, yc + dy);
		}

		dx = -(int) (offsetScale * model.getOffset(0));

		dy = (int) (offsetScale * model.getOffset(1));

		int x = xc + dx;
		int y = yc - dy;

		g.setColor(Color.lightGray);

		g.drawLine(x, yc, x, y);

		g.drawLine(xc, y, x, y);

		g.setColor(Color.black);

		g.drawLine(xc, yc, x, y);

		g.setColor(uranusColour);

		g.fillArc(x - sRadius, y - sRadius, 2 * sRadius + 1,
				2 * sRadius + 1, 0, 360);

		g.setColor(Color.black);
		
		caption = "Displacement of Uranus";
				
		w = fm.stringWidth(caption);
		
		g.drawString(caption, xc - w/2, yCaption);
		
		caption = "in rotating frame";
		
		w = fm.stringWidth(caption);
		
		yCaption += textHeight;
		
		g.drawString(caption, xc - w/2, yCaption);

		double t = model.getTime();
		
		String datestring = format.format(t);

		g.drawString(datestring, 5, 30);
		
		g.dispose();
	}

}
