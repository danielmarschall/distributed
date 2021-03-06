/*
 * Copyright (c) 2003-2009 jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'jMonkeyEngine' nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.jmex.awt.swingui.dnd;

import java.awt.Point;
import java.awt.datatransfer.Transferable;
import java.util.EventObject;

/**
 * @author Galun
 */
public class JMEDropTargetEvent extends EventObject {
	
	private static final long serialVersionUID = 1L;
	private Point point;
    private int action;
    private boolean accepted;
    private boolean completed;
    private JMEDragAndDrop dnd;

    
     JMEDropTargetEvent(Object source, Point point, int action, JMEDragAndDrop dnd ) {
        super(source);
    	this.point = point;
        this.action = action;
        this.dnd = dnd;
    }

    public Point getPoint() {
        return point;
    }

    public int getAction() {
        return action;
    }

    public void acceptDrop( int action ) {
        accepted = true;
    }

    public void dropComplete( boolean success ) {
        completed = success;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public Transferable getTransferable() {
        return dnd.getTransferable();
    }

    public boolean isCompleted() {
        return completed;
    }
}
