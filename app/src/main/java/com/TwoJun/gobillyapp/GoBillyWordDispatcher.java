package com.TwoJun.gobillyapp;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * A Class that extends WordDispatcher and retrieves KOREAN WORD OF THE DAY from the GoBilly website
 *
 * @author jyp <interruptz@gmail.com>
 * @since 01/16/2014
 */

public class GoBillyWordDispatcher extends WordDispatcher {
    // Create an instance when it is initialized in order for Singleton pattern to work properly.
    protected static GoBillyWordDispatcher _instance = new GoBillyWordDispatcher();

    private GoBillyWordDispatcher() {}
    public static GoBillyWordDispatcher getInstance() {
        return _instance;
    }

    private String word, description, example;
    private final String url = "http://www-blogger-opensocial.googleusercontent.com/gadgets/ifr?url=http://korean.peirazo.net/wod.xml&container=blogger&view=default&lang=en&country=ALL&sanitize=0&v=fe0beba99f20cfbb&libs=core&parent=http://www.gobillykorean.com/&mid=1#st=e%3DAFlCd0VUr3NDycvUB9VURIwiJIUX4azIzek%252BmtihIBjiP0px86LRGHidZ45%252FskHSPm2KGVz7J49J4B3FIZ7EpUlxK0dqazcmkpb%252FaVoViN%252FxDsX1ap0D856Hw4f81IneZyyyXosengyH%26c%3Dblogger&rpctoken=680372992061271336";

    @Override
    public void update() {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
            Elements data = doc.select("div");
            word = data.get(0).text().split(":")[0];
            description = data.get(0).text().split(":")[1];
            example = data.get(1).text() + "\n" + data.get(2).text();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getWord() {
        return this.word;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getExample() {
        return this.example;
    }
}
