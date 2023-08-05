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

package com.obliquity.animation.core;

import java.net.URL;

import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class AnimationToolBar extends JToolBar {
	public AnimationToolBar(AbstractAnimationController controller) {
		JButton btnPlay = makeNavigationButton("Play24", "Run animation", "Run");
		JButton btnPause = makeNavigationButton("Pause24", "Pause animation",
				"Pause");
		JButton btnFF = makeNavigationButton("FastForward24", "Fast forward",
				"FF");
		JButton btnRR = makeNavigationButton("Rewind24", "Rewind", "RWD");
		JButton btnStepF = makeNavigationButton("StepForward24",
				"Step forward", "StepFwd");
		JButton btnStepR = makeNavigationButton("StepBack24", "Step backward",
				"StepBack");

		add(btnRR);
		add(btnStepR);

		addSeparator();

		add(btnPause);
		add(btnPlay);

		addSeparator();

		add(btnStepF);
		add(btnFF);

		setFloatable(false);

		controller.registerActionButton(btnPlay, AbstractAnimationController.Action.PLAY);
		controller.registerActionButton(btnPause, AbstractAnimationController.Action.PAUSE);
		controller.registerActionButton(btnFF, AbstractAnimationController.Action.FAST_FORWARD);
		controller.registerActionButton(btnRR, AbstractAnimationController.Action.REWIND);
		controller.registerActionButton(btnStepR, AbstractAnimationController.Action.STEP_BACKWARD);
		controller.registerActionButton(btnStepF, AbstractAnimationController.Action.STEP_FORWARD);

	}
	protected JButton makeNavigationButton(String imageName,
			String toolTipText, String altText) {
		// Look for the image.
		String imgLocation = "/toolbarButtonGraphics/media/" + imageName
				+ ".gif";

		URL imageURL = getClass().getResource(imgLocation);

		// Create and initialize the button.
		JButton button = new JButton();
		button.setToolTipText(toolTipText);

		if (imageURL != null) { // image found
			button.setIcon(new ImageIcon(imageURL));
		} else { // no image found
			button.setText(altText);
			System.err.println("Resource not found: " + imgLocation);
		}

		return button;
	}

}
