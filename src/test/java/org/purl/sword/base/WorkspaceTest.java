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
import java.util.Properties;

/**
 *
 * @author Peter Todd (ppt@aber.ac.uk)
 */
public class WorkspaceTest
{

    private static String TEST_LOCATION = "location example";
    private static String TEST_TITLE = "title example";
    private static String TEST_ACCEPT = "test accept ";
    private static int TEST_ACCEPTS = 5;
    private static boolean TEST_MEDIATION = true;

    @Test
    public void createTest()
    {
       Workspace workspace = new Workspace();

       //Create a test collection
       Collection collection = new Collection();
       
       workspace.addCollection(collection);
       workspace.setTitle(TEST_TITLE);

       Assert.assertEquals(workspace.getCollections().size(), 1 );
       Assert.assertEquals(workspace.getTitle(), TEST_TITLE );
    
    }

    @Test
    public void testNullTitle()
    {
       Workspace workspace = new Workspace();

       //Create a valid test collection
       Collection collection = new Collection();
       collection.setLocation(TEST_LOCATION);
       collection.addAccepts(TEST_ACCEPT + "0");
       collection.setMediation(true);

       workspace.addCollection(collection);

       Assert.assertEquals(true,testWorkspace(workspace, SwordValidationInfoType.VALID));
    }

    @Test
    public void testValidCollection()
    {
       Workspace workspace = new Workspace();
       workspace.setTitle(TEST_TITLE);
       //Create an valid test collection
       Collection collection = new Collection();
       collection.addAccepts(TEST_ACCEPT + "0");
       collection.setLocation(TEST_LOCATION);
       collection.setMediation(true);

       workspace.addCollection(collection);

       Assert.assertEquals(true,testWorkspace(workspace, SwordValidationInfoType.VALID));
    }

    @Test
    public void testInvalidCollection()
    {
       Workspace workspace = new Workspace();
       workspace.setTitle(TEST_TITLE);
       //Create an invalid test collection - no Accepts
       Collection collection = new Collection();
       collection.setLocation(TEST_LOCATION);
       collection.setMediation(true);

       workspace.addCollection(collection);

       Assert.assertEquals(true,testWorkspace(workspace, SwordValidationInfoType.WARNING));
    }

    @Test
    public void testNoCollections()
    {
       Workspace workspace = new Workspace();
       workspace.setTitle(TEST_TITLE);
       Assert.assertEquals(true,testWorkspace(workspace, SwordValidationInfoType.WARNING));
    }


   public boolean testWorkspace(Workspace workspace, SwordValidationInfoType expectedResult)
    {
       SwordValidationInfo info = workspace.validate(new Properties());

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
