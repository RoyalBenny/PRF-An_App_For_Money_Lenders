package com.prf.prf


import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ExpandableListView
import android.widget.LinearLayout
import android.widget.SearchView
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*



var con :Context?=null

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var search:SearchView?=null
    override  fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater=menuInflater
        menuInflater.inflate(R.menu.search,menu)
        var string:String?= null


        val searchItem = menu?.findItem(R.id.search_view)
        search = searchItem?.actionView as SearchView
        search!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                string=newText

                if(string?.length!=0) {
//                    val recyclerView=findViewById(R.id.recyclerView) as RecyclerView
//                    recyclerView.layoutManager= LinearLayoutManager(con!!, LinearLayout.VERTICAL,false)
//
//
                   var db=DatabaseClass(con!!)
                   var data:ArrayList<Users> = arrayListOf()
                   data = db.searchRecycle(string.toString())
                   var globalObject= Globals.Chosen
                   globalObject.globalList=data
                   Collections.sort(globalObject.globalList)

                    val expListView=findViewById<ExpandableListView>(R.id.expandableListView)
                    listAdapter= ExpanadbleListAdapter(con!!, expListView,globalObject.returnGlobals(),2,globalObject.returnGlobals())
                    expListView.setAdapter(listAdapter)
                   var dateExpection = expection(con!!)

//                    dateExpection.changeExpection()
//                    var intent = intent
//                    var context = this
//
//                    recyclerView.adapter = adapter


                }
                return true
        }

        })


      return  super.onCreateOptionsMenu(menu)
    }




    lateinit var bottomNavigation: BottomNavigationView


    lateinit var toolbar2: ActionBar


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->


        //        toolbar.title="SONG"
//
//        val songFragment=SongsFragment.newInstance()
//
//        openFragment(songFragment)


        when (item.itemId) {

            R.id.bottom_home -> {

                search!!.visibility=View.VISIBLE
                toolbar2.title = "PRF"

                val home=homeFragment()
                openFragment(home)

                return@OnNavigationItemSelectedListener true


            }
            R.id.bottom_recent -> {
                my_toolbar2.title = "Recent"
              search!!.visibility=View.INVISIBLE
                val recent = recentFragment()

                openFragment(recent)
                return@OnNavigationItemSelectedListener true
         }
           R.id.bottom_note -> {
               search!!.visibility=View.INVISIBLE
               my_toolbar2.title = "Note"


               val note = noteFragment()
               openFragment(note)

              return@OnNavigationItemSelectedListener true
          }

        }

        false

    }


    @SuppressLint("ResourceType")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       setSupportActionBar(findViewById(R.id.my_toolbar2))





        con=this
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, my_toolbar2, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        toolbar2 = supportActionBar!!

        bottomNavigation = findViewById(R.id.navigationView) as BottomNavigationView

        bottomNavigation.selectedItemId = R.id.bottom_home


//        bottomNavigation.setSelectedItemId(R.id.navigation_artists)
        if (bottomNavigation.selectedItemId == R.id.bottom_home) {


            my_toolbar2.title = "PRF"

            val home = homeFragment()

            openFragment(home)


        }
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)




    }



    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        }

        else if(search!!.isIconified==false) {
            search!!.setIconified(true)
            bottomNavigation.selectedItemId = R.id.bottom_home

            search!!.onActionViewCollapsed()

            my_toolbar2.title = "PRF"

            val home = homeFragment()

            openFragment(home)

        }
        else if (bottomNavigation.selectedItemId == R.id.bottom_home) {


                finish()
                super.onBackPressed()
        }

        else {
            bottomNavigation.selectedItemId = R.id.bottom_home


            my_toolbar2.title = "PRF"

            val home = homeFragment()

            openFragment(home)

        }
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.main, menu)
//        return true
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        when (item.itemId) {
//            R.id.action_settings -> return true
//            else -> return super.onOptionsItemSelected(item)
//        }
//    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
           R.id.nav_create -> {
                val intent=Intent(applicationContext,CreateActivity::class.java)
               startActivity(intent)


            }
            R.id.nav_recent_clear -> {

                val builder=AlertDialog.Builder(this)
                builder.setTitle("Recent Clear")
                builder.setMessage("Do you want to clear recent?")
                builder.setPositiveButton("Yes",{ dialogInterface: DialogInterface, i: Int ->

                var clear=recentDatabase(con!!)
                clear.deleteRecent()

                if (bottomNavigation.selectedItemId == R.id.bottom_recent) {

                    bottomNavigation.selectedItemId = R.id.bottom_home

                    my_toolbar2.title = "PRF"

                    val home = homeFragment()

                    openFragment(home)


                }
                })

                builder.setNegativeButton("No",{ dialogInterface: DialogInterface, i: Int -> })
                builder.show()
            }
            R.id.nav_expected_chane -> {

                val builder=AlertDialog.Builder(this)
                builder.setTitle("Except Change")
                builder.setMessage("Decrease except installment by 1")
                builder.setPositiveButton("Yes",{ dialogInterface: DialogInterface, i: Int -> var db=DatabaseClass(this)
                    db.holiday()

                    bottomNavigation.selectedItemId = R.id.bottom_home

                    my_toolbar2.title = "PRF"

                    val home = homeFragment()

                    openFragment(home)
                })
                builder.setNegativeButton("No",{ dialogInterface: DialogInterface, i: Int -> })
                builder.show()

            }

            R.id.nav_except_incre -> {

                val builder=AlertDialog.Builder(this)
                builder.setTitle("Except Change")
                builder.setMessage("Increase except installment by 1")
                builder.setPositiveButton("Yes",{ dialogInterface: DialogInterface, i: Int -> var db=DatabaseClass(this)
                    db.IncreaseExcept()

                    bottomNavigation.selectedItemId = R.id.bottom_home

                    my_toolbar2.title = "PRF"

                    val home = homeFragment()

                    openFragment(home)
                })
                builder.setNegativeButton("No",{ dialogInterface: DialogInterface, i: Int -> })
                builder.show()

            }


            R.id.nav_create_note-> {
                val intent=Intent(applicationContext,CreateNote::class.java)
                startActivity(intent)
            }

        }

        drawer_layout.closeDrawer(GravityCompat.START)


        return true
    }


    private fun openFragment(fragment: Fragment) {

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()


    }








}


