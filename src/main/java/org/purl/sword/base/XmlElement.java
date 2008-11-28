/**
 * Copyright (c) 2008, Aberystwyth University
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

import nu.xom.Element;
import nu.xom.Node;

/**
 * Parent class for all classes that represent an XML element. This provides
 * some common utility methods that are useful for marshalling and 
 * unmarshalling data. 
 * 
 * @author Neil Taylor
 */
public class XmlElement 
{
   /**
    * The name to use for the prefix. E.g. atom:title, atom is the prefix. 
    */
   protected String prefix; 
   
   /**
    * The local name of the element. E.g. atom:title, title is the local name. 
    */
   protected String localName; 
      
   /**
    * Create a new instance. Set the local name that will be used. 
    * 
    * @param localName The local name for the element. 
    */
   public XmlElement(String localName)
   {
      this("", localName);
   }
   
   /**
    * Create a new instance. Set the prefix and local name. 
    * 
    * @param prefix The prefix for the element. 
    * @param localName The local name for the element. 
    */
   public XmlElement(String prefix, String localName)
   {
      this.prefix = prefix;
      this.localName = localName;
   }
   
   /**
    * The Date format that is used to parse dates to and from the ISO format 
    * in the XML data. 
    */
   protected static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
   
   
   protected static final String[] DATE_FORMATS = 
   {
      "yyyy-MM-dd'T'HH:mm:ss'Z'",
      "yyyy-MM-dd'T'HH:mm:ss.SZ",
      "yyyy-MM-dd'T'HH:mm:ss.Sz",
      "yyyy-MM-dd'T'HH:mm:ssZ",
      "yyyy-MM-dd'T'HH:mm:ssz",
      "yyyy-MM-dd'T'HH:mmZZZZ",
      "yyyy-MM-dd'T'HH:mmzzzz",
      "yyyy-MM-dd'T'HHZZZZ",
      "yyyy-MM-dd'T'HHzzzz",
      "yyyy-MM-dd'T'HH:mm:ss.S",
      "yyyy-MM-dd'T'HH:mm:ss",
      "yyyy-MM-dd'T'HH:mm",
      "yyyy-MM-dd'T'HH",
      "yyyy-MM-dd",
      "yyyy-MM",
      "yyyy"
   };
   
   /**
    * Extract a boolean value from the specified element. The boolean value 
    * is represented as the string 'true' or 'false' as the only child
    * of the specified element. 
    * 
    * @param element The element that contains the boolean value. 
    * @return True or false, based on the string in the element's content. 
    * @throws UnmarshallException If the element does not contain a single child, or if
    * the child does not contain the value 'true' or 'false'. 
    */
   protected boolean unmarshallBoolean( Element element )
   throws UnmarshallException 
   {
	  if( element.getChildCount() != 1 )
      {
         throw new UnmarshallException("Missing Boolean Value", null);
      }
      
      // ok to get the single child element. This should be a text element.
      try
      {
         Node child = element.getChild(0);
         String value = child.getValue();
         if( "true".equals(value) )
         {
        	   return true;
         }
         else if( "false".equals(value))
         {
        	   return false;
         }
         else
         {
        	   throw new UnmarshallException("Illegal Value");
         }
      }
      catch( IndexOutOfBoundsException ex )
      {
         throw new UnmarshallException("Error accessing Boolean element", ex);
      }
   }

   /**
    * Extract a string value from the specified element. The value 
    * is the only child of the specified element. 
    * 
    * @param element The element that contains the string value. 
    * @return The string. 
    * @throws UnmarshallException If the element does not contain a single child. 
    */
   protected String unmarshallString( Element element )
   throws UnmarshallException
   {
       if( element.getChildCount() != 1 )
	   {
	      throw new UnmarshallException("Missing String Value", null);
	   }
	      
	   // ok to get the single child element. This should be a text element.
	   try
	   {
	      Node child = element.getChild(0);
	      return child.getValue();
	   }
	   catch( IndexOutOfBoundsException ex )
	   {
	      throw new UnmarshallException("Error accessing String element", ex);
	   } 
	   
   }
   
   /**
    * Extract an integer value from the specified element. The integer value 
    * is represented as a string in the only child
    * of the specified element. 
    * 
    * @param element The element that contains the integer. 
    * @return The integer. 
    * @throws UnmarshallException If the element does not contain a single child, or if
    * the child does not contain the valid integer. 
    */
   protected int unmarshallInteger( Element element )
   throws UnmarshallException
   {
	   if( element.getChildCount() != 1 )
	   {
	      throw new UnmarshallException("Missing Integer Value", null);
	   }
	      
	   // ok to get the single child element. This should be a text element.
	   try
	   {
	      Node child = element.getChild(0);
	      return Integer.parseInt( child.getValue() );
	   }
	   catch( IndexOutOfBoundsException ex )
	   {
	      throw new UnmarshallException("Error accessing Integer", ex);
	   } 
	   catch( NumberFormatException nfex )
	   {
	      throw new UnmarshallException("Error fomratting the number", nfex);	   
	   }
   }
      
   /**
    * Determines if the specified element is an instance of the element name. If 
    * you are checking the name title in the ATOM namespace, then the local name
    * should be 'title' and the namespaceURI is the URI for the ATOM namespace. 
    * 
    * @param element      The specified element. 
    * @param localName    The local name for the element. 
    * @param namespaceURI The namespace for the element. 
    * @return True if the element matches the localname and namespace. Otherwise, false. 
    */
   protected boolean isInstanceOf(Element element, String localName, String namespaceURI )
   {
      return (localName.equals(element.getLocalName()) && 
              namespaceURI.equals(element.getNamespaceURI()) );
   }
   
   /**
    * Retrieve the qualified name for this object. This uses the
    * prefix and local name stored in this object. 
    * 
    * @return A string of the format 'prefix:localName'
    */
   public String getQualifiedName()
   {
      return getQualifiedName(localName);
   }

   /**
    * Retrieve the qualified name. The prefix for this object is prepended 
    * onto the specified local name. 
    * 
    * @param name the specified local name. 
    * @return A string of the format 'prefix:name'
    */
   public String getQualifiedName(String name)
   {
      String qName = "";
      if( prefix != null && prefix.trim().length() > 0 ) 
      {
         qName = prefix + ":";
      }
      qName += name;
      return qName;
   }
   
   /**
    * Get the qualified name for the given prefix and name
    * 
    * @param prefix the prefix
    * @param name the name
    * @return the qualified name
    */
   public String getQualifiedNameWithPrefix(String prefix, String name)
   {
	   return prefix + ":" + name;
   }
}