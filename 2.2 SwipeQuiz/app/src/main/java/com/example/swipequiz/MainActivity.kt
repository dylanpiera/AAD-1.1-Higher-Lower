package com.example.swipequiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.question_item.view.*

class MainActivity : AppCompatActivity() {

    private val questions = Question.QUESTIONS
    private val questionAdapter = QuestionAdapter(questions, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews() {
        rvQuestions.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        rvQuestions.adapter = questionAdapter

        rvQuestions.addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))

        createItemTouchHelper().attachToRecyclerView(rvQuestions)
    }

    private fun createItemTouchHelper(): ItemTouchHelper {
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder,direction: Int) {
                handleSwipe(questions[viewHolder.adapterPosition], direction)
                questionAdapter.notifyItemChanged(viewHolder.adapterPosition)
            }
        }

        return ItemTouchHelper(callback)
    }

    private fun handleSwipe(question: Question, direction: Int) {
        when (direction) {
            ItemTouchHelper.LEFT -> when {
                question.isTrue -> correct(question)
                else -> incorrect(question)
            }
            ItemTouchHelper.RIGHT -> when {
                question.isTrue -> incorrect(question)
                else -> correct(question)
            }
        }
    }

    private fun correct(question: Question) {
        Snackbar.make(rvQuestions, getString(R.string.correct, question.isTrue), Snackbar.LENGTH_SHORT).show()
    }

    private fun incorrect(question: Question) {
        Snackbar.make(rvQuestions, getString(R.string.incorrect, question.isTrue), Snackbar.LENGTH_SHORT).show()
    }

    data class Question(val question: String, val isTrue: Boolean) {
        companion object Question {
            val QUESTIONS = listOf(
                Question("A 'Val' and 'Var' are the same.", false),
                Question("Mobile Application Development grants 12 ECTS.", true),
                Question("A Unit in Kotlin corresponds to a void in Java", true),
                Question("In Kotlin, 'when' replaces the 'switch' operator in Java", true)
            )
        }
    }

    class QuestionAdapter(private val questions: List<Question>, private val context: Context): RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {
        override fun getItemCount(): Int {
            return questions.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(questions[position])
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.question_item, parent, false))
        }

        inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            fun bind(question: Question) {
                itemView.tvQuestion.text = question.question
                itemView.setOnClickListener {
                    Snackbar.make(itemView, context.getString(R.string.clickMessage, question.question, question.isTrue), Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

}