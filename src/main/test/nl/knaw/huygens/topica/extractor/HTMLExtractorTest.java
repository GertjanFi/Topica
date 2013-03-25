package nl.knaw.huygens.topica.extractor;
/*******************************************************************************
 * Copyright (c) 2012 Huygens ING.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Huygens ING - initial API and implementation
 ******************************************************************************/

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.html.HtmlParser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.LinkContentHandler;
import org.apache.tika.sax.TeeContentHandler;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.ContentHandler;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class HTMLExtractorTest {

	@Before
	public void beforeTest() {

	}
	
	@Test
	public void testHTMLExtraction() throws Exception {
		URL url = new URL("http://www.papalencyclicals.net/Councils/ecum16.htm");
		InputStream input = url.openStream();
	    ContentHandler text = new BodyContentHandler(10*1024*1024);//<co id="html.text.co"/>
	    LinkContentHandler links = new LinkContentHandler();//<co id="html.link.co"/>
	    ContentHandler handler = new TeeContentHandler(links, text);//<co id="html.merge"/>
	    Metadata metadata = new Metadata();//<co id="html.store"/>
	    Parser parser = new HtmlParser();//<co id="html.parser"/>
	    ParseContext context = new ParseContext();
	    parser.parse(input, handler, metadata, context);//<co id="html.parse"/>
	    Files.write( text.toString(), new File( "/Users/Gertjan/git/Topica/data/constance.txt"), Charsets.UTF_8 );
	    System.out.println("Title: " + metadata.get(Metadata.TITLE));
	}
}
