package com.example.omar.taskmanager.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.omar.taskmanager.R
import com.example.omar.taskmanager.TaskManagerApp
import com.example.omar.taskmanager.dagger.component.DaggerActivityComponent
import com.example.omar.taskmanager.ui.tasks.TasksListActivity
import com.example.omar.taskmanager.dagger.module.ActivityModule
import com.example.omar.taskmanager.data.database.tables.User
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var loginVM: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val component = DaggerActivityComponent.builder()
            .appComponent((application as TaskManagerApp).component)
            .activityModule(
                ActivityModule(this)
            ).build()
        component.inject(this)



        login_btn.setOnClickListener {
            val username = user_name_edtxt_login.text.toString()
            loginVM.getUser(username)?.subscribe { user, th ->
                if (user == null) {
                    registerUser(username)
                } else {
                    saveUser(username)
                    goToTasksListActivity()
                }
            }
        }
    }

    private fun goToTasksListActivity() {
        val intent = Intent(this, TasksListActivity::class.java)
        startActivity(intent)
    }

    fun registerUser(username: String) {
        loginVM.insertUser(User(username))?.subscribe({
            Log.d("", "")
            goToTasksListActivity()
            saveUser(username)
        }, {
            Log.d("", "")
        })
    }

    fun saveUser(username: String){
        loginVM.getUser(username)?.subscribe({

            loginVM.setCurrentUser(it)
        },{

        })
    }
}
