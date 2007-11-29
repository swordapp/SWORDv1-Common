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
package org.purl.sword.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import org.purl.sword.base.Namespaces;

import nu.xom.Element; 
import nu.xom.Elements;

import org.apache.log4j.Logger;


/**
 * Represents an Atom Publishing Protocol Service element, with 
 * SWORD extensions. 
 * 
 * @author Neil Taylor
 */
public class Service extends XmlElement implements SwordElementInterface
{
   /**
    * The service compliance level. 
    */
   private ServiceLevel complianceLevel; 
   
   /**
    * The noOp value. 
    */
   private boolean noOp; 
   
   /**
    * Flag to record if the noOp value has been set by a user of the class. 
    */
   private boolean isNoOp; 
   
   /**
    * The verbose value. 
    */
   private boolean verbose;
   
   /**
    * Flag to record if the verbose value has been set by a user of the class. 
    */
   private boolean isVerbose; 
   
   /**
    * List of Workspaces. 
    */
   private List<Workspace> workspaces; 
   
   /** Logger */
   private static Logger log = Logger.getLogger(Service.class);
   
   /**
    * Local name part of level element. 
    */
   public static final String ELEMENT_SWORD_LEVEL = "level";
   
   /**
    * Local name part of verbose element.
    */
   public static final String ELEMENT_SWORD_VERBOSE = "verbose";
   
   /**
    * Local name part of noOp element.
    */
   public static final String ELEMENT_SWORD_NO_OP = "noOp";
   
   /**
    * Name for this element. 
    */
   public static final String ELEMENT_NAME = "service";
   
   /**
    * Create a new instance. 
    */
   public Service()
   {
      super(ELEMENT_NAME);
      
      isVerbose = false;
      isNoOp = false; 
      workspaces = new ArrayList<Workspace>();
      complianceLevel = ServiceLevel.UNDEFINED;
   }
   
   /**
    * Create a new instance. 
    * 
    * @param complianceLevel The service compliance level. 
    */
   public Service( ServiceLevel complianceLevel)
   {
      this();
      this.complianceLevel = complianceLevel;
   }
   
   /**
    * Create a new instance with the specified compliance level, noOp and 
    * verbose values. 
    * 
    * @param complianceLevel  The service compliance level. 
    * @param noOp             The noOp.
    * @param verbose          The verbose element. 
    */
   public Service( ServiceLevel complianceLevel, boolean noOp, boolean verbose ) 
   {
      this();
      this.complianceLevel = complianceLevel; 
      setNoOp(noOp);
      setVerbose(verbose);
   }

   /**
    * Get the service compliance level. 
    * 
    * @return The compliance level. 
    */
   public ServiceLevel getComplianceLevel()
   {
      return complianceLevel;
   }

   /**
    * Set the service compliance level. 
    * 
    * @param The compliance level. 
    */
   public void setComplianceLevel(ServiceLevel complianceLevel)
   {
      this.complianceLevel = complianceLevel;
   }

   /**
    * Get the NoOp value. 
    * 
    * @return The value. 
    */
   public boolean isNoOp()
   {
      return noOp;
   }

   /**
    * Set the NoOp value. 
    * 
    * @param noOp The value. 
    */
   public void setNoOp(boolean noOp)
   {
      this.noOp = noOp;
      isNoOp = true;
   }
   
   /**
    * Determine if the NoOp value has been set. This should be called to 
    * check if an item has been programatically set and does not have a
    * default value. 
    * 
    * @return True if it has been set programmatically. Otherwise, false. 
    */
   public boolean isNoOpSet()
   {
      return isNoOp; 
   }

   /**
    * Get the Verbose setting. 
    * 
    * @return The value. 
    */
   public boolean isVerbose()
   {
      return verbose;
   }

   /**
    * Set the Verbose value. 
    * 
    * @param verbose The value. 
    */
   public void setVerbose(boolean verbose)
   {
      this.verbose = verbose;
      isVerbose = true;  
   }

   /**
    * Determine if the Verbose value has been set. This should be called to 
    * check if an item has been programatically set and does not have a
    * default value. 
    * 
    * @return True if it has been set programmatically. Otherwise, false. 
    */
   public boolean isVerboseSet()
   {
      return isVerbose; 
   }
   
   /**
    * Get an Iterator over the workspaces. 
    * 
    * @return The workspace. 
    */
   public Iterator<Workspace> getWorkspaces()
   {
      return workspaces.iterator();
   }
   
   /**
    * Get a List of workspaces
    * 
    * @return The workspaces in a List
    */
   public List<Workspace> getWorkspacesList()
   {
	   return workspaces;
   }

