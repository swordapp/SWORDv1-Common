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

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;

import org.apache.log4j.Logger;
import org.purl.sword.atom.ContentType;
import org.purl.sword.atom.Title;

/**
 * A representation of a SWORD Collection.
 * 
 * @author Stuart Lewis
 * @author Neil Taylor
 */
public class Collection extends XmlElement implements SwordElementInterface
{
   /** 
    * Collection location, expressed as a URL. 
    */
   private String location;

   /**
    * Holds the ATOM Title for the collection. 
    */
   private Title title;

   /** 
    * List of the APP:Accept elements. 
    */
   private List<String> accepts;

   /**
    * Holds the SWORD Collection policy. 
    */
   private String collectionPolicy; 

   /** 
    * The SWORD mediation value. Indicates if mediation is allowed. 
    */
   private boolean mediation;

   /**
    * Internal value to track if the mediation value has been 
    * set programmatically. 
    */
   private boolean mediationSet; 

   /**
    * The SWORD treatment value. 
    */
   private String treatment;

   /** 
    * The SWORD namespace. 
    */
   private String namespace;

   /**
    * The DC Terms Abstract details. 
    */
   private String dcAbstract; 
   
   /**
    * The SWORD service (nested service document) details. 
    */
   private String service; 
   
   /**
    * The SWORD acceptsPackaging details.
    */
   private Hashtable<String, QualityValue> acceptPackaging;

   /**
    * The logger. 
    */
   private static Logger log = Logger.getLogger(Collection.class);

   /**
    * Local name for the app accept element. 
    */
   public static final String ELEMENT_APP_ACCEPT = "accept";
   
   /**
    * Local name for the sword collectionPolicy element. 
    */
   public static final String ELEMENT_SWORD_COLLECTION_POLICY = "collectionPolicy";
   
   /**
    * Local name for the DC terms abstract element. 
    */
   public static final String ELEMENT_DC_TERMS_ABSTRACT = "abstract";
   
   /**
    * Local name for the sword service element. 
    */
   public static final String ELEMENT_SWORD_SERVICE = "service";
   
   /**
    * Local name for the sword mediation element. 
    */
   public static final String ELEMENT_SWORD_MEDIATION = "mediation"; 
   
   /**
    * Local name for the sword treatment element. 
    */
   public static final String ELEMENT_SWORD_TREATMENT = "treatment";

   /**
    * Local name for the sword formatNamespace element. 
    */
   public static final String ELEMENT_SWORD_FORMAT_NAMESPACE = "formatNamespace";

   /**
    * Local name for the sword acceptPackaging element. 
    */
   public static final String ELEMENT_SWORD_ACCEPT_PACKAGING = "acceptPackaging";
   
   /**
    * Label for the Href attribute.  
    */
   public static final String ATTRIBUTE_HREF = "href";
   
   /**
    * Label for the local part of this element. 
    */
   public static final String ELEMENT_NAME = "collection";
   
   /**
    * Create a new instance.
    */
   public Collection()
   {
      super(ELEMENT_NAME);
      accepts = new ArrayList<String>();
      acceptPackaging = new Hashtable<String, QualityValue>();
      mediationSet = false; 
   }

   /**
    * Create a new instance and set the initial location for the collection. 
    * 
    * @param location The initial location, expressed as a URL. 
    */
   public Collection(String location) 
   {
      super(null);
      this.location = location;
   }

   /**
    * Retrieve an array that holds all of the Accept details. 
    * 
    * @return An array of strings. Each string represents an 
    *         individual accept element. The array will have a length
    *         of 0 if no accepts elements are stored in this collection. 
    */
   public String[] getAccepts() 
   {
      String[] values = new String[this.accepts.size()];
      return (String[])accepts.toArray(values);
   }

   /**
    * Retrieve an array that holds all of the Accept details. 
    * 
    * @return An array of strings. Each string represents an 
    *         individual accept element. The array will have a length
    *         of 0 if no accepts elements are stored in this collection. 
    */
   public List<String> getAcceptsList() 
   {
      return accepts;
   }   

