package seneca.ict.provider;


import android.app.Activity;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CoursesProvidersActivity extends Activity  {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void onClickAddTitle(View view) {
		/*
		//---add a book---
		ContentValues values = new ContentValues();
		values.put(BooksProvider.TITLE, ((EditText)
				findViewById(R.id.txtTitle)).getText().toString());
		values.put(BooksProvider.ISBN, ((EditText)
				findViewById(R.id.txtISBN)).getText().toString());
		Uri uri = getContentResolver().insert(
				BooksProvider.CONTENT_URI, values);
		 */

        ContentValues values = new ContentValues();
        values.put("code", ((EditText)
                findViewById(R.id.txtCODE)).getText().toString());
        values.put("title", ((EditText)
                findViewById(R.id.txtTitle)).getText().toString());
        values.put("room", ((EditText)
                findViewById(R.id.txtRoom)).getText().toString());
        Uri uri = getContentResolver().insert(
                Uri.parse(
                        "content://seneca.ict.provider.CoursesProvider/courses"),
                values);

        Toast.makeText(getBaseContext(),uri.toString(),
                Toast.LENGTH_LONG).show();
    }

    public void onClickRetrieveTitles(View view) {
        //---retrieve the titles---
        Uri allTitles = Uri.parse(
                "content://seneca.ict.provider.CoursesProvider/courses");

        Cursor c;
        SQLiteDatabase courses;
        if (android.os.Build.VERSION.SDK_INT <11) {
            //---before Honeycomb---
            c = managedQuery(allTitles, null, null, null,
                    "code desc");
        } else {
            //---Honeycomb and later---
            CursorLoader cursorLoader = new CursorLoader(
                    this,
                    allTitles, null, null, null,
                    "code desc");
            c = cursorLoader.loadInBackground();
        }

        List<String> listViewValues = new ArrayList<String>();
        if(c.moveToFirst()){
            do{
                String coursesname = c.getString(c.getColumnIndex(
                        TestCoursesProvider._ID))+ ", " +
                        c.getString(c.getColumnIndex(
                                TestCoursesProvider.CODE)) + ", " +
                        c.getString(c.getColumnIndex(
                                TestCoursesProvider.TITLE))+ ", " +
                        c.getString(c.getColumnIndex(TestCoursesProvider.ROOM));

                // add the bookName into the bookTitles ArrayList
                listViewValues.add(coursesname);
            }while(c.moveToNext());
        }
        ListView lv = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.list_view,
                listViewValues.toArray(new String[listViewValues.size()]));
        lv.setAdapter(adapter);


		/*if (c.moveToFirst()) {


			do{


				Toast.makeText(this,
						c.getString(c.getColumnIndex(
								TestCoursesProvider._ID)) + ", " +
								c.getString(c.getColumnIndex(
										TestCoursesProvider.CODE)) + ", " +
										c.getString(c.getColumnIndex(
												TestCoursesProvider.TITLE)) + ", " +
                                                c.getString(c.getColumnIndex(TestCoursesProvider.ROOM)),
											Toast.LENGTH_SHORT).show();


			} while (c.moveToNext());

		}*/

    }

    public void updateTitle() {
        ContentValues editedValues = new ContentValues();
        editedValues.put(TestCoursesProvider.TITLE, "Android Tips and Tricks");
        getContentResolver().update(
                Uri.parse(
                        "content://seneca.ict.provider.CoursesProvider/courses/2"),
                editedValues,
                null,
                null);
    }

    public void deleteTitle() {

        //---delete a title---
        getContentResolver().delete(
                Uri.parse("content://seneca.ict.provider.CoursesProvider/courses/2"),
                null, null);


        //---delete all titles---
        getContentResolver().delete(
                Uri.parse("content://seneca.ict.provider.CoursesProvider/courses"),
                null, null);

    }


}

