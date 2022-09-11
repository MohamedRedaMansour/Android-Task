package com.mansour.taskassignment.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mansour.taskassignment.databinding.FragmentHomeBinding
import com.mansour.taskassignment.ui.MainActivity
import com.mansour.taskassignment.utils.Constants
import com.mansour.taskassignment.utils.Resource
import com.mansour.taskassignment.utils.Urls.GET_DATA
import com.mansour.taskassignment.viewModels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(){

    val viewModel: HomeViewModel by viewModels()

    // This property is only valid between onCreateView and onDestroyView.
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.callDataService(Constants.BASE_URL+GET_DATA)


        observeListener()
    }

    //used for observation
    fun observeListener() {
    viewModel.dataML.observe(viewLifecycleOwner, { event ->
        event.getContentIfNotHandled()?.let {
            // Only proceed if the event has never been handled
            when (it) {
                is Resource.Loading -> {
                    showCustomProgressBar()
                }
                is Resource.Success -> {
                    hideCustomProgressBar()
                    binding.tvUserId.text = it.data?.title
                }
                is Resource.Error -> {
                    hideCustomProgressBar()
                    it.message?.let { message ->
                        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.FailNetwork -> {
                    hideCustomProgressBar()
                    it.message?.let { message ->
                        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    })
  }

    private fun showCustomProgressBar() {
        binding.dataContainer.visibility = View.GONE
        binding.paginationProgressBar.visibility = View.VISIBLE
    }


    private fun hideCustomProgressBar() {
        binding.dataContainer.visibility = View.VISIBLE
        binding.paginationProgressBar.visibility = View.GONE
    }



}