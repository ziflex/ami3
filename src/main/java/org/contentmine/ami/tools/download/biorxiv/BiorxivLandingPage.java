package org.contentmine.ami.tools.download.biorxiv;

import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.contentmine.ami.tools.download.AbstractLandingPage;
import org.contentmine.eucl.xml.XMLUtil;

/** the first page a user/agent sees gievn a citation link
 * it normally has title, authors
 * often has an abstract
 * should have links to fulltext HTML and/or PDF
 * normally Biorxiv does not have fulltext landing pages
 * 
 * 

 * @author pm286
 *
 */
public class BiorxivLandingPage extends AbstractLandingPage {
	private static final Logger LOG = LogManager.getLogger(BiorxivLandingPage.class);
public BiorxivLandingPage() {
		super();
	}

	@Override
	public String getHtmlLink() {
		return landingPageHtml == null ? null :
			landingPageHtml.getHead().getMetaElementValue(CITATION_FULL_HTML_URL);
	}

	@Override
	public String getLinkToAbstract() {
		return landingPageHtml.getHead().getMetaElementValue(CITATION_ABSTRACT_HTML_URL);
	}

	@Override
	public String getPDFLink() {
		return landingPageHtml.getHead().getMetaElementValue(CITATION_PDF_URL);
	}

	@Override
	public List<String> getSupplemntaryLinks() {
		// TODO Auto-generated method stub
		return null;
	}

}
