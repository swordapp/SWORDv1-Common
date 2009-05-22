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

import org.junit.*;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.ParsingException;

import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import org.purl.sword.atom.Generator;
/**
 *
 * @author Neil Taylor (nst@aber.ac.uk)
 * @author Peter Todd (ppt@aber.ac.uk)
 */
public class ServiceTest {

    private String serviceStart = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n" +
                "<service xmlns=\"http://www.w3.org/2007/app\"\n"  +
                "         xmlns:atom=\"http://www.w3.org/2005/Atom\"\n" +
                "         xmlns:sword=\"http://purl.org/net/sword/\"\n" +
                "         xmlns:dcterms=\"http://purl.org/dc/terms/\">\n";

    private String versionElement = "   <sword:version>1.3</sword:version>\n";

    private String verboseElement = "   <sword:verbose>true</sword:verbose>\n";

    private String noOpElement = "   <sword:noOp>true</sword:noOp>\n";

    private String maxUploadSizeElement = "   <sword:maxUploadSize>10</sword:maxUploadSize>\n";

    private String generatorElement = "   <atom:generator uri=\"somewhere\" version=\"1.0\" />\n";

    private String workspaceElement = "   <workspace>\n" +
                                      "      <atom:title>this is the next</atom:title>\n" +
                                      "      <something>test me in here</something>\n" +
                                      "      <collection>\n" +
                                      "         <atom:title test=\"one two three\" other=\"b\">this is the title</atom:title>\n" +
                                      "         <accept>application/zip</accept>\n" +
                                      "         <sword:collectionPolicy>example collection policy</sword:collectionPolicy>\n" +
                                      "         <dcterms:abstract>Collection description</dcterms:abstract>\n" +
                                      "         <sword:acceptPackaging q=\"0.9090\">http://purl.org/net/sword-types/bagit/</sword:acceptPackaging>\n" +
                                      "      </collection>\n" +
                                      "   </workspace>\n";

    private String serviceEnd = "</service>";


    private static String TEST_VERSION = "test service";
    private static int TEST_MAX_UPLOAD_SIZE = 100;

    //These definitions are used to create a valid workspace to add to a test service
    private static String TEST_LOCATION = "location example";
    private static String TEST_TITLE = "title example";
    private static String TEST_ACCEPT = "test accept ";
    private static int TEST_ACCEPTS = 5;
    private static boolean TEST_MEDIATION = true;

    private static String TEST_GENERATOR_URI = "http://test";
    private static String TEST_GENERATOR_VERSION = "test version";
    private static String TEST_GENERATOR_CONTENT = "test content";

    /**
     * Utility method that will create an XML element that is loaded and ready
     * to be unmarshalled.
     *
     * @return An element that is the XOM representation of the XML string. 
     *
     * @throws nu.xom.ParsingException
     * @throws java.io.IOException
     * @throws org.purl.sword.base.UnmarshallException
     */
    public Element createElementForTest()
    throws ParsingException, IOException, UnmarshallException 
    {
        String xml = serviceStart +
                     //versionElement +
                     verboseElement +
                     noOpElement +
                     maxUploadSizeElement +
                     generatorElement +
                     workspaceElement +
                     //workspaceElement +
                     serviceEnd;

        System.out.println(xml);

        Builder builder = new Builder();
		Document doc = builder.build(xml, "http://something.com/here");
        return doc.getRootElement();
    }

