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
package org.purl.sword.atom;

import org.junit.*;

import java.util.Properties;
import org.purl.sword.base.SwordValidationInfo;
import org.purl.sword.base.SwordValidationInfoType;

/**
 *
 * @author Peter Todd (ppt@aber.ac.uk)
 */
public class EntryTest
{
    private static String TEST_ID = "test id";
    private static String TEST_TITLE = "title example";
    private static String TEST_CATEGORY = "category example";
    private static String TEST_PUBLISHED = "test published";
    private static String TEST_UPDATED = "test updated";
    private static String TEST_RIGHTS = "test rights";
    private static String TEST_LOCATION = "test location";
    private static String TEST_AUTHOR_NAME = "test author name";
    private static String TEST_CONTRIBUTOR_NAME = "test contributor name";

    
    private static String TEST_GENERATOR_URI = "http://test";
    private static String TEST_GENERATOR_VERSION = "test version";
    private static String TEST_GENERATOR_CONTENT = "test content";

    @Test
    public void testCreate()
    {
        Entry entry = new Entry();
        Title title = new Title();
        entry.setTitle(title);
        entry.setId(TEST_ID);
        entry.setPublished(TEST_PUBLISHED);
        Rights rights = new Rights();
        entry.setRights(rights);
        Summary summary = new Summary();

        entry.setSummary(summary);
        entry.setUpdated(TEST_UPDATED);

        Generator generator = new Generator();
        entry.setGenerator(generator);
        Author author = new Author();
        entry.addAuthors(author);
        entry.addCategory(TEST_CATEGORY);

        Contributor contributor = new Contributor();
        entry.addContributor(contributor);
        Link link = new Link();
        entry.addLink(link);

        Assert.assertSame(entry.getTitle(), title);
        Assert.assertEquals(entry.getId(), TEST_ID);

        Assert.assertEquals(entry.getPublished(), TEST_PUBLISHED);
        Assert.assertSame(entry.getRights(), rights);
        Assert.assertSame(entry.getSummary(), summary);
        Assert.assertEquals(entry.getUpdated(), TEST_UPDATED);
        Assert.assertSame(entry.getGenerator(), generator);
        Assert.assertSame(entry.getAuthors().next(), author);
        Assert.assertEquals(entry.getCategories().next().toString(), TEST_CATEGORY );
        Assert.assertSame(entry.getContributors().next(), contributor);
        Assert.assertSame(entry.getLinks().next(), link);
    }


    @Test
    public void testValidEntry()
    {
        Entry entry = createTestEntry(TEST_TITLE, TEST_ID, TEST_PUBLISHED,
            true,
            true, true, true,
            true, true, true,
            TEST_UPDATED, TEST_CATEGORY,
            true, true, true,
            true, true, true);
        Assert.assertEquals(true, testEntry(entry, SwordValidationInfoType.VALID));
    }

    @Test
    public void testNullTitle()
    {
        Entry entry = createTestEntry(null, TEST_ID, TEST_PUBLISHED,
            true,
            true, true, true,
            true, true, true,
            TEST_UPDATED, TEST_CATEGORY,
            true, true, true,
            true, true, true);
        Assert.assertEquals(true, testEntry(entry, SwordValidationInfoType.ERROR));
    }

    @Test
    public void testNullId()
    {
        Entry entry = createTestEntry(TEST_TITLE, null, TEST_PUBLISHED,
            true,
            true, true, true,
            true, true, true,
            TEST_UPDATED, TEST_CATEGORY,
            true, true, true,
            true, true, true);
       Assert.assertEquals(true, testEntry(entry, SwordValidationInfoType.WARNING));
    }

    @Test
    public void testNullPublished()
    {
        Entry entry = createTestEntry(TEST_TITLE, TEST_ID, null,
            true,
            true, true, true,
            true, true, true,
            TEST_UPDATED, TEST_CATEGORY,
            true, true, true,
            true, true, true);
        Assert.assertEquals(true, testEntry(entry, SwordValidationInfoType.WARNING));
    }

