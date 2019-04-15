package br.com.refbuilder;

import br.com.refbuilder.model.Reference;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * This program helps assembling URL references/citations to be used in
 * papers/articles following the ABNT guidelines
 *
 * @author Gabriel Cavalheiro Ullmann
 * @version 1.0
 * @since 2019-04-14
 */
public class ReferenceBuilder {

    private ArrayList<String> buildOne(String url) {
        ArrayList<String> urls = new ArrayList<>();
        urls.add(url);
        return buildMany(urls);
    }

    private ArrayList<String> buildMany(ArrayList<String> urls) {
        if (urls != null && urls.size() > 0) {
            ArrayList<String> refs = new ArrayList<>();
            try {
                for (String url : urls) {
                    Reference ref = new Reference();
                    
                    if (url.substring(url.length() - 3).equals("pdf")) {
                        ref.setAuthor("PDF, Author");
                        ref.setTitle("PDF " + url.substring(7, 27));
                        ref.setUrl(url);
                        ref.setLastAccess(new Date());
                        refs.add(ref.toString());
                        continue;
                    }
                    Document doc = Jsoup.connect(url).get();

                    //set webpage author
                    Elements eAuthor = doc.select("meta[name=author]");
                    Elements eSiteName = doc.select("meta[property='og:site_name']");
                    Elements eTitle = doc.select("title");
                    if (!eAuthor.text().isEmpty()) {
                        ref.setAuthor(eAuthor.get(0).attr("content"));
                    } else if (!eSiteName.text().isEmpty()) {
                        ref.setAuthor(eSiteName.get(0).attr("content"));
                    } else {
                        ref.setAuthor(eTitle.get(0).text());
                    }

                    //set webpage title
                    Elements eHeader1 = doc.select("h1");
                    if (!eHeader1.text().isEmpty()) {
                        ref.setTitle(eHeader1.get(0).text());
                    } else {
                        ref.setTitle(eTitle.get(0).text());
                    }

                    //set webpage url
                    ref.setUrl(url);

                    //set last access date (current date)
                    ref.setLastAccess(new Date());
                    refs.add(ref.toString());
                }
                return refs;
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                return new ArrayList<>();
            }
        } else {
            System.out.println("Empty list.");
        }
        return new ArrayList<>();
    }

    public void buildFromFile(String path) {
        System.out.println("Searching...\n");
        ArrayList<String> urls = readFile(path);
        ArrayList<String> result;
        for (String url : urls) {
            result = buildOne(url);
            if (result != null && result.size() > 0) {
                System.out.println(buildOne(url).get(0));
            }
        }
    }

    public ArrayList<String> readFile(String path) {
        if (path != null && path.length() > 1) {
            ArrayList<String> sb = new ArrayList<>();
            try (BufferedReader br = Files.newBufferedReader(Paths.get(path))) {
                String line;
                while ((line = br.readLine()) != null) {
                    sb.add(line);
                }
                return sb;
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return new ArrayList<>();
    }

}
