package com.example.shoppinglist.ui.Frag

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.LottieDrawable
import com.example.shoppinglist.Broadcast.Receiver
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.FragmentHomeBinding
import com.example.shoppinglist.ui.Adapter.HomeAdapter
import com.example.shoppinglist.ui.VModel.factory
import com.example.shoppinglist.ui.VModel.observe
import com.example.shoppinglist.ui.home.HomeViewModel

class HomeFragment : Fragment(){
    private val viewModel: HomeViewModel by viewModels { factory }
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: HomeAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rcView()
        lottie()
    }

    private fun lottie() = with(binding){
        lottieView.setMinProgress(0.0f)
        lottieView.setMaxProgress(1.0f)
        lottieView.repeatCount = LottieDrawable.INFINITE
        lottieView.repeatMode = LottieDrawable.REVERSE
        lottieView.playAnimation()
    }

    private fun rcView() = with(binding) {
        rcView.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter = HomeAdapter({ id, isChecked ->
            viewModel.onCheckH(id, isChecked)
          }, { id ->
            viewModel.dalN(id)
            adapter.notifyDataSetChanged()
            Toast.makeText(context, "Очищено", Toast.LENGTH_SHORT).show()
        })

        rcView.adapter = adapter
        observe(viewModel.getHome()) {
            adapter.items = it
        }

        observe(viewModel.cancel) { id ->
            ContextCompat.getSystemService(requireContext(), AlarmManager::class.java)?.cancel(
                PendingIntent.getBroadcast(
                    requireContext(),
                    id,
                    Intent(requireContext(), Receiver::class.java),
                    PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.toolbar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.plus_1 -> {
                findNavController().navigate(R.id.action_navigation_home_to_navigation_dashboard)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}


