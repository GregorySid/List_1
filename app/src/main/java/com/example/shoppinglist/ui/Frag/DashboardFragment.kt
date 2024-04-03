package com.example.shoppinglist.ui.dashboard

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.AlarmManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionManager
import com.example.shoppinglist.Broadcast.Ext_id
import com.example.shoppinglist.Broadcast.Ext_name
import com.example.shoppinglist.Broadcast.Receiver
import com.example.shoppinglist.databinding.FragmentDashboardBinding
import com.example.shoppinglist.ui.VModel.DashboardViewModel
import com.example.shoppinglist.ui.VModel.Result
import com.example.shoppinglist.ui.VModel.factory
import com.example.shoppinglist.ui.VModel.observe

class DashboardFragment : Fragment() {
    private val viewModel: DashboardViewModel by viewModels { factory }
    private lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.edName.doOnTextChanged { text, _, _, _ ->
            viewModel.onNameChanged(text)
        }
        binding.swichTime.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onTimeCheckedChange(isChecked)
        }
        binding.dataPicker.addOnDateChangedListener { _, date ->
            viewModel.OnDateChanged(date)
        }
        binding.bSave.setOnClickListener {
            viewModel.onSaveClick()
        }
        binding.bCancal.setOnClickListener {
            viewModel.onCancaleClick()
        }
        observe(viewModel.dateTime) {
            binding.dateTime.text =
                android.text.format.DateFormat.format("E dd MMM HH:mm", it)
        }

        observe(viewModel.timChe) {
            TransitionManager.beginDelayedTransition(binding.cardV)
            binding.grTimeEdit.isVisible = it
        }

        observe(viewModel.save) {
            binding.bSave.isEnabled = it
        }

        observe(viewModel.result) { result ->
            if (result is Result.Save) {
                result.time?.let { time ->
                    ContextCompat.getSystemService(requireContext(), AlarmManager::class.java)
                        ?.let {
                            val pi = PendingIntent.getBroadcast(
                                requireContext(),
                                result.id,
                                Intent(requireContext(), Receiver::class.java)
                                    .putExtra(Ext_name, result.name)
                                    .putExtra(Ext_id, result.id),
                                PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
                            )

                            AlarmManagerCompat.setExactAndAllowWhileIdle(
                                it, AlarmManager.RTC_WAKEUP, time, pi
                            )
                        }
                }
            }
            findNavController().popBackStack()
        }
    }
}