package org.contentmine.image.pixel;

import java.io.File;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.contentmine.eucl.euclid.Int2;
import org.contentmine.eucl.euclid.Int2Range;
import org.contentmine.graphics.svg.SVGG;
import org.contentmine.graphics.svg.SVGSVG;
import org.junit.Assert;
import org.junit.Test;

public class PixelListFloodFillTest {

	private static final Logger LOG = LogManager.getLogger(PixelListFloodFillTest.class);
private static String FILL[] ={
			"black", "red", "green", "blue", "yellow", "cyan", "magenta", "brown", "pink"
	};
	@Test
	/** creates PixelList from a string with several separate islands.
	 * Then uses PixelListFloodFill to create the islands
	 * 
	 */
	public void testCreatePixelIslandsFromPixelList() {
		// this is not a good example
		String pixelListString = ""
				+ "(123,11)(123,12)(124,10)(125,10)(123,10)(123,13)(124,13)(126,11)(126,10)(126,13)(126,14)(124,14)(125,14)(127,12)(127,13)(127,11)(123,21)(123,22)(123,20)(125,23)(123,23)(124,23)(124,19)(125,19)(123,19)(126,19)(126,23)(127,22)(127,23)(127,21)(127,20)(127,19)"
				+ "(160,12)(160,13)(161,13)(161,11)(160,11)(163,11)(162,10)(163,10)(161,10)(163,13)(163,14)(161,14)(162,14)(164,12)(164,13)(164,11)(160,25)(160,26)(160,24)(162,27)(160,27)(161,27)(161,23)(162,23)(160,23)(163,23)(163,27)(164,26)(164,27)(164,25)(164,24)(164,23)"
				+ "(91,106)(89,106)(91,107)(89,107)(90,105)(91,105)(89,105)(89,108)(91,109)(89,109)(90,109)(92,108)(92,109)(92,107)(196,16)(198,17)(196,17)(197,17)(197,15)(196,15)(199,17)(198,14)(199,14)(197,14)(200,17)(200,15)(200,14)(201,16)(201,17)(201,15)(197,29)(199,30)(197,30)(198,30)(197,28)(197,27)(198,26)(199,26)(197,26)(200,30)(201,29)(201,30)(201,28)(200,26)(201,27)(201,26)"
				+ "(126,114)(128,115)(126,115)(127,115)(127,113)(128,113)(126,113)(129,114)(129,115)(129,113)(236,23)(234,23)(234,24)(235,22)(236,22)(234,22)(234,25)(236,26)(234,26)(235,26)(237,24)(237,25)(237,23)(237,26)(234,34)(234,35)(235,33)(236,33)(234,33)(234,36)(236,37)(234,37)(235,37)(237,33)(238,34)(238,35)(238,33)(237,37)(238,36)(238,37)"
				+ "(165,119)(163,119)(163,120)(164,118)(165,118)(163,118)(165,121)(163,121)(164,121)(166,120)(166,121)(166,119)(271,32)(273,33)(271,33)(272,33)(271,31)(272,30)(273,30)(271,30)(274,33)(274,31)(274,30)(275,32)(275,33)(275,31)(271,42)(272,41)(273,41)(271,41)(274,42)(274,41)(271,44)(270,43)(270,44)(270,42)(275,43)(275,44)(275,42)(273,45)(271,45)(272,45)(275,45)(274,45)"
				+ "(202,124)(200,124)(202,125)(200,125)(201,125)(201,123)(202,123)(200,123)"
				+ "(308,41)(310,42)(308,42)(309,42)(308,40)(310,39)(308,39)(309,38)(310,38)(308,38)(311,41)(311,42)(311,40)(311,39)(91,237)(89,237)(91,238)(89,238)(90,238)(90,236)(91,236)(89,236)(307,48)(307,49)(308,47)(309,47)(307,47)(307,50)(308,50)(310,47)(311,48)(311,49)(311,47)(310,51)(308,51)(309,51)(311,50)(311,51)"
				+ "(237,135)(239,136)(237,136)(238,136)(238,134)(239,134)(237,134)(240,135)(240,136)(240,134)"
				+ "(344,52)(346,53)(344,53)(345,53)(345,51)(344,51)(347,53)(347,51)(346,50)(347,50)(345,50)(348,52)(348,53)(348,51)(348,58)(348,59)(347,57)(348,57)(346,57)(344,58)(344,59)(345,57)(344,57)(344,60)(346,61)(344,61)(345,61)"
				+ "(126,249)(124,249)(126,250)(124,250)(125,250)(125,248)(126,248)(124,248)"
				+ "(348,60)(348,61)(347,61)"
				+ "(274,139)(276,140)(274,140)(275,140)(275,138)(276,138)(274,138)(277,139)(277,140)(277,138)(381,69)(381,70)(382,70)(382,68)(381,68)(384,68)(383,67)(384,67)(382,67)(384,71)(382,71)(383,71)(381,80)(381,81)(382,79)(383,79)(381,79)(385,70)(385,71)(385,69)(384,79)(381,82)(385,80)(385,81)(385,79)(383,83)(381,83)(382,83)(385,68)"
				+ "(161,259)(161,260)(162,260)(162,258)(163,258)(161,258)(164,259)(164,260)(164,258)(164,261)(162,261)(163,261)"
				+ "(385,82)(385,83)(384,83)(311,149)(313,150)(311,150)(312,150)(312,148)(313,148)(311,148)(314,149)(314,150)(314,148)"
				+ "(420,101)(418,101)(418,102)(419,100)(420,100)(418,100)(418,103)(420,104)(418,104)(419,104)(421,102)(421,103)(421,101)"
				+ "(198,269)(198,270)(199,268)(200,268)(198,268)(200,271)(198,271)(199,271)(421,104)(201,269)(201,270)(201,268)(201,271)"
				+ "(418,119)(418,120)(419,118)(420,118)(418,118)(421,118)(420,121)(418,121)(419,121)(422,119)(422,120)(422,118)(421,121)(422,121)"
				+ "(124,323)(124,324)(125,322)(126,322)(124,322)(124,325)(125,325)(127,323)(127,324)(127,322)(127,325)(127,326)(125,326)(126,326)"
				+ "(347,157)(349,158)(347,158)(348,158)(348,156)(349,156)(347,156)(350,157)(350,158)(350,156)"
				+ "(234,278)(234,279)(235,279)(235,277)(236,277)(234,277)(237,277)(237,280)(235,280)(236,280)(238,278)(238,279)(238,277)(238,280)(160,324)(160,325)(161,325)(161,323)(162,323)(160,323)(163,325)(163,326)(161,326)(162,326)(164,323)(164,324)(163,322)(164,322)(162,322)(164,325)(384,168)(386,169)(384,169)(385,169)(385,167)(386,167)(384,167)(387,168)(387,169)(387,167)(455,174)(455,175)(456,175)(457,173)(455,173)(456,172)(457,172)(455,172)(458,175)(458,176)(456,176)(457,176)(458,174)(458,173)(457,187)(458,187)(456,187)(454,188)(454,189)(455,187)(454,187)(459,188)(459,189)(459,187)(454,190)(456,191)(454,191)(455,191)(271,287)(271,288)(272,288)(272,286)(273,286)(271,286)(274,288)(274,286)(274,289)(272,289)(273,289)(459,190)(458,190)(275,287)(275,288)(275,286)(458,191)(457,191)(197,325)(199,326)(197,326)(198,326)(198,324)(197,324)(200,324)(199,323)(200,323)(198,323)(200,326)(201,325)(201,326)(201,324)(420,184)(420,185)(421,183)(422,183)(420,183)(423,184)(423,185)(423,183)(422,186)(420,186)(421,186)(423,186)(460,210)(458,210)(460,211)(458,211)(459,211)(460,209)(458,209)(459,208)(460,208)(458,208)(308,295)(310,296)(308,296)(309,296)(309,294)(310,294)(308,294)(311,296)(311,294)(312,295)(312,296)(312,294)(237,325)(235,325)(237,326)(235,326)(236,324)(237,324)(235,324)(237,327)(235,327)(236,327)(497,264)(495,264)(497,265)(495,265)(496,265)(496,263)(497,263)(495,263)(347,303)(345,303)(347,304)(345,304)(346,304)(346,302)(347,302)(345,302)(271,326)(271,327)(272,327)(272,325)(271,325)(274,325)(274,326)(273,324)(274,324)(272,324)(274,327)(274,328)(272,328)(273,328)(494,284)(495,284)(493,284)(492,285)(492,286)(492,284)(496,285)(496,286)(496,284)(492,287)(494,288)(492,288)(493,288)(496,287)(496,288)(495,288)(384,310)(382,310)(384,311)(382,311)(383,311)(383,309)(384,309)(382,309)(495,306)(495,307)(494,305)(495,305)(493,305)(492,306)(492,307)(492,305)(494,308)(492,308)(493,308)(495,308)(308,326)(308,327)(309,325)(308,325)(310,328)(308,328)(309,328)(311,325)(311,326)(310,324)(311,324)(309,324)(311,327)(311,328)(419,317)(421,318)(419,318)(420,318)(419,316)(420,315)(421,315)(419,315)(422,316)(422,317)(422,315)(422,318)(344,327)(344,328)(345,328)(345,326)(346,326)(344,326)(347,328)(347,329)(345,329)(347,330)(345,330)(347,331)(345,331)(347,332)(345,332)(346,332)(348,326)(348,327)(347,325)(348,325)(346,325)(348,328)(468,331)(468,332)(466,332)(467,332)(467,330)(468,330)(466,330)(465,331)(465,332)(465,330)(460,331)(460,332)(458,332)(459,332)(459,330)(460,330)(458,330)(457,332)(459,324)(459,325)(458,325)(458,323)(459,323)(457,323)(456,332)(458,329)(458,328)(456,328)(458,327)(456,327)(458,326)(456,326)(456,325)(456,324)(456,323)(455,332)(454,332)(453,332)(452,332)(455,329)(455,328)(451,332)(454,329)(453,329)(452,329)(451,329)(450,332)(450,329)(449,332)(449,329)(448,332)(448,329)(447,331)(447,332)(447,330)(447,329)(381,327)(381,328)(382,326)(383,326)(381,326)(381,329)(381,330)(381,331)(383,332)(381,332)(382,332)(384,327)(384,326)(384,332)(385,331)(385,332)(385,330)(385,328)(385,329)(385,327)(424,331)(424,332)(422,332)(423,332)(424,330)(424,329)(423,328)(424,328)(422,328)(421,332)(421,328)(420,332)(419,332)(420,327)(421,327)(419,327)(418,332)(417,331)(417,332)(417,330)(418,328)(418,327)(417,329)(417,328)"
				+ "";
		PixelIsland island = new PixelIsland();
		PixelList pixelList = PixelList.createPixelList(pixelListString, island);
		Assert.assertEquals(774, pixelList.size());
		PixelListFloodFill pixelListFloodFill = new PixelListFloodFill(pixelList);
		pixelListFloodFill.fillIslands();
		PixelIslandList pixelIslandList = pixelListFloodFill.getIslandList();
		pixelIslandList.sortBySizeDescending();
		Assert.assertEquals(110, pixelIslandList.size());
		// there are duplicates (I think the pixel rings were duplicated)
//		PixelIslandList pixelIslandList2 = new PixelIslandList();
//		for (int i = pixelIslandList.size() - 1; i >= 0; i--) {
//			PixelIsland islandi = pixelIslandList.get(i);
//			Int2Range bboxi = islandi.getIntBoundingBox();
//			for (int j = i - 1; j >= 0; j--) {
//				PixelIsland islandj = pixelIslandList.get(j);
//				Int2Range bboxj = islandj.getIntBoundingBox();
//				if (bboxi.intersectionWith(bboxj) != null) {
//					
//					islandi = null;
//					break;
//				}
//			}
//			if (islandi != null) {
//				pixelIslandList2.add(islandi);
//			}
//		}
//		LOG.debug
		SVGG g = new SVGG();
		int i = 0;
		for (PixelIsland island2 : pixelIslandList) {
			// island plotting needs pixelPlotter
			SVGG gg = new SVGG();
			PixelList pixelList2 = island2.getPixelList();
			Int2Range bbox = pixelList2.getIntBoundingBox();
			Int2 centroid = new Int2(bbox.getXRange().getMidPoint(), bbox.getYRange().getMidPoint());
			pixelList2.plotPixels(gg, FILL[i++ % FILL.length]);
			g.appendChild(gg);
		}
		SVGSVG.wrapAndWriteAsSVG(g, new File("target/islandFlood/fill.svg"));
	}

}
