/*
 * Copyright © 2020-2023 Proxidize. All Rights Reserved.
 */
package com.legacy.android

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class CredentialsDatabase(context: Context) :
    SQLiteOpenHelper(context, "savedCredentials.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table mycredentials(hostIp VARCHAR, region VARCHAR, port INTEGER, token VARCHAR, latitude DOUBLE, longitude DOUBLE)") //VARCHAR MAYBE WILL BE WRONG IN SQLITE IF(REPLACE IT WITH TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}