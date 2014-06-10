package com.amos.android_database.test;

import android.test.AndroidTestCase;
import android.util.Log;
import com.amos.android_database.dao.PersonDao;
import com.amos.android_database.domain.Person;

/**
 * Created by amosli on 14-6-11.
 */
public class PersonDaoTest extends AndroidTestCase{
        private String tag = "PersonDaoTest.class";

        public void testAdd() throws Exception{
            PersonDao personDao = new PersonDao(getContext());
            for(int i=0;i<100;i++){
                personDao.addPerson("amsoli"+i,"131888870"+i);
            }
            Log.d(tag,"添加一些新用户");
        }
        public void testFind(){
            PersonDao personDao = new PersonDao(getContext());
            boolean result = personDao.findPerson("13188887776");
            assertEquals(true,result);
        }
        public void testDelete(){
            PersonDao personDao = new PersonDao(getContext());
            personDao.deletePerson("13188887778");
        }

        public void testUpdate(){
            PersonDao personDao = new PersonDao(getContext());
            personDao.updatePerson("13188887776","hi_amos","13188887775");
        }
        public void testFindAllPerson(){
            PersonDao personDao = new PersonDao(getContext());
            for (Person person : personDao.findAllPerson()) {
                System.out.println(person.toString());
            }
        }


}
