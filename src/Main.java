import com.opencsv.CSVWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //Kuna Pallaadiumil al 28. sept 1998, siis võtame kõigilt nii
        String pallaadiumiFutuuridKuudes = "https://finance.yahoo.com/quote/PA%3DF/history/?frequency=1mo&period1=906955200&period2=1733846726";
        loeAndmedSisse(pallaadiumiFutuuridKuudes, "pallaadiumiFutuuridKuudes.csv");

        String plaatinaFutuuridKuudes = "https://finance.yahoo.com/quote/PL%3DF/history/?period1=906940800&period2=1733848476&frequency=1mo";
        loeAndmedSisse(plaatinaFutuuridKuudes, "plaatinaFutuuridKuudes.csv");

        String REIT = "https://finance.yahoo.com/quote/NLY/history/?period1=906940800&period2=1734537455&frequency=1mo";
        loeAndmedSisse(REIT, "REIT.csv");
    }
    private static void loeAndmedSisse(String url, String outputCSVfile) {
        try {
            Document doc = Jsoup.connect(url).get();
            Element table = doc.select("table").first(); // Assuming there's only one table on the page

            if (table != null) {
                // Extract rows from the table
                Elements rows = table.select("tr");

                // Create a CSVWriter
                try (CSVWriter writer = new CSVWriter(new FileWriter(outputCSVfile))) {
                    for (Element row : rows) {
                        // Extract cells (th or td) from the row
                        Elements cells = row.select("th, td");

                        // Convert cells to a string array
                        String[] rowData = cells.stream()
                                .map(Element::text)
                                .toArray(String[]::new);

                        // Write the row to the CSV file
                        writer.writeNext(rowData);
                    }
                }

                System.out.println("Data has been successfully saved to " + outputCSVfile);
            } else {
                System.out.println("No table found on the page.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}