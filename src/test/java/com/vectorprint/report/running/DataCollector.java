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
import com.vectorprint.report.data.DataCollectionMessages;
import com.vectorprint.report.data.DataCollectorImpl;
import com.vectorprint.report.data.ReportDataHolderImpl;

/**
 *
 * @author Eduard Drenth at VectorPrint.nl
 */
public class DataCollector extends DataCollectorImpl {

   private static boolean produceError = false;

   public static void setProduceError(boolean produceError) {
      DataCollector.produceError = produceError;
   }

   @Override
   public ReportDataHolderImpl collect() {
      if (produceError) {
         super.getDataHolder().getMessages().addMessage(DataCollectionMessages.Level.ERROR, "foutje");
      }
      return getDataHolder();
   }


}
