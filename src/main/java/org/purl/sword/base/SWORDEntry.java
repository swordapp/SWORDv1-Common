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
import nu.xom.Elements;

import org.apache.log4j.Logger;
import org.purl.sword.atom.Entry;

/**
 * Extension of the ATOM Entry class. This adds support for the additional 
 * SWORD elements. These elements reside inside the ATOM Entry object, 
 * created in org.w3.atom.Entry class. 
 * 
 * @author Neil Taylor
 */
public class SWORDEntry extends Entry 
{
   /**
    * Specifies whether the document was run in noOp mode, i.e. 
    * if the document records that no operation was taken for the 
    * deposit other than to generate a response. 
    */
   protected boolean noOp; 
   
   /**
    * Use to supply a verbose description. 
    */
   protected String verboseDescription; 
   
   /**
    * Used for a human readable statement about what treatment 
    * the deposited resource has received. Include either a
    * text description or a URI. 
    */
   protected String treatment;
   
   /**
    * Used to determine if the noOp value has been set.
    */
   protected boolean noOpSet; 
   
   /**
    * The user agent
    */
   protected String userAgent;
   
   /**
    * The packaging format
    */
   protected String packaging;

   /**
    * The logger.
    */
   private static Logger log = Logger.getLogger(SWORDEntry.class);
   
   /**
    * Create a new SWORDEntry with the given namespace and element. This method is
    * not normally used, instead the default constructor should be used as this will
    * set the namespace and element correctly.
    * 
    * @param namespace The namespace of the element
    * @param element The element name
    */
   public SWORDEntry(String namespace, String element)
   {
	   super(namespace, element);
   }
   
   /**
    * A default constructor.
    */
   public SWORDEntry()
   {
   }
   
   /**
    * Get the current value of NoOp.  
    * 
    * @return True if the value is set, false otherwise. 
    */
   public boolean isNoOp()
   {
      return noOp;
   }

   /**
    * Call this method to set noOp. It should be called even by internal 
    * methods so that the object can determine if the value has been set 
    * or whether it just holds the default value.  
    * 
    * @param noOp
    */
   public void setNoOp(boolean noOp)
   {
      this.noOp = noOp;
      this.noOpSet = true;
   }
   
   /**
    * Determine if the noOp value has been set. This should be called 
    * if you want to know whether false for noOp means that it is the 
    * default value (i.e. no code has set it) or it is a value that
    * has been actively set. 
    * 
    * @return True if the value has been set. Otherwise, false. 
    */
   public boolean isNoOpSet()
   {
      return noOpSet;
   }

   /**
    * Get the Verbose Description for this entry. 
    * 
    * @return The description. 
    */
   public String getVerboseDescription()
   {
      return verboseDescription;
   }

   /**
    * Set the verbose description. 
    * 
    * @param verboseDescription The description. 
    */
   public void setVerboseDescription(String verboseDescription)
   {
      this.verboseDescription = verboseDescription;
   }

   /**
    * Get the treatment value. 
    * 
    * @return The treatment. 
    */
   public String getTreatment()
   {
      return treatment;
   }

   /**
    * Set the treatment value. 
    *  
    * @param treatment The treatment. 
    */
   public void setTreatment(String treatment)
   {
      this.treatment = treatment;
   }
   
   /**
    * Get the user agent
    * 
    * @return the user agent
    */
   public String getUserAgent() 
   {
	   return userAgent;
   }
   
   /**
    * Set the user agent
    * 
    * @param userAgent the user agent
    */
   public void setUserAgent(String userAgent)
   {
	   this.userAgent = userAgent;
   }
   
   /**
    * Get the packaging format
    * 
    * @return the packaging format
    */
   public String getPackaging()
   {
	   return packaging;
   }
   
   /**
    * Set the packaging format
    * 
    * @param packaging the packaging format
    */
   public void setPackaging(String packaging)
   {
	   this.packaging = packaging;
   }
   
   /**
    * Overrides the marshall method in the parent Entry. This will 
    * call the parent marshall method and then add the additional 
    * elements that have been added in this subclass.  
    */
   public Element marshall()
   {
      Element entry = super.marshall(); 
      return entry;
   }
   
