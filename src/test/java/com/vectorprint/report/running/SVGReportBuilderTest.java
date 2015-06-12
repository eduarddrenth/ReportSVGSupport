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

import com.vectorprint.VectorPrintException;
import com.vectorprint.configuration.Settings;
import com.vectorprint.configuration.decoration.FindableProperties;
import com.vectorprint.configuration.jaxb.SettingsFromJAXB;
import com.vectorprint.configuration.jaxb.SettingsXMLHelper;
import static com.vectorprint.report.ReportConstants.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Eduard Drenth at VectorPrint.nl
 */
public class SVGReportBuilderTest {

   private static ReportRunner instance;
//   private static 
   public static final String TARGET = "target" + File.separator;

   public SVGReportBuilderTest() throws IOException {
   }

   @BeforeClass
   public static void setUpClass() throws IOException, VectorPrintException {
      Logger.getLogger(Settings.class.getName()).setLevel(Level.FINE);
   }

   private static void init(boolean allowEmpties) throws IOException, VectorPrintException, JAXBException {
      FindableProperties.clearStaticReferences();
      instance = allowEmpties ?
          new ReportRunner(new SettingsFromJAXB().fromJaxb(SettingsXMLHelper.fromXML(new FileReader("src/test/resources/settings.xml")))) :
          new ReportRunner(new SettingsFromJAXB().fromJaxb(SettingsXMLHelper.fromXML(new FileReader("src/test/resources/settingsNoEmpties.xml"))));
   }

   @AfterClass
   public static void tearDownClass() {
   }

   @Before
   public void setUp() throws IOException, VectorPrintException, JAXBException {
      init(true);
      TestableReportGenerator.setDidCreate(false);
      instance.getSettings().remove(VERSION);
      instance.getSettings().remove(HELP);
   }

   @After
   public void tearDown() {
   }

   @Test
   public void testSVGOverrideData() throws Exception {
      instance.buildReport(new String[]{"output="+ TARGET+"testSVGOverrideData.pdf\nSVG.data.set_value=" + "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"200\" height=\"200\" version=\"1.1\"><defs><filter id=\"test\" filterUnits=\"objectBoundingBox\" x=\"0\" y=\"0\" width=\"1.5\" height=\"4\"><feOffset result=\"Off1\" dx=\"15\" dy=\"20\" /><feFlood style=\"flood-color:#ff0000\\;flood-opacity:0.8\" /><feComposite in2=\"Off1\" operator=\"in\" result=\"C1\" /><feOffset in=\"SourceGraphic\" result=\"Off2\" dx=\"30\" dy=\"40\" /><feFlood style=\"flood-color:#ff0000\\;flood-opacity:0.4\" /><feComposite in2=\"Off2\" operator=\"in\" result=\"C2\" /><feMerge><feMergeNode in=\"C2\" /><feMergeNode in=\"C1\" /><feMergeNode in=\"SourceGraphic\" /></feMerge></filter></defs><text x=\"30\" y=\"100\" style=\"font:36px verdana bold\\;fill:blue\\;filter:url(#test)\">Overridden text!</text></svg>"});
      assertTrue(TestableReportGenerator.isDidCreate());
   }

   @Test
   public void testSVG() throws Exception {
      instance.buildReport(new String[]{"output="+ TARGET+"testSVG.pdf"});
      assertTrue(TestableReportGenerator.isDidCreate());
   }

   @Test
   public void testSVGDebug() throws Exception {
      instance.buildReport(new String[]{"output="+ TARGET+"testSVGDebug.pdf\ndebug=true"});
      assertTrue(TestableReportGenerator.isDidCreate());
   }

}
