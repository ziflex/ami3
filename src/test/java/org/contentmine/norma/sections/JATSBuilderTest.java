package org.contentmine.norma.sections;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.contentmine.ami.tools.AbstractAMITest;
import org.contentmine.cproject.metadata.AbstractMetadata.MetadataScheme;
import org.contentmine.eucl.xml.XMLUtil;
import org.contentmine.graphics.html.HtmlElement;
import org.contentmine.graphics.html.HtmlMeta;
import org.junit.Assert;
import org.junit.Test;

/** builds JATSElements from components or legacy
 * 
 * @author pm286
 *
 */
public class JATSBuilderTest extends AbstractAMITest {
	private static final Logger LOG = Logger.getLogger(JATSBuilderTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	private static File TESTSEARCH4 = new File(AbstractAMITest.SRC_TEST_DOWNLOAD, "testsearch4");
	private static File T_903427 = new File(TESTSEARCH4, "10_1101_2020_01_12_903427v1");
	
	@Test
	public void testExtractMetas() {
		File file = new File(T_903427, "landingPage.html");
		HtmlElement htmlElement = HtmlElement.create(file);
		List<HtmlMeta> metaList = HtmlMeta.extractMetas(htmlElement, HtmlMeta.HEAD_META_XPATH);
		Assert.assertEquals("meta", 111, metaList.size());
		long dc =  
				metaList.stream()
					.filter(m -> m.getName() != null && m.getName().startsWith("DC."))
					.peek(m -> System.out.println(m.getName()+" = "+m.getContent()))
					.count();
			Assert.assertEquals("dc", 11, dc);
		long hw =  
				metaList.stream()
					.filter(m -> m.getName() != null && m.getName().startsWith("citation_"))
					.peek(m -> System.out.println(m.getName()+" = "+m.getContent()))
					.count();
			Assert.assertEquals("hw", 78, hw);
		long other =  
				metaList.stream()
					.filter(m -> m.getName() != null &&
							!m.getName().startsWith("citation_") &&
							!m.getName().startsWith("DC"))
					.peek(m -> System.out.println(m.getName()+" = "+m.getContent()))
					.count();
			Assert.assertEquals("other", 21, other);
		long empty =  
				metaList.stream()
					.filter(m -> m.getName() == null)
					.peek(m -> System.out.println(m.getName()+" = "+m.getContent()))
					.count();
			Assert.assertEquals("empty", 1, empty);
	}

	@Test
	public void testExtractMetaLists() {
		List<HtmlMeta> metaList = createMetaList(new File(T_903427, "landingPage.html"));
		HtmlMetaJATSBuilder jatsBuilder = (HtmlMetaJATSBuilder) JATSBuilderFactory.createJATSBuilder(JATSBuilder.BuilderType.HTML);
		Map<MetadataScheme, List<HtmlMeta>> map = jatsBuilder.readMetaListsByMetadataScheme(metaList);
		Assert.assertEquals(80, map.get(MetadataScheme.HW).size());
		Assert.assertEquals(11, map.get(MetadataScheme.DC).size());

	}

	@Test
	public void testProcessMetaLists() {
		List<HtmlMeta> metaList = createMetaList(new File(T_903427, "landingPage.html"));
		HtmlMetaJATSBuilder jatsBuilder = (HtmlMetaJATSBuilder) JATSBuilderFactory.createJATSBuilder(JATSBuilder.BuilderType.HTML);
		Map<MetadataScheme, List<HtmlMeta>> map = jatsBuilder.readMetaListsByMetadataScheme(metaList);
		JATSArticleElement article = jatsBuilder.processHWList();
		Assert.assertEquals("descendants", 99, XMLUtil.getQueryElements(article, "//*"));
//		XMLUtil.debug(article);

	}

	// ========= PRIVATE ==========
	private List<HtmlMeta> createMetaList(File file) {
		HtmlElement htmlElement = HtmlElement.create(file);
		List<HtmlMeta> metaList = HtmlMeta.extractMetas(htmlElement, HtmlMeta.HEAD_META_XPATH);
		return metaList;
	}

}
