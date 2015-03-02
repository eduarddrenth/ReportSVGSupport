/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vectorprint.report.svg;

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
import com.itextpdf.awt.PdfGraphics2D;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfTemplate;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.GVTBuilder;
import org.apache.batik.bridge.UserAgent;
import org.apache.batik.bridge.UserAgentAdapter;
import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.svg.SVGDocument;

/**
 *
 * @author Eduard Drenth at VectorPrint.nl
 */
public class BatikHelper {

   /**
    * The SVG document factory.
    */
   private static SAXSVGDocumentFactory factory;
   /**
    * The SVG bridge context.
    */
   private static BridgeContext ctx;

   static {
      String parser = XMLResourceDescriptor.getXMLParserClassName();
      factory = new SAXSVGDocumentFactory(parser);

      UserAgent userAgent = new UserAgentAdapter();
      ctx = new BridgeContext(userAgent);
      ctx.setDynamicState(BridgeContext.STATIC);

   }

   /**
    *
    * @param svg the svg document
    * @param contentByte the canvas to plot to
    * @param width the width in pt of the svg will be fit to
    * @param height the height in pt of the svg will be fit to
    * @return
    * @throws BadElementException
    */
   public static PdfTemplate getSVGTemplate(Reader svg, PdfContentByte contentByte, float width, float height, float opacity)
       throws BadElementException, IOException {

      // create PdfTemplate from PdfContentByte
      PdfTemplate template = contentByte.createTemplate(width, height);

      // create Graphics2D from PdfTemplate
      Graphics2D g2 = new PdfGraphics2D(template, width, height);

      SVGDocument svgd = factory.createSVGDocument(null, svg);
      svgd.getRootElement().setAttribute("opacity", String.valueOf(opacity));

      GraphicsNode build = new GVTBuilder().build(ctx, svgd);
      build.paint(g2);

      g2.dispose();    // always dispose this
      return template;
   }

   /**
    *
    * @param svg the svg document
    * @param contentByte the canvas to plot to
    * @param width the width in pt of the svg will be fit to
    * @param height the height in pt of the svg will be fit to
    * @return
    * @throws BadElementException
    */
   public static Image getSVGImage(Reader svg, PdfContentByte contentByte, float width, float height, float opacity)
       throws BadElementException, IOException {
      return Image.getInstance(getSVGTemplate(svg, contentByte, width, height, opacity));
   }

   public static Image getSVGImage(URL svg, PdfContentByte contentByte, float width, float height, float opacity) throws BadElementException, IOException {
      return getSVGImage(new BufferedReader(new InputStreamReader(svg.openStream())), contentByte, width, height, opacity);
   }

   public static Image getSVGImage(String svg, PdfContentByte contentByte, float width, float height, float opacity) throws BadElementException, IOException {
      return getSVGImage(new StringReader(svg), contentByte, width, height, opacity);
   }
}
