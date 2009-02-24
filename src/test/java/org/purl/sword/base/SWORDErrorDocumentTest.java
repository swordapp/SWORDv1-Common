/**
 * Copyright (c) 2007-2009, Aberystwyth University
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above
 *    copyright notice, this list of conditions and the
 *    following disclaimer.
 *
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 *  - Neither the name of the Centre for Advanced Software and
 *    Intelligent Systems (CASIS) nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF
 * THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */
package org.purl.sword.base;

import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.ParsingException;
import org.junit.*;
/**
 *
 * @author Neil Taylor (nst@aber.ac.uk)
 */
public class SWORDErrorDocumentTest
{
   private String start = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n" +
                "<error xmlns=\"http://purl.org/net/sword/\"\n"  +
                "         xmlns:atom=\"http://www.w3.org/2005/Atom\"\n" +
                "         xmlns:sword=\"http://purl.org/net/sword/\"\n" +
                "         xmlns:dcterms=\"http://purl.org/dc/terms/\"\n";


   private String end = "</error>";

   public Element createElementForTest(String xml)
   throws ParsingException, IOException, UnmarshallException
   {
        Builder builder = new Builder();
		Document doc = builder.build(xml, Namespaces.NS_SWORD);
        return doc.getRootElement();
   }

   @Test
   public void unmarshallWithoutHref()
   throws Exception
   {
       String xml = start + ">\n" + end;

       Element element = createElementForTest(xml);
       SWORDErrorDocument error = new SWORDErrorDocument();
       SwordValidationInfo info = error.unmarshall(element, new Properties());

       printValues(info, "");

       assert(info.getType() == SwordValidationInfoType.WARNING);
   }

   @Test
   public void unmarshallWithValidErrorContentHref()
   throws Exception
   {
       String xml = start + " href=\"" + ErrorCodes.ERROR_CONTENT + "\" >\n" + end;
       System.out.println(xml);
       
       Element element = createElementForTest(xml);
       SWORDErrorDocument error = new SWORDErrorDocument();
       SwordValidationInfo info = error.unmarshall(element, new Properties());
       System.out.println("the test is: " + info);
       printValues(info, "");

       assert(info.getType() == SwordValidationInfoType.VALID);
   }

   @Test
   public void unmarshallWithValidErrorBadRequestHref()
   throws Exception
   {
       String xml = start + " href=\"" + ErrorCodes.ERROR_BAD_REQUEST + "\" >\n" + end;
       System.out.println(xml);

       Element element = createElementForTest(xml);
       SWORDErrorDocument error = new SWORDErrorDocument();
       SwordValidationInfo info = error.unmarshall(element, new Properties());
       System.out.println("the test is: " + info);
       printValues(info, "");

       assert(info.getType() == SwordValidationInfoType.VALID);
   }

   @Test
   public void unmarshallWithValidErrorChecksumHref()
   throws Exception
   {
       String xml = start + " href=\"" + ErrorCodes.ERROR_CHECKSUM_MISMATCH + "\" >\n" + end;
       System.out.println(xml);

       Element element = createElementForTest(xml);
       SWORDErrorDocument error = new SWORDErrorDocument();
       SwordValidationInfo info = error.unmarshall(element, new Properties());
       System.out.println("the test is: " + info);
       printValues(info, "");

       assert(info.getType() == SwordValidationInfoType.VALID);
   }

   @Test
   public void unmarshallWithValidMediationHref()
   throws Exception
   {
       String xml = start + " href=\"" + ErrorCodes.MEDIATION_NOT_ALLOWED + "\" >\n" + end;
       System.out.println(xml);

       Element element = createElementForTest(xml);
       SWORDErrorDocument error = new SWORDErrorDocument();
       SwordValidationInfo info = error.unmarshall(element, new Properties());
       System.out.println("the test is: " + info);
       printValues(info, "");

       assert(info.getType() == SwordValidationInfoType.VALID);
   }

   @Test
   public void unmarshallWithValidTargetHref()
   throws Exception
   {
       String xml = start + " href=\"" + ErrorCodes.TARGET_OWNER_UKNOWN + "\" >\n" + end;
       System.out.println(xml);

       Element element = createElementForTest(xml);
       SWORDErrorDocument error = new SWORDErrorDocument();
       SwordValidationInfo info = error.unmarshall(element, new Properties());
       System.out.println("the test is: " + info);
       printValues(info, "");

       assert(info.getType() == SwordValidationInfoType.VALID);
   }

   @Test
   public void unmarshallWithValidExternalHref()
   throws Exception
   {
       String xml = start + " href=\"http://www.aber.ac.uk/casis/test\" >\n" + end;
       System.out.println(xml);

       Element element = createElementForTest(xml);
       SWORDErrorDocument error = new SWORDErrorDocument();
       SwordValidationInfo info = error.unmarshall(element, new Properties());
       System.out.println("the test is: " + info);
       printValues(info, "");

       assert(info.getType() == SwordValidationInfoType.VALID);
   }

   @Test
   public void unmarshallWithInvalidSWORDHref()
   throws Exception
   {
       String xml = start + " href=\"" + ErrorCodes.MEDIATION_NOT_ALLOWED + "additional\" >\n" + end;
       System.out.println(xml);

       Element element = createElementForTest(xml);
       SWORDErrorDocument error = new SWORDErrorDocument();
       SwordValidationInfo info = error.unmarshall(element, new Properties());
       System.out.println("the test is: " + info);
       printValues(info, "");

       assert(info.getType() == SwordValidationInfoType.ERROR);
   }

   /**
     * Utility method that will recursively print out the list of items
     * for the specified validation info object.
     *
     * @param info   The validation info object to display.
     * @param indent The level of indent, expressed as a number of space characters.
     */
    private void printValues(SwordValidationInfo info, String indent)
    {
       String prefix = info.getElement().getPrefix();
       //System.out.println("prefix: " + prefix);
       StringBuffer buffer = new StringBuffer();
       buffer.append(indent);

       buffer.append("[");
       buffer.append(info.getType());
       buffer.append("]");

       if( prefix != null && prefix.trim().length() > 0 )
       {
          buffer.append(prefix);
          buffer.append(":");
       }

       buffer.append(info.getElement().getLocalName());
       buffer.append("  ");
       if (info.getAttribute() != null) {
          buffer.append(info.getAttribute().getLocalName());
          buffer.append("=\"");
          if( info.getContentDescription() != null )
          {
             buffer.append(info.getContentDescription());
          }
          buffer.append("\"");
       }
       else
       {
          if( info.getContentDescription() != null )
          {
                 buffer.append(" Value: '");
                 buffer.append(info.getContentDescription());
                 buffer.append("'");
          }
       }
       buffer.append("\n" + indent + "message: " );
       buffer.append(info.getMessage());

       System.out.println(buffer.toString());

       // process the list of attributes first
       Iterator<SwordValidationInfo> iterator = info.getValidationAttributeInfoIterator();
       while( iterator.hasNext())
       {
          printValues(iterator.next(), "   " + indent);
       }

       iterator = info.getUnmarshallAttributeInfoIterator();
       while( iterator.hasNext())
       {
          printValues(iterator.next(), "   " + indent);
       }

       // next, process the element messages
       iterator = info.getValidationElementInfoIterator();
       while( iterator.hasNext())
       {
          printValues(iterator.next(), "   " + indent);
       }

       iterator = info.getUnmarshallElementInfoIterator();
       while( iterator.hasNext())
       {
          printValues(iterator.next(), "   " + indent);
       }

    }

}
