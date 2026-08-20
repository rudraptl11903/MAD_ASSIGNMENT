package com.example.mad_assignment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SubjectsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subjects)

        findViewById<View>(R.id.btnBack).setOnClickListener { finish() }
        findViewById<View>(R.id.btnAdd).setOnClickListener {
            startActivity(Intent(this, AddSubjectActivity::class.java))
        }
        findViewById<View>(R.id.btnClearAll).setOnClickListener {
            SubjectRepository.clearSubjects(this)
            displaySubjects()
        }
    }

    override fun onResume() {
        super.onResume()
        displaySubjects()
    }

    private fun displaySubjects() {
        val subjects = SubjectRepository.getSubjects(this)
        val container = findViewById<TextView>(R.id.subjectContainer)
        if (subjects.isEmpty()) {
            container.text = "No subjects added yet."
        } else {
            val sb = StringBuilder()
            for ((index, s) in subjects.withIndex()) {
                sb.append("${index + 1}. ${s.name}\n")
                sb.append("   Teacher: ${s.teacher}\n")
                sb.append("   Day: ${s.day} (${s.startTime} - ${s.endTime})\n")
                sb.append("   Room: ${s.room}\n\n")
            }
            container.text = sb.toString().trim()
        }
    }
}
