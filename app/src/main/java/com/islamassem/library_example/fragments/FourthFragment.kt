package com.islamassem.library_example.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.islamassem.library_example.R

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class FourthFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fourth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.pop).setOnClickListener {
            findNavController().navigate(R.id.action_FourthFragment_to_Second)
        }
        view.findViewById<Button>(R.id.destination).setOnClickListener {
            findNavController().navigate(R.id.action_FourthFragment_to_FirstFragment)
        }
    }
}