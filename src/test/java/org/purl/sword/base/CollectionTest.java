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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import org.junit.*;
import org.purl.sword.atom.Accept;

/**
 *
 * @author Peter Todd (ppt@aber.ac.uk)
 */
public class CollectionTest
{

    private static String TEST_LOCATION = "location example";

    private static String TEST_TITLE = "title example";

    private static String TEST_ACCEPT = "test accept ";

    private static int TEST_ACCEPTS = 5;

    private static boolean TEST_MEDIATION = true;

    private static String TEST_SERVICE = "service example";

    private static String TEST_ABSTRACT = "abstract example";

    private static String TEST_TREATMENT = "treatment example";

    private static String TEST_POLICY = "policy example";

    private static String TEST_ACCEPT_PACKAGING = "http://www.imsglobal.org/xsd/imscp_v1p1";

    private static int TEST_ACCEPT_PACKAGES = 5;

    @Test
    public void createTest()
    {

        Collection collection = new Collection();

        // SETUP THE TEST DATA
        //-------------------------------------------
        collection.setLocation(TEST_LOCATION);
        collection.setTitle(TEST_TITLE);

        //Build Accept test list
        List<String> testAccepts = new ArrayList<String>();
        for(int i = 1;i < TEST_ACCEPTS;i++)
        {
            collection.addAccepts(TEST_ACCEPT + Integer.toString(i));
            testAccepts.add(TEST_ACCEPT + Integer.toString(i));
        }
        collection.setMediation(TEST_MEDIATION);
        collection.setService(TEST_SERVICE);
        collection.setAbstract(TEST_ABSTRACT);
        collection.setTreatment(TEST_TREATMENT);
        collection.setCollectionPolicy(TEST_POLICY);

        //Build AcceptPackage test list
        List<String> testAcceptPacks = new ArrayList<String>();
        for(int i = 1;i < TEST_ACCEPT_PACKAGES;i++)
        {
            collection.addAcceptPackaging(TEST_ACCEPT_PACKAGING + Integer.toString(i));
            testAcceptPacks.add(TEST_ACCEPT_PACKAGING + Integer.toString(i));
        }


        //EXECUTE THE TESTS
        //------------------------------------------------
        Assert.assertEquals(collection.getLocation(), TEST_LOCATION);
        Assert.assertEquals(collection.getTitle(), TEST_TITLE);

        //Test Accept List
        List<Accept> acceptList = collection.getAcceptList();
        Iterator<Accept> acceptListIterator = acceptList.iterator();
        Iterator<String> testAcceptListIterator = testAccepts.iterator();
        while( acceptListIterator.hasNext() )
        {
            assert(acceptListIterator.next().getContent().equals(testAcceptListIterator.next()));
        }
        Assert.assertEquals(collection.getMediation(), TEST_MEDIATION );
        Assert.assertEquals(collection.getService(), TEST_SERVICE);
        Assert.assertEquals(collection.getAbstract(), TEST_ABSTRACT);
        Assert.assertEquals(collection.getTreatment(), TEST_TREATMENT);
        Assert.assertEquals(collection.getCollectionPolicy(), TEST_POLICY);

        //Test Accept Package List
        List<SwordAcceptPackaging> acceptPacksList = collection.getAcceptPackaging();
        Iterator<SwordAcceptPackaging> acceptPacksListIterator = acceptPacksList.iterator();
        Iterator<String> testAcceptPacksListIterator = testAcceptPacks.iterator();
        while( acceptPacksListIterator.hasNext() )
        {
            Assert.assertEquals(acceptPacksListIterator.next().getContent(), testAcceptPacksListIterator.next());
        }

    }
 
    @Test
    public void testNullLocation()
    {
      Collection collection = createTestCollection(null,TEST_TITLE,TEST_ACCEPTS,"true",
              TEST_SERVICE,TEST_ABSTRACT,TEST_TREATMENT,TEST_POLICY,TEST_ACCEPT_PACKAGES,true);
      Assert.assertEquals(true, testCollection(collection,SwordValidationInfoType.WARNING));
    }

    
    
    @Test
    public void testNullTitle()
    {
      Collection collection = createTestCollection(TEST_LOCATION,null,TEST_ACCEPTS,"true",
              TEST_SERVICE,TEST_ABSTRACT,TEST_TREATMENT,TEST_POLICY,TEST_ACCEPT_PACKAGES,true);
      Assert.assertEquals(true, testCollection(collection,SwordValidationInfoType.VALID));
    }

    @Test
    public void testNoAccepts()
    {
      Collection collection = createTestCollection(TEST_LOCATION,TEST_TITLE,0,"true",
              TEST_SERVICE,TEST_ABSTRACT,TEST_TREATMENT,TEST_POLICY,TEST_ACCEPT_PACKAGES,true);
      Assert.assertEquals(true, testCollection(collection,SwordValidationInfoType.WARNING));
    }

