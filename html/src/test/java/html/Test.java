package html;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Test {
	public static void main(String[] args) throws IOException {
		Document doc = Jsoup.connect("https://www.cz89.com/zst/qlc").data("year", "2002").get();
		System.out.println(doc.getElementById("chartsTable").getElementsByTag("tr").get(2));
	}
}
