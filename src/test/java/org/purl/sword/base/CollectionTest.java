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
 * @author neiltaylor
 */
public class CollectionTest {

    private static String TEST_LOCATION = "location example";

    private static String TEST_TITLE = "title example";

    private static String TEST_ACCEPT = "test accept ";

    private static int TEST_ACCEPTS = 5;

    private static boolean TEST_MEDIATION = true;

    private static String TEST_SERVICE = "service example";

    private static String TEST_ABSTRACT = "abstract example";

    private static String TEST_TREATMENT = "treatment example";

    private static String TEST_POLICY = "policy example";

    private static String TEST_ACCEPT_PACKAGING = "test accept packaging ";

    private static int TEST_ACCEPT_PACKS = 5;


    public Collection createInstance()
    {
       Collection collection = new Collection();

       collection.setAbstract(TEST_ABSTRACT);
       collection.setTitle(TEST_TITLE);

       return collection; 
    }

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
        for(int i = 1;i < TEST_ACCEPT_PACKS;i++)
        {
            collection.addAcceptPackaging(TEST_ACCEPT_PACKAGING + Integer.toString(i));
            testAcceptPacks.add(TEST_ACCEPT_PACKAGING + Integer.toString(i));
        }


        //EXECUTE THE TESTS
        //------------------------------------------------
        assert(collection.getLocation().equals(TEST_LOCATION));
        assert(collection.getTitle().equals(TEST_TITLE));

        //Test Accept List
        List<Accept> acceptList = collection.getAcceptList();
        Iterator<Accept> acceptListIterator = acceptList.iterator();
        Iterator<String> testAcceptListIterator = testAccepts.iterator();
        while( acceptListIterator.hasNext() )
        {
            assert(acceptListIterator.next().getContent().equals(testAcceptListIterator.next()));
        }
        assert(collection.getMediation() == TEST_MEDIATION );
        assert(collection.getService().equals(TEST_SERVICE));
        assert(collection.getAbstract().equals(TEST_ABSTRACT));
        assert(collection.getTreatment().equals(TEST_TREATMENT));
        assert(collection.getCollectionPolicy().equals(TEST_POLICY));

        //Test Accept Package List
        List<SwordAcceptPackaging> acceptPacksList = collection.getAcceptPackaging();
        Iterator<SwordAcceptPackaging> acceptPacksListIterator = acceptPacksList.iterator();
        Iterator<String> testAcceptPacksListIterator = testAcceptPacks.iterator();
        while( acceptPacksListIterator.hasNext() )
        {
            assert(acceptPacksListIterator.next().getContent().equals(testAcceptPacksListIterator.next()));
        }

    }

    @Test
    public void validateIsValidTest()
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
        for(int i = 1;i < TEST_ACCEPT_PACKS;i++)
        {
            //collection.addAcceptPackaging(TEST_ACCEPT_PACKAGING + Integer.toString(i));
            collection.addAcceptPackaging("http://www.imsglobal.org/xsd/imscp_v1p1");
            testAcceptPacks.add(TEST_ACCEPT_PACKAGING + Integer.toString(i));
        }


       SwordValidationInfo info = collection.validate(new Properties());
       StringBuffer buffer = new StringBuffer();
       info.createString(info, buffer, " ");
       System.out.println(buffer.toString());
       System.out.println("****************************************************************");
       assert(info.getType() == SwordValidationInfoType.VALID);
    }

}
