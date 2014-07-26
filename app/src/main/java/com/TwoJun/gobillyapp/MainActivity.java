package com.TwoJun.gobillyapp;


import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity {
    List headlines;
    List links;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        headlines = new ArrayList();
        links = new ArrayList();
        PostTask pt = new PostTask();
        pt.execute("http://feeds.feedburner.com/GoBillyKorean");

        pt.onPostExecute(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onListItemClick(ListView view, View v, int position, long id){
        Uri uri = Uri.parse((String)links.get(position));
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
    private class PostTask extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... strings) {
            String urls = strings[0];

            try{
                URL url = new URL(urls);


                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();

                xpp.setInput(getInputStream(url), "UTF_8");

                boolean insideItem = false;

                int eventType = xpp.getEventType();
                while(eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {

                        if (xpp.getName().equalsIgnoreCase("item")) {
                            insideItem = true;
                        } else if (xpp.getName().equalsIgnoreCase("title")) {
                            if (insideItem)
                                headlines.add(xpp.nextText()); //extract the headline
                        } else if (xpp.getName().equalsIgnoreCase("link")) {
                            if (insideItem)
                                links.add(xpp.nextText()); //extract the link of article
                        }
                    }else if(eventType==XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")){
                        insideItem=false;
                    }
                    eventType = xpp.next();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        public void onPostExecute(String result){
            super.onPostExecute(result);
            adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, headlines);
            setListAdapter(adapter);
        }

        private InputStream getInputStream(URL url){
            try{
                return url.openConnection().getInputStream();
            } catch (IOException e){
                return null;
            }
        }


    }
}
