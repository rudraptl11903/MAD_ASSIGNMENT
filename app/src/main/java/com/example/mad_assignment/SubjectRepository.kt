package com.example.mad_assignment

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

object SubjectRepository {
    private const val PREFS_NAME = "timetable_prefs"
    private const val KEY_SUBJECTS = "subjects"

    fun getSubjects(context: Context): List<Subject> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val jsonStr = prefs.getString(KEY_SUBJECTS, null) ?: return emptyList()
        val list = mutableListOf<Subject>()
        try {
            val jsonArray = JSONArray(jsonStr)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val s = Subject(
                    jsonObject.optString("name", ""),
                    jsonObject.optString("teacher", ""),
                    jsonObject.optString("day", ""),
                    jsonObject.optString("startTime", ""),
                    jsonObject.optString("endTime", ""),
                    jsonObject.optString("room", "")
                )
                list.add(s)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }

    fun saveSubjects(context: Context, subjects: List<Subject>) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val jsonArray = JSONArray()
        for (s in subjects) {
            val jsonObject = JSONObject().apply {
                put("name", s.name)
                put("teacher", s.teacher)
                put("day", s.day)
                put("startTime", s.startTime)
                put("endTime", s.endTime)
                put("room", s.room)
            }
            jsonArray.put(jsonObject)
        }
        prefs.edit().putString(KEY_SUBJECTS, jsonArray.toString()).apply()
    }

    fun addSubject(context: Context, s: Subject) {
        val list = getSubjects(context).toMutableList()
        list.add(s)
        saveSubjects(context, list)
    }

    fun clearSubjects(context: Context) {
        saveSubjects(context, emptyList())
    }
}
