package com.gok.selin.friendlychatgyk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AnaSayfaActivity extends AppCompatActivity {

    ListView lvKonular;

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference dbRef = db.getReference("Chats");

    ArrayList<String> konularListe = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ana_sayfa);

        lvKonular = (ListView) findViewById(R.id.listViewKonular);
        konularListe.add("Yükleniyor..");
        final ArrayAdapter adaptor = new ArrayAdapter(getApplicationContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,konularListe);
        lvKonular.setAdapter(adaptor);


        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                konularListe.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    konularListe.add(ds.getKey());
                    Log.d("DENEME",ds.getKey());
                }
                adaptor.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        lvKonular.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),
                        ChatActivity.class);
                intent.putExtra("konu",konularListe.get(position));
                startActivity(intent);
            }
        });



    }
}
