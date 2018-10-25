package eu.gitcode.musicmap.feature.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import eu.gitcode.musicmap.R
import eu.gitcode.musicmap.common.extensions.replaceFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.base_activity)
        if (savedInstanceState == null) {
            replaceFragment(R.id.fragment_container, MapFragment.newInstance(),
                    MapFragment.TAG)
        }
    }
}