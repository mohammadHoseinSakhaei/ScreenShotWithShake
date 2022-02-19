package com.example.screenshot

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class FraagmentB :Fragment() {

    companion object{
        var imageView : ImageView?= null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_b,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        imageView = view.findViewById(R.id.image)
        val b = arguments?.getParcelable<Bitmap>("bit")
        imageView?.setImageBitmap(b);
        super.onViewCreated(view, savedInstanceState)
    }
}