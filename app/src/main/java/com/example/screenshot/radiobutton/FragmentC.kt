package com.example.screenshot.radiobutton

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.screenshot.R

class FragmentC:Fragment (){

    val list = mutableListOf<String>("A","B","C")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_c,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val rec:RecyclerView = view.findViewById(R.id.rec_fragmentC)
        val layoutManager = LinearLayoutManager(requireContext())
        val adapter = Adapter(list)
        rec.apply {
            this.adapter = adapter
            this.layoutManager = layoutManager
        }
        super.onViewCreated(view, savedInstanceState)
    }
}