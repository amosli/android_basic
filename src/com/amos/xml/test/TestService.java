package com.amos.xml.test;

import android.test.AndroidTestCase;
import com.amos.xml.domain.Person;
import com.amos.xml.service.PersonService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amosli on 14-6-3.
 */
public class TestService extends AndroidTestCase {

//    public void testGetPersons() throws Exception {
//        PersonService personService = new PersonService(getContext());
//        List<Person> persons = personService.getPersons("person.xml");
//        for (Person person : persons) {
//            System.out.println(person.getName());
//        }
//    }


//    public void testBook() throws Exception {
//        InputStream is = getContext().getAssets().open("books.xml");
////                  parser = new SaxBookParser();
////                  parser = new DomBookParser();
//        PullBookParser parser = new PullBookParser();
//        List<Book> books = parser.parse(is);
//        for (Book book : books) {
//            Log.i("test", book.toString());
//        }
//    }



    //测试生成xml文件
    public void Test_create_xml() {
        List<Person> persons = new ArrayList<Person>();
        for(int i=0;i<10;i++){
            Person person = new Person(i,"amosli"+i,i+20);
            persons.add(person);

            System.out.println(person.toString());
        }

        PersonService personService = new PersonService(getContext());
        boolean result = personService.saveToXML(persons);
        System.out.println("result:"+result);
        assertEquals(true,result);


    }


}
