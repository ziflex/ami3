package org.contentmine.norma.sections;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.contentmine.graphics.html.HtmlCol;
import org.contentmine.graphics.html.HtmlDiv;
import org.contentmine.graphics.html.HtmlElement;

import nu.xom.Element;

public class JATSMathElement extends AbstractJATSMathmlElement {
    private static final Logger LOG = LogManager.getLogger(JATSMathElement.class);
public static String TAG = "math";

    public JATSMathElement(Element element) {
        super(element);
    }
	/** HTMLEquivalent
	 */
	@Override
	public HtmlElement createHTML() {
		HtmlDiv div = new HtmlDiv();
		div.setClassAttribute(TAG);
		return deepCopyAndTransform(div);
	}


}
