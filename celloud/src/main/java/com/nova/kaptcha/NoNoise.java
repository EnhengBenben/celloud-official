package com.nova.kaptcha;

import java.awt.image.BufferedImage;

import com.google.code.kaptcha.GimpyEngine;

public class NoNoise implements GimpyEngine {
	@Override
	public BufferedImage getDistortedImage(BufferedImage bi) {
		return bi;
	}
}
