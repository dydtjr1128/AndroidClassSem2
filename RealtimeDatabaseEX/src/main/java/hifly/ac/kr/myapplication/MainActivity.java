package hifly.ac.kr.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button addBtn;
    private EditText nameEditText;
    private EditText telEditText;
    private FirebaseDatabase database;
    private ListView listView;
    private DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addBtn = (Button) findViewById(R.id.addBtn);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        telEditText = (EditText) findViewById(R.id.telEditText);
        listView = (ListView) findViewById(R.id.telListView);


        addBtn.setOnClickListener(this);
        database = FirebaseDatabase.getInstance();
        String email = getIntent().getStringExtra("user_id");
        myRef = database.getReference("users");
        Query contacts = myRef.orderByChild("users").limitToFirst(10);
        contacts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    String name = snapshot.child("name").getValue(String.class);
                    String tel = snapshot.child("tel").getValue(String.class);
                    Log.i("CYSN", name + " " + tel);
                    HashMap<String, String> map = new HashMap<>();
                    map.put("item1", name);
                    map.put("item2", tel);
                    arrayList.add(map);
                }
                SimpleAdapter simpleAdapter = new SimpleAdapter(getApplicationContext(), arrayList, android.R.layout.simple_list_item_2, new String[]{"item1", "item2"}, new int[]{android.R.id.text1,android.R.id.text2} );
                listView.setAdapter(simpleAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onClick(View view) {

        String name = nameEditText.getText().toString();
        String tel = telEditText.getText().toString();
        myRef.child(name).child("name").setValue(name);
        myRef.child(name).child("tel").setValue(tel);
    }
}
