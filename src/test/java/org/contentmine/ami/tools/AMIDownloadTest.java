package org.contentmine.ami.tools;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.contentmine.ami.tools.download.AbstractDownloader;
import org.contentmine.ami.tools.download.AbstractMetadataEntry;
import org.contentmine.ami.tools.download.BiorxivDownloader;
import org.contentmine.ami.tools.download.CurlDownloader;
import org.contentmine.ami.tools.download.CurlPair;
import org.contentmine.ami.tools.download.ResultSet;
import org.contentmine.cproject.files.CProject;
import org.contentmine.cproject.files.CTree;
import org.contentmine.cproject.util.CMineTestFixtures;
import org.contentmine.cproject.util.CMineUtil;
import org.junit.Assert;
import org.junit.Test;

/** test OCR.
 * 
 * @author pm286
 *
 */
public class AMIDownloadTest extends AbstractAMITest {
	private static final Logger LOG = Logger.getLogger(AMIDownloadTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	private static File DOWNLOAD_DIR = new File(SRC_TEST_TOOLS, "download");
	private static File BIORXIV_DIR = new File(DOWNLOAD_DIR, "biorxiv");
	private static File CLIMATE_DIR = new File(BIORXIV_DIR, "climate");
	
	@Test
	/** 
	 * run query
	 */
	public void testBiorxiv() throws Exception {
		String args = 
				"-p target/biorxiv"
				+ " --site biorxiv"
				+ " --query coronavirus"
				+ " --pagesize 40"
				+ " --pages 1 10"
				+ " --limit 500"
			;
		new AMIDownloadTool().runCommands(args);
	}

	@Test
	/** 
	 * run query
	 */
	public void testBiorxivClimate() throws Exception {
		String args = 
				"-p target/biorxiv/climate"
				+ " --site biorxiv"
				+ " --query climate change"
				+ " --metadata metadata"
				+ " --rawfiletypes html"
				+ " --pagesize 10"
				+ " --pages 1 3"
				+ " --limit 100"
			;
		new AMIDownloadTool().runCommands(args);
		Assert.assertTrue(new File("target/biorxiv/climate/metadata/page1.html").exists());
	}

	// extract fulltext with div[class~="fulltext-view"]
	
	
	/** to test that we can run curl from java
	 * 
	 * @throws Exception
	 */
	@Test 
	public void testCurlREST() throws Exception {
			
		String result = runCurlProcess("https://www.ebi.ac.uk/europepmc/webservices/rest/search?query=coronavirus");
		System.out.println("EBI "+result);

		result = runCurlProcess("https://www.biorxiv.org/search/coronavirus");
		System.out.println("BIOX "+result);
		
//		https://www.biorxiv.org/search/coronavirus%20numresults%3A75%20sort%3Arelevance-rank

	}

	@Test 
	/** downloads a single curlPair
	 * 
	 * @throws Exception
	 */
	public void testCurlDownloader() throws Exception {
			
		File downloadDir = new File("target/biorxiv/");
		CurlDownloader curlDownloader = new CurlDownloader();
		String fileroot = "10.1101/850289v1";
		
		CurlPair curlPair = BiorxivDownloader.createCurlPair(downloadDir, fileroot);
		curlDownloader.addCurlPair(curlPair);

		String result = curlDownloader.run();
		System.out.println("BIOX ["+result+"]");
		Assert.assertTrue(curlPair.getFile().getAbsoluteFile()+" exists", curlPair.getFile().exists());

	}
	

	@Test 
	/** download multiple URLs in a single run.
	 * Still appears to run each sequentially so relatively little performanace gain,
	 * but maybe worthwhile.
	 * 
	 * @throws Exception
	 */
	public void testCurlDownloaderMultiple() throws Exception {
		File downloadDir = new File("target/biorxiv/");
		CurlDownloader curlDownloader = new CurlDownloader();
		// these are verbatim from the resultSet file
		String[] fileroots = {
			       "/content/10.1101/2020.01.24.917864v1",
			       "/content/10.1101/850289v1",
			       "/content/10.1101/641399v2",
			       "/content/10.1101/844886v1",
			       "/content/10.1101/709089v1",
			       "/content/10.1101/823724v1",
			       "/content/10.1101/827196v1",
			       "/content/10.1101/823930v1",
			       "/content/10.1101/821561v1",
			       "/content/10.1101/819326v1",
			      };
		for (String fileroot : fileroots) {
			curlDownloader.addCurlPair(BiorxivDownloader.createCurlPair(downloadDir, fileroot));
		}
		
		curlDownloader.setTraceFile("target/trace.txt");
		curlDownloader.setTraceTime(true);
		String result = curlDownloader.run();
		LOG.debug("result ["+result+"]");

	}



	@Test
	/**
	 * 
	 */
	public void testCreateUnpopulatedCTreesFromResultSet() throws IOException {
		File targetDir = new File("target/biorxiv/climate");
		CMineTestFixtures.cleanAndCopyDir(CLIMATE_DIR, targetDir);
		
		CProject cProject = new CProject(targetDir);
		File metadataDir = cProject.getOrCreateExistingMetadataDir();
		/** reads existing resultSet file to create object */
		ResultSet resultSet = new BiorxivDownloader().setCProject(cProject).createResultSet(new File(metadataDir, "resultSet1.clean.html"));
		// result set had default 10 entries
		List<AbstractMetadataEntry> metadataEntryList = resultSet.getMetadataEntryList();
		Assert.assertEquals("metadata", 10, +metadataEntryList.size());
		// metadata directory had 3 results sets, each raw and clean
		Assert.assertEquals(6, metadataDir.listFiles().length);
		// remove all existing CTrees for the test
		cProject.cleanAllTrees();
		Assert.assertEquals(0,  cProject.getOrCreateCTreeList().size());
		// this is the __metadata directory
		Assert.assertEquals(1,  cProject.getDirectory().listFiles().length);
		// create trees from result set
		resultSet.createCTrees(cProject);
		Assert.assertEquals("Ctree count", 10, cProject.getOrCreateCTreeList().size());
		
		
	}
	
	@Test
	/**
	 * as above, but download landing pages
	 */
	public void testCreateCTreeLandingPagesFromResultSetIT() throws IOException {
		File targetDir = new File("target/biorxiv/climate");
		CMineTestFixtures.cleanAndCopyDir(CLIMATE_DIR, targetDir);
		
		CProject cProject = new CProject(targetDir).cleanAllTrees();
		File metadataDir = cProject.getOrCreateExistingMetadataDir();
		AbstractDownloader biorxivDownloader = new BiorxivDownloader().setCProject(cProject);
		ResultSet resultSet = biorxivDownloader.createResultSet(new File(metadataDir, "resultSet1.clean.html"));
		List<String> fileroots = resultSet.getCitationLinks();
//		resultSet.createLandingPages(cProject);
		
		CurlDownloader curlDownloader = new CurlDownloader();
//		// these are verbatim from the resultSet file
//		String[] fileroots = {
//			       "/content/10.1101/2020.01.24.917864v1",
//			       "/content/10.1101/850289v1",
//			       "/content/10.1101/641399v2",
//			       "/content/10.1101/844886v1",
//			       "/content/10.1101/709089v1",
//			       "/content/10.1101/823724v1",
//			       "/content/10.1101/827196v1",
//			       "/content/10.1101/823930v1",
//			       "/content/10.1101/821561v1",
//			       "/content/10.1101/819326v1",
//			      };
		for (String fileroot : fileroots) {
			curlDownloader.addCurlPair(BiorxivDownloader.createCurlPair(cProject.getDirectory(), fileroot));
		}
		
		curlDownloader.setTraceFile("target/trace.txt");
		curlDownloader.setTraceTime(true);
		String result = curlDownloader.run();
		LOG.debug("result ["+result+"]");

//		Assert.assertEquals("Ctree count", 10, cProject.getOrCreateCTreeList().size());
		
	}


	// ====private====
	
	private String runCurlProcess(String url) throws IOException {
		String[] command = new String[] {"curl", "-X", "GET", url};
		ProcessBuilder processBuilder = new ProcessBuilder(command);
		Process process = processBuilder.start();
		String result = String.join("\n", IOUtils.readLines(process.getInputStream(), CMineUtil.UTF8_CHARSET));
		int exitCode = process.exitValue();
		return result;
	}
	

}
