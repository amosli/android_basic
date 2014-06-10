package com.amos.xml.service;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.util.Xml;
import com.amos.xml.domain.Person;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
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
        //初始化项目.
        List<Person> persons = null;
        Person person = null;

        try {
            InputStream inputStream = manager.open(filename);
            //在android下使用xmlpullparser进行解析
            XmlPullParser xmlPullParser = Xml.newPullParser();
            //设置xmlpullparser的一些参数
            xmlPullParser.setInput(inputStream, "utf-8");

            //获取pull解析器对应事件类型
            int eventType = xmlPullParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        persons = new ArrayList<Person>();
                        break;

                    case XmlPullParser.START_TAG:
                        if (xmlPullParser.getName().equals("person")) {
                            person = new Person();
                            String id = xmlPullParser.getAttributeValue(0);
                            Log.d("person.id", id);
                            person.setId(Integer.parseInt(id));
                            eventType = xmlPullParser.next();
                        } else if (xmlPullParser.getName().equals("name")) {
                            String name = xmlPullParser.nextText();
                            Log.d("person.name", name);
                            person.setName(name);
                            eventType = xmlPullParser.next();
                        } else if (xmlPullParser.getName().equals("age")) {
                            String age = xmlPullParser.nextText();
                            Log.d("person.age", age);
                            person.setAge(Integer.valueOf(age));
                            eventType = xmlPullParser.next();
                        }
                        ;
                        break;
                    case XmlPullParser.END_TAG:
                        if (xmlPullParser.getName().equals("person")) {
                            persons.add(person);
                            person = null;
                        }

                        break;
                }
                eventType = xmlPullParser.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return persons;

    }


    /**
     * 生成xml文件
     *
     * @param personList
     * @return
     */
    public boolean saveToXML(List<Person> personList) {
        XmlSerializer xmlSerializer = Xml.newSerializer();
//        File file = new File(Environment.getDownloadCacheDirectory(), "created_person.xml");

        File file = new File("/mnt/person.xml");

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            xmlSerializer.setOutput(fileOutputStream, "utf-8");
            //文档的开头
            xmlSerializer.startDocument("utf-8", true);
            Log.d("准备开始数据了!", "111");
            xmlSerializer.startTag(null, "persons");
            for (Person person : personList) {
                //person

                //startTag第一参数是命名空间
                xmlSerializer.startTag(null, "person");
                xmlSerializer.attribute(null, "id", person.getId() + "");

                //name
                xmlSerializer.startTag(null, "name");
                xmlSerializer.text(person.getName());
                xmlSerializer.endTag(null, "name");

                //age
                xmlSerializer.startTag(null, "age");
                xmlSerializer.text(person.getAge() + "");
                xmlSerializer.endTag(null, "age");

                xmlSerializer.endTag(null, "person");
            }
            Log.d("结束写数据了!", "111");
            //文档的末尾
            xmlSerializer.endDocument();
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            Log.d("出错了!", "111");
            e.printStackTrace();
            return false;
        }
        Log.d("成功!", "111");
        return true;

    }


}
