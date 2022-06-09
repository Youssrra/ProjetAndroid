package com.example.miniprojetandroid.ui.ui.activitity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import com.example.miniprojetandroid.R
import com.example.miniprojetandroid.ui.ui.fragments.ForYouFragment
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout


class HomeActivity : AppCompatActivity() {

    // Initialise the DrawerLayout, NavigationView and ToggleBar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var navView: NavigationView
    private lateinit var topAppBar: MaterialToolbar
    private lateinit var email: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val toolbar: Toolbar = findViewById(R.id.app_bar)
        setSupportActionBar(toolbar)

        //topAppBar = findViewById(R.id.topAppBar)
        // Call findViewById on the DrawerLayout
        drawerLayout = findViewById(R.id.drawerLayout)

        // Pass the ActionBarToggle action into the drawerListener
        actionBarToggle = ActionBarDrawerToggle(this, drawerLayout, 0, 0)
        drawerLayout.addDrawerListener(actionBarToggle)

        // Display the hamburger icon to launch the drawer
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Call syncState() on the action bar so it'll automatically change to the back button when the drawer layout is open
        actionBarToggle.syncState()


        // Call findViewById on the NavigationView
        navView = findViewById(R.id.navView)


        val navigationView : NavigationView  = findViewById(R.id.navView)
        val headerView : View = navigationView.getHeaderView(0)
        val navUserEmail : TextView = headerView.findViewById(R.id.nav_header_textViewFullName)

        //navUserEmail.text = email.toString()

       // email = findViewById(R.id.nav_header_textViewFullName)

        if (intent.getStringExtra("email") != null ){
            navUserEmail.setText(intent.getStringExtra("email"))
        }

        //*Toast.makeText(this, email, Toast.LENGTH_SHORT).show()
        Log.println(Log.INFO, "INFO EMAIL ", "email : " +intent.getStringExtra("email"))


        // Call setNavigationItemSelectedListener on the NavigationView to detect when items are clicked
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.Home -> {
                    changeFragment(ForYouFragment(),"")
                    drawerLayout.close()
                    true
                }
                R.id.News -> {
                    Toast.makeText(this, "News", Toast.LENGTH_SHORT).show()
                    drawerLayout.close()
                    true
                }
                R.id.Notification -> {
                    Toast.makeText(this, "Notification", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.Map -> {
                    Toast.makeText(this, "Map", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.Profile -> {
                    Toast.makeText(this, "My Profile", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.LogOut -> {
                    Toast.makeText(this, "LogOut", Toast.LENGTH_SHORT).show()
                    true
                }

                else -> {
                    false
                }
            }
        }

       supportFragmentManager.beginTransaction().add(R.id.fragment_container, ForYouFragment()).commit()


    }

    private fun changeFragment(fragment: Fragment, name: String) {
        if (name.isEmpty())
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
        else
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(name).commit()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // override the onSupportNavigateUp() function to launch the Drawer when the hamburger icon is clicked
override fun onSupportNavigateUp(): Boolean {
    drawerLayout.openDrawer(navView)
    return true
}

// override the onBackPressed() function to close the Drawer when the back button is clicked
override fun onBackPressed() {
    if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
        this.drawerLayout.closeDrawer(GravityCompat.START)
    } else {
        super.onBackPressed()
    }
}

}