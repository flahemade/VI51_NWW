/* 
 * $Id$
 * 
 * Copyright (c) 2011-15 Stephane GALLAND <stephane.galland@utbm.fr>.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * This program is free software; you can redistribute it and/or modify
 */
package fr.utbm.info.vi51.framework.environment;

import java.io.Serializable;
import java.util.UUID;

import fr.utbm.info.vi51.framework.math.Point2f;

/**
 * Abstract implementation of an influence.
 * 
 * @param <IO> is the type of the influencable objects.
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public abstract class Influence implements Serializable {

	private static final long serialVersionUID = -3172105252469025247L;

	protected UUID emitter = null;
	
	/** Replies the emitter of the influence.
	 * 
	 * @return the emitter of the influence.
	 */
	public UUID getEmitter() {
		return this.emitter;
	}
	
	/** Set the emitter of the influence.
	 * 
	 * @param emitter is the emitter of the influence.
	 */
	public void setEmitter(UUID emitter) {
		assert(emitter!=null);
		this.emitter = emitter;
	}

	public Point2f getCenter() {
		// TODO Auto-generated method stub
		return null;
	}
}