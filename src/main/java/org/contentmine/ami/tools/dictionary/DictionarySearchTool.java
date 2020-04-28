package org.contentmine.ami.tools.dictionary;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.contentmine.ami.dictionary.DefaultAMIDictionary;
import org.contentmine.ami.tools.AMIDictionaryTool;
import org.contentmine.eucl.euclid.Util;

public class DictionarySearchTool extends AMIDictionaryTool {
	private static final Logger LOG = Logger.getLogger(DictionarySearchTool.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	public DictionarySearchTool() {
		super();
	}
	
	@Override
	public void run() {
		DefaultAMIDictionary amiDictionary = AMIDictionaryTool.readDictionary(new File(dictionaryList.get(0)));
		Set<String> rawTermSet = amiDictionary.getRawLowercaseTermSet();
		
		if (searchTerms != null) {
			searchDictionaryForTerms(rawTermSet, searchTerms);
		} else if (searchTermFilenames != null) {
			searchTermsInFiles(rawTermSet, searchTermFilenames);	
		}
	}

	private void searchDictionaryForTerms(Set<String> rawTermSet, List<String> searchTerms) {
		rawTermSet = Util.toLowercase(rawTermSet);
		for (String searchTerm : searchTerms) {
			if (rawTermSet.contains(searchTerm)) {
				System.out.println("found: "+searchTerm);
			} else {
				System.err.println("Cannot find term in dictionary "+searchTerm);
			}
		}
	}

	private void searchTermsInFiles(Set<String> rawTermSet, List<String> searchTermFilenames) {
		List<String> allSearchTerms = new ArrayList<>();
		for (String searchTermFilename : searchTermFilenames) {
			File file = new File(searchTermFilename);
			try {
				List<String> searchTerms0 = FileUtils.readLines(file, Charset.forName(UTF_8));
				allSearchTerms.addAll(searchTerms0);
			} catch (Exception e) {
				throw new RuntimeException("Cannot read "+file);
			}
		}
		searchDictionaryForTerms(rawTermSet, allSearchTerms);
	}

	
}