   /**
    * Add an accepts entry. 
    * 
    * @param accepts The accepts value. 
    */
   public void addAccepts(String accepts) {
      this.accepts.add(accepts);
   }

   /**
    * Remove all of the accepts associated with this Collection. 
    */
   public void clearAccepts( )
   {
      this.accepts.clear();
   }

   /**
    * Retrieve a hashtable that holds all the acceptsPackaging details. 
    * 
    * @return A hashtable. The keys are accepted packaging formats,
    * 	      and the values the quality values (stored as QualityValue objects)
    */
   public Hashtable getAcceptPackaging() 
   {
      return acceptPackaging;
   }

   /**
    * Add an acceptPackaging format. 
    * 
    * @param acceptPackaging the packaging format.
    * @param qualityValue the quality value of accepted packaging format.
    */
   public void addAcceptPackaging(String acceptPackaging, float qualityValue) {
	   this.acceptPackaging.put(acceptPackaging, new QualityValue(qualityValue));
   }

   /**
    * Add an acceptPackaging format. A default quality vale is given.
    * 
    * @param acceptPackaging the packaging format.
    */
   public void addAcceptPackaging(String acceptPackaging) {
	   this.acceptPackaging.put(acceptPackaging, new QualityValue());
   }

   /**
    * Remove all of the accepted packaging formats associated with this Collection. 
    */
   public void clearAcceptPackaging( )
   {
      this.acceptPackaging.clear();
   }

   /**
    * Get the collection policy. 
    * 
    * @return The SWORD collectionPolicy.
    */
   public String getCollectionPolicy() 
   {
      return collectionPolicy;
   }

   /**
    * Set the collection policy. 
    * 
    * @param collectionPolicy The collection policy.
    */
   public void setCollectionPolicy(String collectionPolicy) 
   {
      this.collectionPolicy = collectionPolicy;
   }

   /**
    * Get the location. 
    * 
    * @return TShe location
    */
   public String getLocation() {
      return location;
   }

   /**
    * Set the location. 
    * 
    * @param location The location.
    */
   public void setLocation(String location) {
      this.location = location;
   }

   /**
    * Get the mediation value. 
    * 
    * @return The mediation
    */
   public boolean getMediation() {
      return mediation;
   }

   /**
    * Set the mediation value. 
    * 
    * @param mediation The mediation value. 
    */
   public void setMediation(boolean mediation) {
      this.mediation = mediation;
      mediationSet = true;
   }

   /**
    * Get the format namespace. 
    * 
    * @return The format namespace. 
    */
   public String getFormatNamespace()
   {
      return namespace;
   }

   /**
    * Set the format namespace. 
    * 
    * @param namespace The namespace. 
    */
   public void setFormatNamespace(String namespace)
   {
      this.namespace = namespace; 
   }

   /**
    * Get the DC Term abstract.
    *  
    * @return The abstract. 
    */
   public String getAbstract()
   {
      return dcAbstract;   
   }

   /**
    * Set the abstract. 
    * 
    * @param abstractString The abstract. 
    */
   public void setAbstract(String abstractString)
   {
      this.dcAbstract = abstractString;
   }

   /**
    * Get the sword service.
    *  
    * @return The service. 
    */
   public String getService()
   {
      return service;   
   }

   /**
    * Set the sword service. 
    * 
    * @param serviceString The service. 
    */
   public void setService(String serviceString)
   {
      this.service = serviceString;
   }

   /**
    * Set the title. This will set the title type to ContentType.TEXT. 
    * 
    * @param title The title. 
    */
   public void setTitle( String title )
   {
      if( this.title == null)
      {
         this.title = new Title();
      }
      this.title.setContent(title);
      this.title.setType(ContentType.TEXT);
   }