   /**
    * Add a workspace. 
    * 
    * @param workspace The workspace. 
    */
   public void addWorkspace(Workspace workspace)
   {
      this.workspaces.add(workspace);
   }
   
   /**
    * Clear the list of workspaces. 
    */
   public void clearWorkspaces()
   {
	   this.workspaces.clear();
   }
   
   /**
    * Marshall the data in this object to an Element object. 
    * 
    * @return A XOM Element that holds the data for this Content element. 
    */
   public Element marshall( )
   {
      Element service = new Element(getQualifiedName(), Namespaces.NS_APP);
      service.addNamespaceDeclaration(Namespaces.PREFIX_ATOM, Namespaces.NS_ATOM);
      service.addNamespaceDeclaration(Namespaces.PREFIX_DC_TERMS, Namespaces.NS_DC_TERMS);
      service.addNamespaceDeclaration(Namespaces.PREFIX_SWORD, Namespaces.NS_SWORD);
      
      if( complianceLevel != ServiceLevel.UNDEFINED )
      {
    	   Element compliance = new Element(
    			   Namespaces.PREFIX_SWORD + ":" + ELEMENT_SWORD_LEVEL, Namespaces.NS_SWORD);
         compliance.appendChild(Integer.toString(complianceLevel.number()));
         service.appendChild(compliance);
      }
      
      if( isVerboseSet() )
      {
         Element verboseElement = new Element(
        		 Namespaces.PREFIX_SWORD + ":" + ELEMENT_SWORD_VERBOSE, Namespaces.NS_SWORD); 
         verboseElement.appendChild(Boolean.toString(verbose));
         service.appendChild(verboseElement);
      }
      
      if( isNoOpSet() ) 
      {
         Element noOpElement = new Element(
        		 Namespaces.PREFIX_SWORD + ":" + ELEMENT_SWORD_NO_OP, Namespaces.NS_SWORD);
         noOpElement.appendChild(Boolean.toString(noOp)); 
         service.appendChild(noOpElement);
      }
      
      for( Workspace item : workspaces )
      {
         service.appendChild(item.marshall());
      }
      return service;    
   }
   
   
   /**
    * Get a service level that corresponds to the specified value. Used 
    * during the unmarshall process. 
    * 
    * @param level The integer version of a service level. 
    * 
    * @return If the parameter matches one of the defined levels, 
    *    a ServiceLevel of ONE or ZERO is returned. Otherwise, 
    *    ServiceLevel.UNDEFINED is returned. 
    */
   private ServiceLevel getServiceLevel( int level ) 
   {
      ServiceLevel theLevel = ServiceLevel.UNDEFINED; 
      
      switch( level )
      {
         case 0: 
            theLevel = ServiceLevel.ZERO;
            break;
               
         case 1: 
            theLevel = ServiceLevel.ONE;
            break;
               
         default: 
            theLevel = ServiceLevel.UNDEFINED;
            log.error("Invalid value for sword:level" );
            break;
      }
      
      return theLevel; 
   }
     
   /**
    * Unmarshall the content element into the data in this object. 
    * 
    * @throws UnmarshallException If the element does not contain a
    *                             content element or if there are problems
    *                             accessing the data. 
    */
   public void unmarshall( Element service )
   throws UnmarshallException
   {
      if( ! isInstanceOf(service, localName, Namespaces.NS_APP))
      {
         throw new UnmarshallException( "Not an app:service element" );
      }
      
      try
      {
         workspaces.clear(); 
         
         // retrieve all of the sub-elements
         Elements elements = service.getChildElements();
         Element element = null; 
         int length = elements.size();
         
         for(int i = 0; i < length; i++ )
         {
            element = elements.get(i);

            if( isInstanceOf(element, ELEMENT_SWORD_LEVEL, Namespaces.NS_SWORD ) )
            {
               int level = unmarshallInteger(element); 
               complianceLevel = getServiceLevel(level);
            }
            else if( isInstanceOf(element, ELEMENT_SWORD_VERBOSE, Namespaces.NS_SWORD))
            {
               setVerbose(unmarshallBoolean(element)); 
            }
            else if( isInstanceOf(element, ELEMENT_SWORD_NO_OP, Namespaces.NS_SWORD))
            {
               setNoOp(unmarshallBoolean(element));
            }
            else if( isInstanceOf(element, Workspace.ELEMENT_NAME, Namespaces.NS_APP ))
            {
               Workspace workspace = new Workspace( );
               workspace.unmarshall(element);
               workspaces.add(workspace);
            }
         }
      }
      catch( Exception ex )
      {
         log.error("Unable to parse an element in Service: " + ex.getMessage());
         throw new UnmarshallException("Unable to parse element in Service", ex);
      }
   }
}