    @Test
    public void unmarshallWithValidationTest()
    throws ParsingException, IOException, UnmarshallException
    {
       Element element = createElementForTest();
       Service service = new Service();
       SwordValidationInfo info = service.unmarshall(element, new Properties());
       System.out.println("the test is: " + info);
       printValues(info, "");

       assert(true);

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


    @Test
    public void testCreate()
    {
        Service service = new Service();
        service.setVersion(TEST_VERSION);
        service.setVerbose(true);
        service.setNoOp(true);
        service.setMaxUploadSize(TEST_MAX_UPLOAD_SIZE);

        Generator generator = new Generator();
        Workspace workspace = new Workspace();

        service.addWorkspace(workspace);
        service.setGenerator(generator);

        Assert.assertEquals(service.getVersion(), TEST_VERSION);
        Assert.assertEquals(service.getMaxUploadSize(), TEST_MAX_UPLOAD_SIZE);
        Assert.assertSame(service.getGenerator(), generator);
        Assert.assertEquals(service.getWorkspacesList().size(), 1);
    }

    
    @Test
    public void testNullVersion()
    {
        Service service = createTestService(null, "true", "true", TEST_MAX_UPLOAD_SIZE, true, true, true, true);
        Assert.assertEquals(true, testService(service, SwordValidationInfoType.WARNING));
    }

    @Test
    public void testNullVerbose()
    {
        Service service = createTestService(TEST_VERSION, "null", "true", TEST_MAX_UPLOAD_SIZE, true, true, true, true);
        Assert.assertEquals(true, testService(service, SwordValidationInfoType.VALID));
    }

    @Test
    public void testNullNoOp()
    {
        Service service = createTestService(TEST_VERSION, "true", "null", TEST_MAX_UPLOAD_SIZE, true, true, true, true);
        Assert.assertEquals(true, testService(service, SwordValidationInfoType.VALID));
    }

    @Test
    public void testZeroUploadSize()
    {
        Service service = createTestService(TEST_VERSION, "true", "null", 0, true, true, true, true);
        Assert.assertEquals(true, testService(service, SwordValidationInfoType.VALID));
    }

    @Test
    public void testValidService()
    {
        Service service = createTestService(TEST_VERSION, "true", "true", TEST_MAX_UPLOAD_SIZE, true, true, true, true);
        Assert.assertEquals(true, testService(service, SwordValidationInfoType.VALID));
    }

    @Test
    public void testNoGenerator()
    {
        Service service = createTestService(TEST_VERSION, "true", "true", TEST_MAX_UPLOAD_SIZE, false, true, true, true);
        Assert.assertEquals(true, testService(service, SwordValidationInfoType.WARNING));
    }

    @Test
    public void testNoWorkspace()
    {
        Service service = createTestService(TEST_VERSION, "true", "true", TEST_MAX_UPLOAD_SIZE, true, false, true, true);
        Assert.assertEquals(true, testService(service, SwordValidationInfoType.WARNING));
    }

    @Test
    public void testInvalidGenerator()
    {
        Service service = createTestService(TEST_VERSION, "true", "true", TEST_MAX_UPLOAD_SIZE, true, true, false, true);
        Assert.assertEquals(true, testService(service, SwordValidationInfoType.WARNING));
    }

    @Test
    public void testInvalidWorkspace()
    {
        Service service = createTestService(TEST_VERSION, "true", "true", TEST_MAX_UPLOAD_SIZE, true, true, true, false);
        Assert.assertEquals(true, testService(service, SwordValidationInfoType.WARNING));
    }

    public Service createTestService(String version, String verbose, String NoOp,
            int maxUploadSize, boolean createGenerator, boolean createWorkspace, boolean validGenerator, boolean validWorkspace)
    {
        Service service = new Service();
        service.setVersion(version);

        if(verbose.equalsIgnoreCase("true"))
        {
            service.setVerbose(true);
        }
        else if(verbose.equalsIgnoreCase("false"))
        {
            service.setVerbose(false);
        }

        if(NoOp.equalsIgnoreCase("true"))
        {
            service.setNoOp(true);
        }
        else if(NoOp.equalsIgnoreCase("false"))
        {
            service.setNoOp(false);
        }

        service.setMaxUploadSize(maxUploadSize);

        if(createGenerator == true)
        {
            Generator generator = new Generator();
            if(validGenerator)
            {
                generator.setUri(TEST_GENERATOR_URI);
                generator.setVersion(TEST_GENERATOR_VERSION);
                generator.setContent(TEST_GENERATOR_CONTENT);
            }
            service.setGenerator(generator);
        }

        if(createWorkspace == true)
        {
            Workspace workspace = new Workspace();
            if(validWorkspace)
            {
               workspace.setTitle(TEST_TITLE);
               //Create an valid test collection
               Collection collection = new Collection();
               collection.addAccepts(TEST_ACCEPT + "0");
               collection.setLocation(TEST_LOCATION);
               collection.setMediation(true);

               workspace.addCollection(collection);

            }
            service.addWorkspace(workspace);
        }

        return service;
    }

    public boolean testService(Service service, SwordValidationInfoType expectedResult)
    {
       SwordValidationInfo info = service.validate(new Properties());

       System.out.print("Test is " + Thread.currentThread().getStackTrace()[2].getMethodName() + "   Expected Value was " + expectedResult.toString() + " Actual Value was " + info.getType().toString() + "       Test Result: ");

       if(!info.getType().equals(expectedResult))
       {
           System.out.println("FAILED");
           System.out.println("Details of the failed test:");
           StringBuffer buffer = new StringBuffer();
           info.createString(info, buffer, " ");
           System.out.println(buffer.toString());
           System.out.println("=================================================");
           return false;
       }
       System.out.println("PASSED");
       return true;
   }
}