    @Test
    public void testNoGenerator()
    {
        Entry entry = createTestEntry(TEST_TITLE, TEST_ID, TEST_PUBLISHED,
            true,
            false, true, true,
            true, true, true,
            TEST_UPDATED, TEST_CATEGORY,
            true, true, true,
            true, true, true);
        Assert.assertEquals(true, testEntry(entry, SwordValidationInfoType.ERROR));
    }

    @Test
    public void testInvalidGenerator()
    {
        Entry entry = createTestEntry(TEST_TITLE, TEST_ID, TEST_PUBLISHED,
            true,
            true, true, true,
            false, true, true,
            TEST_UPDATED, TEST_CATEGORY,
            true, true, true,
            true, true, true);
        Assert.assertEquals(true, testEntry(entry, SwordValidationInfoType.WARNING));
    }

    @Test
    public void testNoRights()
    {
        Entry entry = createTestEntry(TEST_TITLE, TEST_ID, TEST_PUBLISHED,
            true,
            true, false, true,
            true, true, true,
            TEST_UPDATED, TEST_CATEGORY,
            true, true, true,
            true, true, true);
        Assert.assertEquals(true, testEntry(entry, SwordValidationInfoType.VALID));
    }


    @Test
    public void testInvalidRights()
    {
        Entry entry = createTestEntry(TEST_TITLE, TEST_ID, TEST_PUBLISHED,
            true,
            true, true, true,
            true, false, true,
            TEST_UPDATED, TEST_CATEGORY,
            true, true, true,
            true, true, true);
        Assert.assertEquals(true, testEntry(entry, SwordValidationInfoType.WARNING));
    }

    @Test
    public void testNoSummary()
    {
        Entry entry = createTestEntry(TEST_TITLE, TEST_ID, TEST_PUBLISHED,
            true,
            true, true, false,
            true, true, true,
            TEST_UPDATED, TEST_CATEGORY,
            true, true, true,
            true, true, true);
        Assert.assertEquals(true, testEntry(entry, SwordValidationInfoType.VALID));
    }

    @Test
    public void testInvalidSummary()
    {
        Entry entry = createTestEntry(TEST_TITLE, TEST_ID, TEST_PUBLISHED,
            true,
            true, true, true,
            true, true, false,
            TEST_UPDATED, TEST_CATEGORY,
            true, true, true,
            true, true, true);
        Assert.assertEquals(true, testEntry(entry, SwordValidationInfoType.WARNING));
    }


    @Test
    public void testNullUpdated()
    {
        Entry entry = createTestEntry(TEST_TITLE, TEST_ID, TEST_PUBLISHED,
            true,
            true, true, true,
            true, true, true,
            null, TEST_CATEGORY,
            true, true, true,
            true, true, true);
        Assert.assertEquals(true, testEntry(entry, SwordValidationInfoType.WARNING));
    }

    @Test
    public void testNullCategory()
    {
        Entry entry = createTestEntry(TEST_TITLE, TEST_ID, TEST_PUBLISHED,
            true,
            true, true, true,
            true, true, true,
            TEST_UPDATED, null,
            true, true, true,
            true, true, true);
        Assert.assertEquals(true, testEntry(entry, SwordValidationInfoType.WARNING));
    }

    @Test
    public void testNoAuthor()
    {
        Entry entry = createTestEntry(TEST_TITLE, TEST_ID, TEST_PUBLISHED,
            true,
            true, true, true,
            true, true, true,
            TEST_UPDATED, TEST_CATEGORY,
            false, true, true,
            true, true, true);
        Assert.assertEquals(true, testEntry(entry, SwordValidationInfoType.VALID));
    }

    @Test
    public void testInvalidAuthor()
    {
        Entry entry = createTestEntry(TEST_TITLE, TEST_ID, TEST_PUBLISHED,
            true,
            true, true, true,
            true, true, true,
            TEST_UPDATED, TEST_CATEGORY,
            true, true, true,
            false, true, true);
        Assert.assertEquals(true, testEntry(entry, SwordValidationInfoType.ERROR));
    }

