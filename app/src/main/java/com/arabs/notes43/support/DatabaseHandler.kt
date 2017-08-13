package com.arabs.notes43.support

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

import com.arabs.notes43.model.Contact

import java.util.ArrayList

/**
 * Created by Rashid on 10/08/2017.
 */

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // Creating Tables
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_CONTACTS_TABLE = "CREATE TABLE $TABLE_CONTACTS("+KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"+KEY_PH_NO + " TEXT" + ")"
        db.execSQL(CREATE_CONTACTS_TABLE)
    }

    // Upgrading database
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS)

        // Create tables again
        onCreate(db)
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    internal fun addContact(contact: Contact) {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(KEY_NAME, contact.name) // Contact Name
        values.put(KEY_PH_NO, contact.phoneNumber) // Contact Phone

        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values)
        db.close() // Closing database connection
    }

    // Getting single contact
    internal fun getContact(id: Int): Contact {
        val db = this.readableDatabase

        val cursor = db.query(TABLE_CONTACTS, arrayOf(KEY_ID, KEY_NAME, KEY_PH_NO), KEY_ID + "=?",
                arrayOf(id.toString()), null, null, null, null)
        cursor?.moveToFirst()

        val contact = Contact(Integer.parseInt(cursor!!.getString(0)),
                cursor.getString(1), cursor.getString(2))
        // return contact
        return contact
    }

    // Getting All Contacts
    // Select All Query
    // looping through all rows and adding to list
    // Adding contact to list
    // return contact list
    val allContacts: List<Contact>
        get() {
            val contactList = ArrayList<Contact>()
            val selectQuery = "SELECT  * FROM " + TABLE_CONTACTS

            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {
                do {
                    val contact = Contact()
                    contact.id = Integer.parseInt(cursor.getString(0))
                    contact.name = cursor.getString(1)
                    contact.phoneNumber = cursor.getString(2)
                    contactList.add(contact)
                } while (cursor.moveToNext())
            }
            return contactList
        }

    // Updating single contact
    fun updateContact(contact: Contact): Int {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(KEY_NAME, contact.name)
        values.put(KEY_PH_NO, contact.phoneNumber)

        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                arrayOf(contact.id.toString()))
    }

    // Deleting single contact
    fun deleteContact(contact: Contact) {
        val db = this.writableDatabase
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                arrayOf(contact.id.toString()))
        db.close()
    }


    // Getting contacts Count
    // return count
    val contactsCount: Int
        get() {
            val countQuery = "SELECT  * FROM " + TABLE_CONTACTS
            val db = this.readableDatabase
            val cursor = db.rawQuery(countQuery, null)
            cursor.close()
            return cursor.count
        }

    companion object {

        // All Static variables
        // Database Version
        private val DATABASE_VERSION = 1

        // Database Name
        private val DATABASE_NAME = "contactsManager"

        // Contacts table name
        private val TABLE_CONTACTS = "contacts"

        // Contacts Table Columns names
        private val KEY_ID = "id"
        private val KEY_NAME = "name"
        private val KEY_PH_NO = "phone_number"
    }
}