/**
 * Copyright (c) 2007, Aberystwyth University
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
package org.purl.sword.atom;

import nu.xom.Attribute;
import nu.xom.Element;

import org.purl.sword.base.Namespaces;
import org.purl.sword.base.SwordElementInterface;
import org.purl.sword.base.UnmarshallException;
import org.purl.sword.base.XmlElement;

import org.apache.log4j.Logger;

/**
 * Represents an ATOM Generator element. 
 * 
 * @author Neil Taylor
 */
public class Generator extends XmlElement implements SwordElementInterface
{
   /**
    * Label for the uri attribute. 
    */
   public static final String ATTRIBUTE_URI = "uri";
	
   /**
    * Label for the version attribute. 
    */
   public static final String ATTRIBUTE_VERSION = "version";
   
	/**
	* Local name for the element. 
	*/
   public static final String ELEMENT_NAME = "generator";
   
   /**
    * The content for the element. 
    */
   private String content; 

   /**
    * The URI attribute. 
    */
   private String uri;

   /**
    * The version attribute. 
    */
   private String version;

   /**
    * The logger. 
    */
   private static Logger log = Logger.getLogger(Generator.class);

   /** 
    * Create a new instance and set the prefix to 
    * 'atom' and the local name to 'generator'.  
    */
   public Generator()
   {
      super(Namespaces.PREFIX_ATOM, ELEMENT_NAME);
   }

   /**
    * Marshall the data in the object to an Element object. 
    * 
    * @return The element. 
    */
   public Element marshall()
   {
      Element element = new Element(getQualifiedName(), Namespaces.NS_ATOM);

      if( content != null )
      {
         element.appendChild(content);
      }

      if( uri != null ) 
      {
         Attribute uriAttribute = new Attribute(ATTRIBUTE_URI, uri);
         element.addAttribute(uriAttribute);
      }

      if( version != null ) 
      {
         Attribute versionAttribute = new Attribute(ATTRIBUTE_VERSION, version);
         element.addAttribute(versionAttribute);
      }


      return element;
   }

   /**
    * Unmarshall the specified Generator element into the data in this object. 
    * 
    * @param generator The generator element. 
    * 
    * @throws UnmarshallException If the specified element is not an atom:generator 
    *                             element, or if there is an error accessing the data. 
    */
   public void unmarshall(Element generator)
   throws UnmarshallException 
   {
      if( ! isInstanceOf(generator, localName, Namespaces.NS_ATOM))
      {
         log.error("Unexpected element. Expected " + getQualifiedName() + ". Got: " + 
               ((generator != null) ? generator.getQualifiedName() : "null" ));
         throw new UnmarshallException( "Not an atom:generator element" ); 
      }

      try
      {
         // get the attributes
         int attributeCount = generator.getAttributeCount();
         Attribute attribute = null; 
         for( int i = 0; i < attributeCount; i++ )
         {
            attribute = generator.getAttribute(i);
            if( ATTRIBUTE_URI.equals(attribute.getQualifiedName()))
            {
               uri = attribute.getValue();
            }
            else if( ATTRIBUTE_VERSION.equals(attribute.getQualifiedName()))
            {
               version = attribute.getValue();
            }
         }

         int length = generator.getChildCount();
         if( length > 0 )
         {
            content = unmarshallString(generator);
         }
      }
      catch( Exception ex )
      {
         log.error("Unable to parse an element in Generator: " + ex.getMessage());
         throw new UnmarshallException("Unable to parse element in Generator", ex);
      }
   }

   /**
    * Get the content. 
    * 
    * @return The content. 
    */
   public String getContent() {
      return content;
   }

   /**
    * Set the content. 
    * 
    * @param content The content. 
    */
   public void setContent(String content) {
      this.content = content;
   }

   /**
    * Get the URI. 
    * 
    * @return The URI. 
    */
   public String getUri() {
      return uri;
   }

   /**
    * Set the URI. 
    * 
    * @param uri The URI. 
    */
   public void setUri(String uri) {
      this.uri = uri;
   }

   /**
    * Get the version. 
    * 
    * @return The version. 
    */
   public String getVersion() {
      return version;
   }

   /**
    * Set the version. 
    * 
    * @param version The version. 
    */
   public void setVersion(String version) {
      this.version = version;
   }
   
   /**
    * Get a string representation. 
    * 
    * @param The string. 
    */
   public String toString()
   {
      return "Generator - content: " + getContent() + 
      " version: " + getVersion() + 
      " uri: " + getUri();
   }

}