    @Test
    public void testNoContributor()
    {
        Entry entry = createTestEntry(TEST_TITLE, TEST_ID, TEST_PUBLISHED,
            true,
            true, true, true,
            true, true, true,
            TEST_UPDATED, TEST_CATEGORY,
            true, false, true,
            true, true, true);
        Assert.assertEquals(true, testEntry(entry, SwordValidationInfoType.VALID));
    }

    @Test
    public void testInvalidContributor()
    {
        Entry entry = createTestEntry(TEST_TITLE, TEST_ID, TEST_PUBLISHED,
            true,
            true, true, true,
            true, true, true,
            TEST_UPDATED, TEST_CATEGORY,
            true, true, true,
            true, false, true);
        Assert.assertEquals(true, testEntry(entry, SwordValidationInfoType.ERROR));
    }

    @Test
    public void testNoLink()
    {
        Entry entry = createTestEntry(TEST_TITLE, TEST_ID, TEST_PUBLISHED,
            true,
            true, true, true,
            true, true, true,
            TEST_UPDATED, TEST_CATEGORY,
            true, true, false,
            true, true, true);
        Assert.assertEquals(true, testEntry(entry, SwordValidationInfoType.VALID));
    }

    @Test
    public void testInvalidLink()
    {
        Entry entry = createTestEntry(TEST_TITLE, TEST_ID, TEST_PUBLISHED,
            true,
            true, true, true,
            true, true, true,
            TEST_UPDATED, TEST_CATEGORY,
            true, true, true,
            true, true, false);
        Assert.assertEquals(true, testEntry(entry, SwordValidationInfoType.ERROR));
    }

    public Entry createTestEntry(String titleString, String id, String published,
            boolean validTitle,
            boolean createGenerator, boolean createRights, boolean createSummary,
            boolean validGenerator, boolean validRights, boolean validSummary,
            String updated, String category,
            boolean createAuthor,boolean createContributor, boolean createLink,
            boolean validAuthor,boolean validContributor, boolean validLink)
    {
        Entry entry = new Entry();
        
        if(titleString != null)
        {
            Title title = new Title();
            if(validTitle)
            {
                title.setContent(TEST_TITLE);
                title.setType(ContentType.TEXT);
            }
            entry.setTitle(title);
        }

        entry.setId(id);
        entry.setPublished(published);
        if(createRights)
        {
            Rights rights = new Rights();
            if(validRights)
            {
                rights.setContent(TEST_RIGHTS);
                rights.setType(ContentType.TEXT);
            }
            entry.setRights(rights);
        }

        if(createSummary)
        {
            Summary summary = new Summary();
            if(validSummary)
            {
                summary.setContent("TEST_SUMMARY");
                summary.setType(ContentType.TEXT);
            }
            entry.setSummary(summary);
        }

        entry.setUpdated(updated);

        if(createGenerator)
        {
            Generator generator = new Generator();
            if(validGenerator)
            {
                generator.setUri(TEST_GENERATOR_URI);
                generator.setVersion(TEST_GENERATOR_VERSION);
                generator.setContent(TEST_GENERATOR_CONTENT);
            }
            entry.setGenerator(generator);
        }

        if(createAuthor)
        {
            Author author = new Author();
            if(validAuthor)
            {
                author.setName(TEST_AUTHOR_NAME);
            }
            entry.addAuthors(author);
        }
        entry.addCategory(category);

        if(createContributor == true)
        {
            Contributor contributor = new Contributor();
            if(validContributor)
            {
                contributor.setName(TEST_CONTRIBUTOR_NAME);
            }
            entry.addContributor(contributor);
        }

        if(createLink == true)
        {
            Link link = new Link();
            if(validLink)
            {
                link.setHref(TEST_LOCATION);
            }
            entry.addLink(link);
        }

        return entry;
    }

    public boolean testEntry(Entry entry, SwordValidationInfoType expectedResult)
    {
       SwordValidationInfo info = entry.validate(new Properties());

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
