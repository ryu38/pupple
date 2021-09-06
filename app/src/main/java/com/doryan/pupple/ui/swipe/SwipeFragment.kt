package com.doryan.pupple.ui.swipe

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.animation.AccelerateInterpolator
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.doryan.pupple.R
import com.doryan.pupple.databinding.FragmentSwipeBinding
import com.yuyakaido.android.cardstackview.*
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SwipeFragment : Fragment(), CardStackListener {

    private val viewModel: SwipeViewModel by viewModels()

    private lateinit var cardStackAdapter: CardStackAdapter
    private lateinit var cardStackLayoutManager: CardStackLayoutManager

    private lateinit var binding: FragmentSwipeBinding

    object ActionDirections {
        val pass = Direction.Right
        val like = Direction.Left
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_swipe, container, false)
        binding.run {
            swipeViewModel = viewModel
            lifecycleOwner = viewLifecycleOwner
            cardStack.run {
                layoutManager = setCardStackLayoutManager()
                adapter = CardStackAdapter(viewLifecycleOwner, viewModel).also {
                    cardStackAdapter = it
                }
            }
        }

        setupButtons()

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.dogs.observe(viewLifecycleOwner, {
            cardStackAdapter.submitList(it)
        })
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

    private fun setCardStackLayoutManager(): CardStackLayoutManager {
        val manager = CardStackLayoutManager(context, this@SwipeFragment)
        manager.setCanScrollVertical(false)
        cardStackLayoutManager = manager
        return manager
    }

    private fun setupButtons() {
        binding.buttonPass.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                    .setDirection(ActionDirections.pass)
                    .setDuration(Duration.Normal.duration)
                    .setInterpolator(AccelerateInterpolator())
                    .build()
            cardStackLayoutManager.setSwipeAnimationSetting(setting)
            binding.cardStack.swipe()
        }

        binding.buttonLike.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                    .setDirection(ActionDirections.like)
                    .setDuration(Duration.Normal.duration)
                    .setInterpolator(AccelerateInterpolator())
                    .build()
            cardStackLayoutManager.setSwipeAnimationSetting(setting)
            binding.cardStack.swipe()
        }
    }

    override fun onCardSwiped(direction: Direction?) {
        when (direction) {
            ActionDirections.pass -> viewModel.pass()
            ActionDirections.like -> viewModel.fav()
        }
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {}

    override fun onCardRewound() {}

    override fun onCardCanceled() {}

    override fun onCardAppeared(view: View?, position: Int) {}

    override fun onCardDisappeared(view: View?, position: Int) {}
}