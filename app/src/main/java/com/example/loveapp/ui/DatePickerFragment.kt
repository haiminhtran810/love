package com.example.loveapp.ui

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.Calendar

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {
    lateinit var datePickerInterface: DatePickerInterface
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(activity, this, year, month, day)
    }

    override fun onDateSet(
        view: DatePicker?,
        year: Int,
        month: Int,
        dayOfMonth: Int
    ) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        datePickerInterface.changeDate(calendar)
    }

    companion object {
        const val TAG = "DatePickerFragment"
        fun newInstance() = DatePickerFragment()
    }
}