package com.amos.xml.test;

import android.test.AndroidTestCase;
import com.amos.xml.domain.Person;
import com.amos.xml.service.PersonService;

import java.util.List;

/**
 * Created by amosli on 14-6-3.
 */
public class TestPersonService extends AndroidTestCase {

    public void testGetPersons() throws Exception {
        PersonService personService = new PersonService(getContext());
        List<Person> persons = personService.getPersons("person.xml");
        for (Person person : persons) {
            System.out.println(person.getName());
        }
    }
}
