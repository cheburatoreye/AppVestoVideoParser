import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.util.List;

public class Parse {
    public static void main(String[] args) {
        final WebClient client=new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);

        try {

            String Url = "https://www.udemy.com/course/learn-flutter-dart-to-build-ios-android-apps/";
            HtmlPage page =client.getPage(Url);

            List<HtmlDivision> items = page.getByXPath("//div[@class='lectures-container collapse in']/div");

            if(items.isEmpty()){
                System.out.println("No items found !");
            } else {
                for (HtmlElement htmlItem : items) {
                    String title="";
                    String duration="";

                    HtmlElement spanDuration = ((HtmlElement) htmlItem.getFirstByXPath(".//div/span[@class='content-summary']"));
                    if ( htmlItem.getFirstByXPath(".//div[2]//a[1]")!=null){
                        HtmlAnchor itemAnchor = ((HtmlAnchor) htmlItem.getFirstByXPath(".//div[2]//a[1]"));
                        duration=itemAnchor.asText();
                    }else {
                        duration= "Useful Resources & Links";//div[@class='left-content']/div[@class='top']/div[@class='title']
                        //duration=itemAnchor.asText();
                    }


                    Item item = new Item();
                    item.setTitle(duration);
                    item.setDurationl( spanDuration.asText());

                    ObjectMapper mapper = new ObjectMapper();
                    String jsonString = mapper.writeValueAsString(item) ;
                    System.out.println(jsonString);
                }

            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
