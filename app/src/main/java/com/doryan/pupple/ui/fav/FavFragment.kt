package com.doryan.pupple.ui.fav

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.doryan.pupple.R
import com.doryan.pupple.databinding.FragmentFavBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavFragment : Fragment() {

    private val viewModel: FavViewModel by viewModels()
    private lateinit var favAdapter: FavAdapter

    private lateinit var binding: FragmentFavBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_fav, container, false)

        binding.run {
            favViewModel = viewModel
            lifecycleOwner = viewLifecycleOwner
            rvFavDogs.run {
                layoutManager = LinearLayoutManager(context)
                adapter = FavAdapter(viewLifecycleOwner, viewModel).also {
                    favAdapter = it
                }
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.run {
            favDogs.observe(viewLifecycleOwner, {
                favAdapter.submitList(it)
            })
        }
    }
}