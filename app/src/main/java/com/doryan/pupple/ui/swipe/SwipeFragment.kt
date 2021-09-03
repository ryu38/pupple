package com.doryan.pupple.ui.swipe

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.doryan.pupple.R
import com.doryan.pupple.databinding.FragmentSwipeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SwipeFragment : Fragment() {

    private val viewModel: SwipeViewModel by viewModels()

    private lateinit var binding: FragmentSwipeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_swipe, container, false)
        binding.swipeViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.swipe_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.fav -> findNavController()
                    .navigate(SwipeFragmentDirections.actionSwipeFragmentToFavFragment())
        }
        return super.onOptionsItemSelected(item)
    }
}