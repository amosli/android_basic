package com.amos.xml.service;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Xml;
import com.amos.xml.domain.Person;
import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by amosli on 14-6-3.
 */
public class PersonService {
    private Context context;

    public PersonService(Context context) {
        this.context = context;
    }

    /**
     * 把person.xml的输入流解析转化成list集合
     *
     * @param
     * @return
     */
    public List<Person> getPersons(String filename) {
        AssetManager manager = context.getAssets();
        List<Person> persons = new ArrayList<Person>();

        try {
            Person person = null;
            InputStream inputStream = manager.open(filename);
            //在android下使用xmlpullparser进行解析
            XmlPullParser xmlPullParser = Xml.newPullParser();
            //设置xmlpullparser的一些参数
            xmlPullParser.setInput(inputStream, "utf-8");

            //获取pull解析器对应事件类型
            int eventType = xmlPullParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {

                if (eventType == XmlPullParser.START_TAG) {
                    if (xmlPullParser.getName().equals("person")) {
                        person = new Person();
                        //获取id对应的属性的值
                        String id = xmlPullParser.getAttributeValue(0);
                        person.setId(Integer.valueOf(id));
                    }else if(xmlPullParser.getName().equals("name")) {
                        person.setName(xmlPullParser.nextText());
                    }else if(xmlPullParser.getName().equals("age")) {
                        person.setAge(Integer.parseInt(xmlPullParser.nextText()));
                    }

                }

                if (eventType == XmlPullParser.END_TAG) {
                    persons.add(person);
                }

                eventType = xmlPullParser.next();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return persons;
    }

}