    @Test
    public void testNullMediation()
    {
      Collection collection = createTestCollection(TEST_LOCATION,TEST_TITLE,TEST_ACCEPTS,"null",
              TEST_SERVICE,TEST_ABSTRACT,TEST_TREATMENT,TEST_POLICY,TEST_ACCEPT_PACKAGES,true);
      Assert.assertEquals(true, testCollection(collection,SwordValidationInfoType.WARNING));
    }

    @Test
    public void testNullService()
    {
      Collection collection = createTestCollection(TEST_LOCATION,TEST_TITLE,TEST_ACCEPTS,"true",
              null,TEST_ABSTRACT,TEST_TREATMENT,TEST_POLICY,TEST_ACCEPT_PACKAGES,true);
      Assert.assertEquals(true, testCollection(collection,SwordValidationInfoType.VALID));
    }

    @Test
    public void testNullAbstract()
    {
      Collection collection = createTestCollection(TEST_LOCATION,TEST_TITLE,TEST_ACCEPTS,"true",
              TEST_SERVICE,null,TEST_TREATMENT,TEST_POLICY,TEST_ACCEPT_PACKAGES,true);
      Assert.assertEquals(true, testCollection(collection,SwordValidationInfoType.VALID));
    }

    @Test
    public void testNullTreatment()
    {
      Collection collection = createTestCollection(TEST_LOCATION,TEST_TITLE,TEST_ACCEPTS,"true",
              TEST_SERVICE,TEST_ABSTRACT,null,TEST_POLICY,TEST_ACCEPT_PACKAGES,true);
      Assert.assertEquals(true, testCollection(collection,SwordValidationInfoType.VALID));
    }

    @Test
    public void testNullPolicy()
    {
      Collection collection = createTestCollection(TEST_LOCATION,TEST_TITLE,TEST_ACCEPTS,"true",
              TEST_SERVICE,TEST_ABSTRACT,TEST_TREATMENT,null,TEST_ACCEPT_PACKAGES,true);
      Assert.assertEquals(true, testCollection(collection,SwordValidationInfoType.VALID));
    }


    @Test
    public void testNoAcceptPackages()
    {
      Collection collection = createTestCollection(TEST_LOCATION,TEST_TITLE,TEST_ACCEPTS,"true",
              TEST_SERVICE,TEST_ABSTRACT,TEST_TREATMENT,TEST_POLICY,0,true);
      Assert.assertEquals(true, testCollection(collection,SwordValidationInfoType.VALID));
    }

    @Test
    public void testInvalidAcceptPackages()
    {
      Collection collection = createTestCollection(TEST_LOCATION,TEST_TITLE,TEST_ACCEPTS,"true",
              TEST_SERVICE,TEST_ABSTRACT,TEST_TREATMENT,TEST_POLICY,TEST_ACCEPT_PACKAGES,false);
      Assert.assertEquals(true, testCollection(collection,SwordValidationInfoType.WARNING));
    }

    @Test
    public void testMinimalValidCollection()
    {
      Collection collection = createTestCollection(TEST_LOCATION,null,1,"true",
              null,null,null,null,0,true);
      Assert.assertEquals(true, testCollection(collection,SwordValidationInfoType.VALID));
    }

    public boolean testCollection(Collection collection, SwordValidationInfoType expectedResult)
    {
       SwordValidationInfo info = collection.validate(new Properties());

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

   public Collection createTestCollection(String location,String title,int testAccepts,String mediationString,String service,String Abstract,String treatment,String policy,
           int testAcceptPackages, boolean validAcceptPackages)
   {
       Collection collection = new Collection();

        // SETUP THE TEST DATA
        //-------------------------------------------
        collection.setLocation(location);
        collection.setTitle(title);

        //Build Accepts
        for(int i = 0;i < testAccepts;i++)
        {
            collection.addAccepts(TEST_ACCEPT + Integer.toString(i));
        }

        //Set mediation to true/false or leave as null
        if (mediationString.equalsIgnoreCase("true"))
        {
            collection.setMediation(true);
        }
        else if (mediationString.equalsIgnoreCase("false"))
        {
            collection.setMediation(false);
        }

        collection.setService(service);
        collection.setAbstract(Abstract);
        collection.setTreatment(treatment);
        collection.setCollectionPolicy(policy);

        //Build AcceptPackages
        for(int i = 0;i < testAcceptPackages;i++)
        {
            if(validAcceptPackages)
            {
                collection.addAcceptPackaging(TEST_ACCEPT_PACKAGING);
            }
            else
            {
                collection.addAcceptPackaging("invalid uri");
            }
        }

       return collection;
   }
}
