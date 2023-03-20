package com.example.team42fitness.ui.fitness

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.team42fitness.R
import com.example.team42fitness.data.fitnessData.LocationData
import kotlinx.coroutines.flow.Flow

/**
 * The ClickedDayFragment will be used to deal with when the user clicks on a specific day
 * to add locations of where they went to on a particular day. Users are able to add entries
 * on locations they went to, which will be displayed in a recyclerview. When the user clicks
 * on a particular entry, an intent will be launched to view that location. The hope is that
 * there will be functionality added as well, where, at the click of a button, the user
 * will be able to see all the entries as markers on an embedded Google Maps object.
 */
class ClickedDayFragment : Fragment(R.layout.fragment_clicked_day)
{
    private val TAG = "ClickedDayFragment.kt"

    /**
     * I think i need to have this viewModel used... or actually, maybe gotta create a ViewModel class...
     */
    private val viewModel: ClickedDayViewModel by viewModels()
    private val args: ClickedDayFragmentArgs by navArgs()

    private val roomViewModel: RoomViewModel by viewModels()
    private val locationEntriesAdapter = ClickedDayAdapter(::onLocationEntryClick)



    private lateinit var locationEntryRV: RecyclerView

    private var insertCounter = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        /**
         * RecyclerView set up for user location entries
         */
        locationEntryRV = view.findViewById(R.id.rv_clicked_day)
        locationEntryRV.layoutManager = LinearLayoutManager(requireContext())
        locationEntryRV.setHasFixedSize(true)
        locationEntryRV.adapter = locationEntriesAdapter


        /**
         * Override support action bar title to include the date, based on which day was clicked
         */
        var fullSupportActionBarTitle: String = args.locationDate.date + " " + "Lookback"
        (activity as AppCompatActivity).supportActionBar?.title = fullSupportActionBarTitle



        /**
         * Wondering if I gotta read from database to populate adapter whenever a day is clicked to repopulate...
         * Since it looks like content is not displayed if I go back and then return to specific day...
         */

//        entriesFromSpecificDate?.let {
//            for (item in it) {
//                locationEntriesAdapter.addLocationEntry(LocationData(item.index, item.day, item.locationName))
//            }
//        }
        // locationEntriesAdapter.addLocationEntry()


        val locationEntryET: EditText = view.findViewById(R.id.et_location_entry)
        val locationEntryBtn: Button = view.findViewById(R.id.btn_add_entry)

        /**
         * OnClickListener for button to add a location entry when the user
         * has entered a location they went to that day. Also adds entry info
         * into database, including index, day, and location entry
         */
        locationEntryBtn.setOnClickListener {
            val newLocationEntry = locationEntryET.text.toString()
            if (!TextUtils.isEmpty(newLocationEntry))
            {
                locationEntriesAdapter.addLocationEntry(LocationData(insertCounter, args.locationDate.date, newLocationEntry))
                locationEntryET.setText("")
                roomViewModel.addLocationEntry(LocationData(insertCounter, args.locationDate.date, newLocationEntry))

                insertCounter += 1
            }
        }
    }


    override fun onResume()
    {
        super.onResume()


        // add functionality that gets entries from database for specific day (based on which day was clicked) and puts that into recyclerview
        // unless, that has to be handled by ClickedDayViewModel
    }


    /* launch intent to google maps for the location entry clicked */
    private fun onLocationEntryClick(locationData: LocationData)
    {
        viewLocationEntryOnMap(locationData)
    }


    private fun viewLocationEntryOnMap(locationData: LocationData)
    {
        Log.d(TAG, "ViewLocationEntryOnMap() called on ${args.locationDate.date} for the entry $locationData")

    }
}