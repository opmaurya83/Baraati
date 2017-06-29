package com.nectarbits.baraati.generalHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;



public class DBHelper extends SQLiteOpenHelper {

	/**This is the Path where database has copy from assets.
	 */
	private  static String DB_PATH = "/data/data/com.nectarbits.baraati/db/";

	/** This is the name of database.
	 */
	static final String DB_NAME = "baraati.sqlite";

	/**This is the version of database.
	 */
	static final int DB_VERSION = 1;

	/** this is context of current activity.
	 */
	private Context context;
	/** this is SQLiteDatabase object.
	 */
	private SQLiteDatabase dbTest = null;

	/**@param context
	 * this For DBHelper Constructor
	 * pass the Activity context.*/
	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		this.context = context;
		if(android.os.Build.VERSION.SDK_INT >= 17){
			DB_PATH = context.getApplicationInfo().dataDir + "/db/";
		}
		else
		{
			DB_PATH = "/data/data/" + context.getPackageName() + "/db/";
		}
	}

	public Context getDBContext(){
		return this.context;
	}

	/** (non-Javadoc)
	 * @see SQLiteOpenHelper#onCreate(SQLiteDatabase)*/
	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	/** (non-Javadoc)
	 * @see SQLiteOpenHelper#onUpgrade(SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d("DBHelper", "onUpdated");
		onCreate(db);
	}

	/**
	 * @throws IOException
	 * This for create database. 
	 * Copy the database from assets to application folder.
	 */
	public void createDataBase() throws IOException {
		boolean dbExist = checkDataBase();

		if (dbExist){
			// do nothing - database already exist
		}else {
			this.getReadableDatabase();

			try{
				copyDataBase();
			} catch (IOException e) {
				throw new Error("Error Copying Database !");
			}
		}
	}

	/**
	 * @return Boolean true or false.
	 * Check the database is exist or not. 
	 */
	private boolean checkDataBase() {
		SQLiteDatabase checkDB = null;
		try{
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,	SQLiteDatabase.OPEN_READWRITE);
		} catch (SQLiteException e){
			System.out.println("Data Base DoesNot Exit-->"+e.toString());
		}

		if (checkDB != null) {
			checkDB.close();
		}

		return checkDB != null ? true : false;
	}

	// Copy DataBase

	/**
	 * @throws IOException
	 * Copy the database to application assets folder to application folder
	 */
	public void copyDataBase() throws IOException {
		try {

			File file=new File(DB_PATH);
			if(!file.exists()){
				file.mkdirs();
				// Open your local db as the input stream
				InputStream myInput = context.getAssets().open(DB_NAME);
				// Path to the just created empty db
				String outFileName = DB_PATH + DB_NAME;
				// Open the empty db as the output stream
				OutputStream myOutput = new FileOutputStream(outFileName);
				// transfer bytes from the inputfile to the outputfile
				byte[] buffer = new byte[1024];
				int length;
				while ((length = myInput.read(buffer)) > 0){
					myOutput.write(buffer, 0, length);
				}
				// Close the streams
				myOutput.flush();
				myOutput.close();
				myInput.close();
				System.out.println("Created");
			}


		} catch (Exception ex){
			ex.printStackTrace();
		//	Toast.makeText(context, "ERROR IN CREATE DATABASE", Toast.LENGTH_SHORT).show();
		}
	}


	/**
	 * @throws IOException
	 * Copy the database to application assets folder to application folder
	 */
	public void Again() throws IOException {
		try {

			File file=new File(DB_PATH);
			if(file.exists()){
				file.mkdirs();
				// Open your local db as the input stream
				InputStream myInput = context.getAssets().open(DB_NAME);
				// Path to the just created empty db
				String outFileName = DB_PATH + DB_NAME;
				// Open the empty db as the output stream
				OutputStream myOutput = new FileOutputStream(outFileName);
				// transfer bytes from the inputfile to the outputfile
				byte[] buffer = new byte[1024];
				int length;
				while ((length = myInput.read(buffer)) > 0){
					myOutput.write(buffer, 0, length);
				}
				// Close the streams
				myOutput.flush();
				myOutput.close();
				myInput.close();
				System.out.println("Created");
			}


		} catch (Exception ex){
			ex.printStackTrace();
			//Toast.makeText(context, "ERROR IN CREATE DATABASE", Toast.LENGTH_SHORT).show();
		}
	}


	/**
	 * @throws SQLiteException
	 * check the database open or not.Before some transaction on this.
	 */
	public void openDatabase() throws SQLiteException{
		String dbPath = DB_PATH + DB_NAME;
		dbTest = SQLiteDatabase.openDatabase(dbPath, null,SQLiteDatabase.OPEN_READWRITE);
		//dbTest = SQLiteDatabase.openDatabase(dbPath, null,SQLiteDatabase.OPEN_READWRITE);
	}

	/**
	 * @throws SQLiteException
	 * this for write the database on application folder or not.
	 */
	public void writeDatabase() throws SQLiteException {
		String dbPath = DB_PATH + DB_NAME;
		dbTest = SQLiteDatabase.openDatabase(dbPath, null,SQLiteDatabase.OPEN_READWRITE);
	}

	/** 
	 *  
	 * @see SQLiteOpenHelper#close()
	 */
	public synchronized void close(){
		if (dbTest != null) {
			dbTest.close();
		}
	}

	/**
	 * @param tabName
	 * @return Cursor.
	 * pass table name and get the all record from table in cursor type. 
	 */
	public Cursor getAllRecord(String tabName) {
		openDatabase();
		Cursor cur;
		cur = dbTest.query(tabName, null, null, null, null, null, null);
		cur.moveToFirst();
		if(dbTest!=null){
			dbTest.close();
		}
		return cur;
	}
	/**@param tableNm
	 * @param fieldNm
	 * @param id
	 * @return Cursor.
	 * pass table name,fieldname and id and get the all record for this table in cursor type.
	 */
	public Cursor getAllRecordFromID(String tableNm, String fieldNm, String id){
		openDatabase();
		Cursor cursor;
		cursor = dbTest.rawQuery("select * from " + tableNm + " where "	+ fieldNm + "=" + id + "", null);
		cursor.moveToFirst();
		dbTest.close();
		return cursor;
	}


	/**
	 * @param insertString
	 * @return true or false.
	 * pass the query like a insert,update,delete and get the response that transaction complete successfully or not. 
	 */
	public boolean insertRecord(String insertString){
		try{
			writeDatabase();
			dbTest.execSQL(insertString);
			dbTest.close();
			return true;
		}catch (Exception ex){
		//	Toast.makeText(context, "ERROR IN INSERT RECORD",Toast.LENGTH_SHORT).show();
			Log.d("ERROR IN insertString =", "" + insertString);
			//Lg.showLog("ERROR IN INSERT RECORD, insertString = " + insertString);
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * @param //String selectString
	 * @return Cursor.
	 * pass the query witch fetch data from table.use this method for select query only.
	 */
	public Cursor executeSelectQuery(String selectString){	
		Cursor cursor = null;
		try{	
			openDatabase();
			cursor = dbTest.rawQuery(selectString,null);
			cursor.moveToFirst();
			//Edit
			if(dbTest!=null){
				dbTest.close();
			}
			//Edit
			return cursor;
		}catch (Exception ex){
			ex.printStackTrace();
		//	Toast.makeText(context, "ERROR IN EXECUTE QUERY",Toast.LENGTH_SHORT).show();
			ex.toString();
			return cursor;			
		}
	}

	public boolean executeUpdateUpery(String updateQuery){
		try{	
			writeDatabase();
			dbTest.execSQL(updateQuery);
			if(dbTest!=null){
				dbTest.close();
			}
			return true;
		}catch (Exception ex){
			ex.printStackTrace();
		//	Toast.makeText(context, "ERROR IN Update QUERY",Toast.LENGTH_SHORT).show();
			return false;			
		}
	}

	public boolean executeDeleteQuery(String deleteQuery){
		try{
			writeDatabase();
			dbTest.execSQL(deleteQuery);
			if(dbTest!=null){
				dbTest.close();
			}
			return true;
		}catch (Exception ex){
			ex.printStackTrace();
		//	Toast.makeText(context, "ERROR IN Update QUERY",Toast.LENGTH_SHORT).show();
			return false;
		}
	}


}