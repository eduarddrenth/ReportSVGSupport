/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vectorprint.report.running;

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

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.vectorprint.VectorPrintException;
import com.vectorprint.report.data.DataCollectionMessages;
import com.vectorprint.report.data.ReportDataHolder;
import com.vectorprint.report.itext.BaseReportGenerator;
import com.vectorprint.report.itext.DefaultElementProducer;
import com.vectorprint.report.itext.EventHelper;
import com.vectorprint.report.itext.style.StyleHelper;
import com.vectorprint.report.itext.style.stylers.SVG;

/**
 *
 * @author Eduard Drenth at VectorPrint.nl
 */
public class TestableReportGenerator extends BaseReportGenerator<ReportDataHolder> {

   public TestableReportGenerator() throws VectorPrintException {
      super(new EventHelper<>(), new DefaultElementProducer());
   }

   @Override
   protected void createReportBody(Document document, ReportDataHolder data, com.itextpdf.text.pdf.PdfWriter writer) throws DocumentException, VectorPrintException {
      try {
         // add and style a SVG from a String, the string will override a svg string or url in the setup
         
         createAndAddElement("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"200\" height=\"200\" version=\"1.1\"><defs><filter id=\"test\" filterUnits=\"objectBoundingBox\" x=\"0\" y=\"0\" width=\"1.5\" height=\"4\"><feOffset result=\"Off1\" dx=\"15\" dy=\"20\" /><feFlood style=\"flood-color:#ff0000;flood-opacity:0.8\" /><feComposite in2=\"Off1\" operator=\"in\" result=\"C1\" /><feOffset in=\"SourceGraphic\" result=\"Off2\" dx=\"30\" dy=\"40\" /><feFlood style=\"flood-color:#ff0000;flood-opacity:0.4\" /><feComposite in2=\"Off2\" operator=\"in\" result=\"C2\" /><feMerge><feMergeNode in=\"C2\" /><feMergeNode in=\"C1\" /><feMergeNode in=\"SourceGraphic\" /></feMerge></filter></defs><text x=\"30\" y=\"100\" style=\"font:36px verdana bold;fill:blue;filter:url(#test)\">This is some text!</text></svg>", getStylers("svg"), Image.class);

         if (!getSettings().containsKey("SVG.data.set_value")) {
            StyleHelper.getStylers(getStylers("svg"),SVG.class).get(0).setData(null);
         }
         // add and style a SVG directly from setup
         
         createAndAddElement(null, getStylers("svg"), Image.class);

      } catch (InstantiationException | IllegalAccessException ex) {
         throw new VectorPrintException(ex);
      }
   }

   @Override
   public boolean continueOnDataCollectionMessages(DataCollectionMessages messages, com.itextpdf.text.Document document) throws VectorPrintException {
      if (!messages.getMessages(DataCollectionMessages.Level.ERROR).isEmpty()) {
         try {
            createAndAddElement(messages.getMessages(DataCollectionMessages.Level.ERROR), getStylers("bigbold"), Phrase.class);
         } catch (InstantiationException | IllegalAccessException | DocumentException ex) {
            throw new VectorPrintException(ex);
         }
      }
      return true;
   }

}
