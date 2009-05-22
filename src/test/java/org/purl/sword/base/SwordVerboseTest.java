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

import java.io.IOException;
import java.util.Properties;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.ParsingException;
import org.junit.*;

/**
 *
 * @author Neil Taylor (nst@aber.ac.uk)
 * @author Peter Todd (ppt@aber.ac.uk)
 */
public class SwordVerboseTest {

    @Test
    public void elementNameTest()
    {
        XmlName name = SwordVerbose.elementName();
        Assert.assertEquals(name.getPrefix(), Namespaces.PREFIX_SWORD);
        Assert.assertEquals(name.getLocalName(), "verbose");
        Assert.assertTrue(name.getNamespace().endsWith(Namespaces.NS_SWORD));
    }

    @Test
    public void createWithNoContentTest()
    {
       SwordVerbose verbose = new SwordVerbose();
        Assert.assertEquals(verbose.getXmlName().getPrefix(), Namespaces.PREFIX_SWORD);
        Assert.assertEquals(verbose.getXmlName().getLocalName(), "verbose");
        Assert.assertTrue(verbose.getXmlName().getNamespace().endsWith(Namespaces.NS_SWORD));
    }

    @Test
    public void createWithContentTest()
    {
       SwordVerbose verbose = new SwordVerbose(true);
       Assert.assertTrue(verbose.getContent());
    }

    private static final String start = "<?xml version=\"1.0\" ?>\n" +
                     "<sword:verbose xmlns=\"http://purl.org/net/sword/\"\n" +
                     "         xmlns:sword=\"http://purl.org/net/sword/\"\n";

    private static final String startBracket = ">";
    
    private static final String end = "</sword:verbose>";

    public Element createElementForTest(String xml)
    throws ParsingException, IOException, UnmarshallException
    {
        Builder builder = new Builder();
		Document doc = builder.build(xml, "http://something.com/here");
        return doc.getRootElement();
    }

    @Test
    public void unmarshallTrueValidTest()
    throws Exception
    {
        SwordVerbose verbose = new SwordVerbose();
        
        Element e = createElementForTest(start + startBracket + "true" + end);
        SwordValidationInfo info = verbose.unmarshall(e, new Properties());

        Assert.assertEquals(info.getType(), SwordValidationInfoType.VALID);
     }

    @Test
    public void unmarshallFalseValidTest()
    throws Exception
    {
        SwordVerbose verbose = new SwordVerbose();

        Element e = createElementForTest(start + startBracket + "false" + end);
        SwordValidationInfo info = verbose.unmarshall(e, new Properties());

        Assert.assertEquals(info.getType(), SwordValidationInfoType.VALID);
   }

    @Test
    public void unmarshallSetTrueValidTest()
    throws Exception
    {
        SwordVerbose verbose = new SwordVerbose();

        Element e = createElementForTest(start + startBracket + end);
        SwordValidationInfo info = verbose.unmarshall(e, new Properties());
        verbose.setContent(true);
        info = verbose.validate(new Properties());
        Assert.assertEquals(info.getType(), SwordValidationInfoType.VALID);
    }

    @Test
    public void unmarshallSetFalseValidTest()
    throws Exception
    {
        SwordVerbose verbose = new SwordVerbose();

        Element e = createElementForTest(start + startBracket + end);
        SwordValidationInfo info = verbose.unmarshall(e, new Properties());
        verbose.setContent(false);
        info = verbose.validate(new Properties());
        Assert.assertEquals(info.getType(), SwordValidationInfoType.VALID);
    }

    @Test
    public void unmarshallErrorTest()
    throws Exception
    {
        SwordVerbose verbose = new SwordVerbose();

        Element e = createElementForTest(start + startBracket + "other" + end);
        SwordValidationInfo info = verbose.unmarshall(e, new Properties());

        Assert.assertEquals(info.getType(), SwordValidationInfoType.ERROR);
    }

    @Test
    public void unmarshallInfoTest()
    throws Exception
    {
        SwordVerbose verbose = new SwordVerbose();

        Element e = createElementForTest(start + " test=\"one\""+ startBracket + "true" + end);
        SwordValidationInfo info = verbose.unmarshall(e, new Properties());

        Assert.assertEquals(info.getType(), SwordValidationInfoType.INFO);
    }

    @Test
    public void unmarshallWarningTest()
    throws Exception
    {
        SwordVerbose verbose = new SwordVerbose();

        Element e = createElementForTest(start + startBracket + end);
        SwordValidationInfo info = verbose.unmarshall(e, new Properties());
        Assert.assertEquals(info.getType(), SwordValidationInfoType.WARNING);
    }


}
