package com.example.lab9prm
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode

class Activity : AppCompatActivity() {

    private var actionMode: ActionMode? = null
    private lateinit var textView: TextView
    private lateinit var btnContext: Button
    private lateinit var btnPopup: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.text_view)
        btnContext = findViewById(R.id.btnContext)
        btnPopup = findViewById(R.id.btnPopup)

        // Contextual ActionBar (long press TextView)
        textView.setOnLongClickListener {
            if (actionMode != null) return@setOnLongClickListener false
            actionMode = startSupportActionMode(contextualCallback)
            true
        }

        // Context Menu (long press Button)
        registerForContextMenu(btnContext)

        // Popup Menu (click Button)
        btnPopup.setOnClickListener { showPopupMenu(it) }
    }

    // Options Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        menuInflater.inflate(R.menu.menu_example, menu) // Add this line
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.option_search -> {
                toast("Option Menu: Search clicked")
                true
            }
            R.id.option_upload -> {
                toast("Option Menu: Upload clicked")
                true
            }
            R.id.mail -> {
                toast("Menu Example: Mail clicked")
                true
            }
            R.id.upload -> {
                toast("Menu Example: Upload clicked")
                true
            }
            R.id.share -> {
                toast("Menu Example: Share clicked")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Context Menu
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu?.setHeaderTitle("Context Menu")
        menu?.add(0, v!!.id, 0, "Download")
        menu?.add(0, v?.id ?: View.NO_ID, 0, "Edit")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        toast("Context Menu: ${item.title}")
        return true
    }

    // Popup Menu
    private fun showPopupMenu(view: View) {
        val popup = PopupMenu(this, view)
        popup.menuInflater.inflate(R.menu.popup_menu, popup.menu)
        popup.setOnMenuItemClickListener {
            toast("Popup Menu: ${it.title}")
            true
        }
        popup.show()
    }

    // Contextual Action Bar
    private val contextualCallback = object : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            mode?.menuInflater?.inflate(R.menu.contextual_menu, menu)
            mode?.title = "Select Action"
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean = false

        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            when (item?.itemId) {
                R.id.contextual_delete -> toast("Contextual: Delete clicked")
                R.id.contextual_share -> toast("Contextual: Share clicked")
            }
            mode?.finish()
            return true
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            actionMode = null
        }
    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
