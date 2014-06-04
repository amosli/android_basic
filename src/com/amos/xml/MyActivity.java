package com.amos.xml;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.amos.xml.domain.Person;
import com.amos.xml.service.PersonService;

import java.util.List;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        TextView show_users = (TextView) findViewById(R.id.show_users);
        PersonService personService = new PersonService(this);
        StringBuilder stringBuilder = new StringBuilder();
        List<Person> persons = personService.getPersons("person.xml");
        for (Person person : persons) {
            stringBuilder.append("  "+person.getName()).append(":").append(person.getAge());
        }
        System.out.println("stringBuilder:"+stringBuilder);
        show_users.setText(stringBuilder);
        Toast.makeText(this,"数据写入成功!"+stringBuilder,Toast.LENGTH_LONG);

    }
}
