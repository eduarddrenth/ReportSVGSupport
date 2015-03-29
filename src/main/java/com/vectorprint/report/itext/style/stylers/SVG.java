/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vectorprint.report.itext.style.stylers;

/*
 * #%L
 * VectorPrintReport4.0
 * %%
 * Copyright (C) 2012 - 2013 VectorPrint
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.vectorprint.VectorPrintException;
import com.vectorprint.configuration.EnhancedMap;
import com.vectorprint.report.itext.ImageLoader;
import com.vectorprint.report.itext.LayerManager;
import com.vectorprint.report.itext.style.parameters.FloatParameter;

import static com.vectorprint.report.itext.style.stylers.DocumentSettings.WIDTH;
import static com.vectorprint.report.itext.style.stylers.DocumentSettings.HEIGHT;
import com.vectorprint.report.svg.BatikHelper;
import java.io.IOException;

/**
 * printing svg as images
 * @author Eduard Drenth at VectorPrint.nl
 */
public class SVG extends com.vectorprint.report.itext.style.stylers.Image<String> {

   public SVG() {
      super();
      initParams();
   }

   private void initParams() {
      addParameter(new FloatParameter(WIDTH, "width of the svg").setDefault(50f),SVG.class);
      addParameter(new FloatParameter(HEIGHT, "width of the svg").setDefault(50f),SVG.class);
   }

   public SVG(ImageLoader imageLoader, LayerManager layerManager,Document document, PdfWriter writer, EnhancedMap settings) throws VectorPrintException {
      super(imageLoader,layerManager,document, writer, settings);
      initParams();
   }

   @Override
   protected Image createImage(PdfContentByte canvas, String data, float opacity) throws VectorPrintException, BadElementException {
      Image img;
      try {
         if (data==null&&getData()==null) {
            img = BatikHelper.getSVGImage(getUrl(), canvas, getWidth(), getHeight(), opacity);
         } else {
            img = BatikHelper.getSVGImage((data==null)?getData():data, canvas, getWidth(), getHeight(),opacity);
         }
      } catch (IOException ex) {
         throw new VectorPrintException(ex);
      }
      applySettings(img);
      return img;
   }

   public float getWidth() {
      return getValue(WIDTH, Float.class);
   }

   public void setWidth(float width) {
      setValue(WIDTH, width);
   }

   public float getHeight() {
      return getValue(HEIGHT, Float.class);
   }

   public void setHeight(float height) {
      setValue(HEIGHT, height);
   }


}
