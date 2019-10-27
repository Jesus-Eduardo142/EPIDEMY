package com.example.user.vigilante.ciudadano.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mx.com.epivector.R


class Bitacorafragment : Fragment() {
    companion object {
        fun newInstance(): Bitacorafragment = Bitacorafragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_bitacora, container, false)
}