   protected void marshallElements(Element entry)
   {
	   super.marshallElements(entry);
	   
	   if (treatment != null)
	      {
	         Element treatmentElement = new Element(
	        		 Namespaces.PREFIX_SWORD + ":treatment", Namespaces.NS_SWORD);
	         treatmentElement.appendChild(treatment);
	         entry.appendChild(treatmentElement); 
	      }
	      
	      if (verboseDescription != null)
	      {
	         Element verboseDescriptionElement = new Element(
	        		 Namespaces.PREFIX_SWORD + ":verboseDescription", Namespaces.NS_SWORD);
	         verboseDescriptionElement.appendChild(verboseDescription);
	         entry.appendChild(verboseDescriptionElement);
	      }
	      
	      if (noOpSet)
	      {
	         Element noOpElement = new Element(
	        		 Namespaces.PREFIX_SWORD + ":noOp", Namespaces.NS_SWORD);
	         noOpElement.appendChild(Boolean.toString(noOp));
	         entry.appendChild(noOpElement);
	      }
	      
	      if (userAgent != null)
	      {
	         Element userAgentElement = new Element(
	        		 Namespaces.PREFIX_SWORD + ":userAgent", Namespaces.NS_SWORD);
	         userAgentElement.appendChild(userAgent);
	         entry.appendChild(userAgentElement);
	      }
	      
	      if (packaging != null)
	      {
	         Element packagingElement = new Element(
	        		 Namespaces.PREFIX_SWORD + ":packaging", Namespaces.NS_SWORD);
	         packagingElement.appendChild(packaging);
	         entry.appendChild(packagingElement);
	      }
   }

   /**
    * Overrides the unmarshall method in the parent Entry. This will 
    * call the parent method to parse the general Atom elements and
    * attributes. This method will then parse the remaining sword
    * extensions that exist in the element. 
    * 
    * @param entry The entry to parse. 
    * 
    * @throws UnmarshallException If the entry is not an atom:entry 
    *              or if there is an exception extracting the data. 
    */
   public void unmarshall(Element entry)
   throws UnmarshallException
   {
      super.unmarshall(entry);
      
      // retrieve all of the sub-elements
      Elements elements = entry.getChildElements();
      Element element = null; 
      int length = elements.size();

      for(int i = 0; i < length; i++ )
      {
    	  element = elements.get(i);

    	  if (isInstanceOf(element, "treatment", Namespaces.NS_SWORD))
    	  {
    		  try
    		  {
    			  treatment = unmarshallString(element);
    		  }
    		  catch( UnmarshallException ume )
    		  {
    			  log.error("Error accessing the content for the treatment element");
    		  }
    	  }
    	  else if (isInstanceOf(element, "noOp", Namespaces.NS_SWORD))
    	  {
    		  try
    		  {
    			  setNoOp(unmarshallBoolean(element));
    		  }
    		  catch( UnmarshallException ume )
    		  {
    			  log.error("Error accessing the boolean value for noOp");
    		  }
    	  }
    	  else if (isInstanceOf(element, "verboseDescription", Namespaces.NS_SWORD))
    	  {
    		  try
    		  {
    			  verboseDescription = unmarshallString(element);
    		  }
    		  catch( UnmarshallException ume ) 
    		  {
    			  log.error("Error accessing the content for the verboseDescription element");
    		  }
    	  }
    	  else if (isInstanceOf(element, "userAgent", Namespaces.NS_SWORD))
    	  {
    		  try
    		  {
    			  userAgent = unmarshallString(element);
    		  }
    		  catch( UnmarshallException ume ) 
    		  {
    			  log.error("Error accessing the content for the userAgent element");
    		  }
    	  }
    	  else if (isInstanceOf(element, "packaging", Namespaces.NS_SWORD))
    	  {
    		  try
    		  {
    			  packaging = unmarshallString(element);
    		  }
    		  catch( UnmarshallException ume ) 
    		  {
    			  log.error("Error accessing the content for the userAgent element");
    		  }
    	  }
      } // for
   }   
}