   /**
    * Get the title. 
    * 
    * @return The title, or <code>null</code> if no title has been set. 
    */
   public String getTitle( )
   {
      if( title == null ) 
      {
         return null;
      }
      return title.getContent(); 
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
    * Set the treatment. 
    * 
    * @param treatment The treatment.
    */
   public void setTreatment(String treatment) 
   {
      this.treatment = treatment;
   }  

   /**
    * Get a string representation of this object. This is 
    * equivalent to calling marshall().toString().
    */
   public String toString()
   {
      Element element = marshall(); 
      return element.toString(); 
   }

   /**
    * Marshall the data in this object to an Element object. 
    * 
    * @return A XOM Element that holds the data for this Content element. 
    */
   public Element marshall( )
   {
      // convert data into XOM elements and return the 'root', i.e. the one 
      // that represents the collection. 
      Element collection = new Element(getQualifiedName(), Namespaces.NS_APP);
      Attribute href = new Attribute(ATTRIBUTE_HREF, location);
      collection.addAttribute(href);

      collection.appendChild(title.marshall());

      Element acceptsElement = null; 
      for (String item:accepts)
      {
    	  acceptsElement = new Element(ELEMENT_APP_ACCEPT, Namespaces.NS_APP);
    	  acceptsElement.appendChild(item);
    	  collection.appendChild(acceptsElement);
      }
      
      Element acceptPackagingElement = null;
      Enumeration<String> apEnum = acceptPackaging.keys();
      while (apEnum.hasMoreElements())
      {
    	  String packagingFormat = apEnum.nextElement();
    	  acceptPackagingElement = new Element(Namespaces.PREFIX_SWORD + ":" + ELEMENT_SWORD_ACCEPT_PACKAGING, Namespaces.NS_SWORD);
    	  Attribute apAttr = new Attribute("q", "" + acceptPackaging.get(packagingFormat));
    	  acceptPackagingElement.addAttribute(apAttr);
    	  acceptPackagingElement.appendChild(packagingFormat);
    	  collection.appendChild(acceptPackagingElement);
      }

      if (collectionPolicy != null)
      {
         Element colPolicyElement = new Element(Namespaces.PREFIX_SWORD + ":" + 
        		 ELEMENT_SWORD_COLLECTION_POLICY, Namespaces.NS_SWORD);
         colPolicyElement.appendChild(collectionPolicy);
         collection.appendChild(colPolicyElement);
      }

      if (dcAbstract != null)
      {
         Element dcAbstractElement = new Element(Namespaces.PREFIX_DC_TERMS + ":" + 
        		 ELEMENT_DC_TERMS_ABSTRACT, Namespaces.NS_DC_TERMS);
         dcAbstractElement.appendChild(dcAbstract);
         collection.appendChild(dcAbstractElement);
      }

      if (service != null)
      {
         Element serviceElement = new Element(Namespaces.PREFIX_SWORD + ":" + 
        		 ELEMENT_SWORD_SERVICE, Namespaces.NS_SWORD);
         serviceElement.appendChild(service);
         collection.appendChild(serviceElement);
      }

      if (mediationSet)
      {
         Element mediationElement = new Element(Namespaces.PREFIX_SWORD + ":" + 
        		 ELEMENT_SWORD_MEDIATION, Namespaces.NS_SWORD);
         mediationElement.appendChild(Boolean.toString(mediation));
         collection.appendChild(mediationElement);
      }

      // treatment
      if (treatment != null)
      {
         Element treatmentElement = new Element(Namespaces.PREFIX_SWORD + ":" + 
        		 ELEMENT_SWORD_TREATMENT, Namespaces.NS_SWORD);
         treatmentElement.appendChild(treatment);
         collection.appendChild(treatmentElement);
      }

      // namespace 
      if (namespace != null)
      {
         Element namespaceElement = new Element(Namespaces.PREFIX_SWORD + ":" + 
        		 ELEMENT_SWORD_FORMAT_NAMESPACE, Namespaces.NS_SWORD);
         namespaceElement.appendChild(namespace);
         collection.appendChild(namespaceElement);
      }

      return collection; 
   }

   /**
    * Unmarshall the content element into the data in this object. 
    * 
    * @throws UnmarshallException If the element does not contain a
    *                             content element or if there are problems
    *                             accessing the data. 
    */
   public void unmarshall(Element collection)
   throws UnmarshallException 
   {
      if (!isInstanceOf(collection, localName, Namespaces.NS_APP))
      {
         log.error("Unexpected element. Expected app:collection. Got " 
               + ((collection != null) ? collection.getQualifiedName() : "null" ));
         throw new UnmarshallException( "Not an app:collection element" );
      }

      try
      {
         // retrieve the attributes
         int count = collection.getAttributeCount(); 
         Attribute a = null;
         for( int i = 0; i < count; i++ ) 
         {
            a = collection.getAttribute(i);
            if (ATTRIBUTE_HREF.equals(a.getQualifiedName()))
            {
               location = a.getValue();
            }
         }

         clearAccepts();
         clearAcceptPackaging();

         // retrieve all of the sub-elements
         Elements elements = collection.getChildElements();
         Element element = null; 
         int length = elements.size();

         for (int i = 0; i < length; i++)
         {
            element = elements.get(i);
            if (isInstanceOf(element, Title.ELEMENT_NAME, Namespaces.NS_ATOM))
            {
               title = new Title();
               title.unmarshall(element);   
            }
            else if (isInstanceOf(element, ELEMENT_APP_ACCEPT, Namespaces.NS_APP))
            {
               try
               {
                  String acceptsItem = unmarshallString(element);
                  accepts.add(acceptsItem);
               }
               catch(UnmarshallException ume)
               {
                  log.error("Error accessing the content of the accepts element");
               }
            }
            else if (isInstanceOf(element, ELEMENT_SWORD_ACCEPT_PACKAGING, Namespaces.NS_SWORD))
            {
            	try
            	{
            		String acceptsFormat = unmarshallString(element);
                  	if (element.getAttribute("q") != null)
                  	{
                  		float qv = Float.parseFloat(element.getAttribute("q").getValue());
                  		addAcceptPackaging(acceptsFormat, qv);
                  	}
                  	else
                  	{
                  		addAcceptPackaging(acceptsFormat);
                  	}
            	}
            	catch (NumberFormatException e)
            	{
             		log.error("Error accessing the q value of the acceptPackaging element");
            	}
            	catch( UnmarshallException ume )
            	{
                  log.error("Error accessing the content of the acceptPackaging element");
            	}
            }
            else if (isInstanceOf(element, ELEMENT_SWORD_COLLECTION_POLICY, Namespaces.NS_SWORD))
            {
               try
               {
                  collectionPolicy = unmarshallString(element);
               }
               catch( UnmarshallException ume)
               {
                  log.error("Error accessing the content for the collectionPolicy element.");
               }
            }
            else if (isInstanceOf(element, ELEMENT_DC_TERMS_ABSTRACT, Namespaces.NS_DC_TERMS))
            {
               try
               {
                  dcAbstract = unmarshallString(element);
               }
               catch( UnmarshallException ume )
               {
                  log.error("Error accessing the Dublin Core Abstract element.");
               }
            }
            else if (isInstanceOf(element, ELEMENT_SWORD_SERVICE, Namespaces.NS_SWORD))
            {
               try
               {
                  service = unmarshallString(element);
               }
               catch( UnmarshallException ume )
               {
                  log.error("Error accessing the SWORD service element.");
               }
            }
            else if (isInstanceOf(element, ELEMENT_SWORD_MEDIATION, Namespaces.NS_SWORD))
            {
               try
               {
                  setMediation(unmarshallBoolean(element));
               }
               catch( UnmarshallException ume )
               {
                  log.error("Error accessing the boolean value for the mediation element.");
               }
            }
            else if (isInstanceOf(element, ELEMENT_SWORD_TREATMENT, Namespaces.NS_SWORD))
            {
               try
               {
                  treatment = unmarshallString(element);
               }
               catch(UnmarshallException ume)
               {
                  log.error("Error accessing the content for the treatment element");
               }
            }
            else if (isInstanceOf(element, ELEMENT_SWORD_FORMAT_NAMESPACE, Namespaces.NS_SWORD))
            {
               try
               {
                  namespace = unmarshallString(element);
               }
               catch( UnmarshallException ume )
               {
                  log.error("Error accessing the content for the namespace element");
               }
            }
         }
      }
      catch (Exception ex)
      {
         log.error("Unable to parse an element in collection: " + ex.getMessage());
         throw new UnmarshallException("Unable to parse an element in Collection", ex);
      }

   }
}