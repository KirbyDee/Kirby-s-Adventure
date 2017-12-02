package com.kirbydee.main;

import java.awt.Graphics2D;

public interface Controlable extends Creatable {
	public void update() throws Exception;
	public void render(Graphics2D g) throws Exception;
}