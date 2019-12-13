package com.prf.prf

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.widget.ScrollView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_create.*
import kotlinx.android.synthetic.main.activity_create_note.*

class CreateNote : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        NoteButton.setOnClickListener {
            var db = DataBase_Note(this)
            if (titleEdit.text.toString().length > 0
                    && noteEdit.text.toString().length > 0) {
                var title = titleEdit.text.toString()
                var note = noteEdit.text.toString()

                if(db.search(title)==null) {
                  var result=  db.inserNote(title, note)
                    if(result!=(-1).toLong()){
                    Snackbar.make(NoteButton,"Saved",Snackbar.LENGTH_SHORT).show()}
                    else{
                        Snackbar.make(NoteButton,"Error occur!",Snackbar.LENGTH_SHORT).show()
                    }
                }
                else{
                    Snackbar.make(NoteButton,"Title already exists",Snackbar.LENGTH_SHORT).show()
                }

            }
            else{
                Snackbar.make(NoteButton,"Please fill data",Snackbar.LENGTH_SHORT).show()
            }

        }
    }
}
