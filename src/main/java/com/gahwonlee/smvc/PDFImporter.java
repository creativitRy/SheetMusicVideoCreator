/*
 * SMVC
 * Copyright (C) 2017 GahwonLee
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.gahwonlee.smvc;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.function.Supplier;

/**
 * PDF
 *
 * @author GahwonLee
 */
public class PDFImporter implements Runnable, Supplier<BufferedImage[]> {
	private File pdfFile;
	private BufferedImage[] output;
	
	public PDFImporter(File pdfFile) {
		this.pdfFile = pdfFile;
	}
	
	@Override
	public void run() {
		try (PDDocument document = PDDocument.load(pdfFile)) {
			PDFRenderer renderer = new PDFRenderer(document);
			output = new BufferedImage[document.getNumberOfPages()];
			for (int i = 0; i < output.length; i++) {
				output[i] = renderer.renderImage(i);
			}
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
	
	public Image[] getFx() {
		Image[] images = new Image[output.length];
		for (int i = 0; i < output.length; i++) {
			images[i] = SwingFXUtils.toFXImage(output[i], null);
		}
		return images;
	}
	
	@Override
	public BufferedImage[] get() {
		return output;
	}
}
